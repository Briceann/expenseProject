package edu.matc.controller;

import edu.matc.entity.Expense;
import edu.matc.persistence.ExchangeRatesDao;
import edu.matc.persistence.ExpenseCategoryDao;
import edu.matc.persistence.ExpenseDao;
import edu.matc.rates.ExchangeRates;
import edu.matc.util.CurrencyConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.util.Map;
/**
 * Handles the display of the dashboard view.
 * Retrieves the currently logged in user's ID from the session
 * Fetches total spending by category
 * Fetches recent expenses past 7 days
 * Forwards the request to dashboard.jsp with the data
 * Requires a valid user session with "userId" stored.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DashboardServlet.class);
    private final ExpenseDao expenseDao = new ExpenseDao();
    private final ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();

    /**
     * Handles GET requests to display the dashboard.
     *
     * Retrieves category totals and recent expenses for the user.
     * If the user is not logged in, redirects to the login page.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Retrieve user ID from session
      Object userIdObj = request.getSession().getAttribute("userId");

        // If not authenticated, redirect to login
       if (userIdObj == null) {
           response.sendRedirect("logIn");
           return;
       }

       int userId = (int) userIdObj;
        // Get days from request parameter, default to 7 (Past Week)
        int days = 7;
        String daysParam = request.getParameter("days");
        if (daysParam != null) {
            try {
                days = Integer.parseInt(daysParam);
            } catch (NumberFormatException e) {
                // If parsing fails, keep default (7)
            }
        }

        // Fetch spending totals by category
        Map<String, Double> categoryTotals = expenseDao.getAllCategoryTotalsForUser(userId);
        request.setAttribute("categoryTotals", categoryTotals);

        // Fetch expenses from the past x days
        List<Expense> recentExpenses = expenseDao.getRecentExpenses(userId, days);

        // Currency conversion logic
        try {
            // Get selected currency from request param, fallback to USD
            String selectedCurrency = request.getParameter("currency");
            if (selectedCurrency == null || selectedCurrency.isEmpty()) {
                selectedCurrency = "USD";
            }

            ExchangeRates rates = new ExchangeRatesDao().getExchangeRates("USD");

            for (Expense e : recentExpenses) {
                double converted = CurrencyConverter.convert(e.getAmount(), rates, selectedCurrency);
                e.setConvertedAmount(converted);
            }

            request.setAttribute("currencyCode", selectedCurrency);

        } catch (Exception e) {
            request.setAttribute("conversionError", "Could not convert currency.");
            logger.error("Currency conversion failed", e);
        }

        request.setAttribute("recentExpenses", recentExpenses);
        request.setAttribute("selectedDays", days);

        // Forward the data to dashboard.jsp for display
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp"); //issue with dashboard mapping
        dispatcher.forward(request, response);
    }
}

