package edu.matc.controller;

import edu.matc.entity.Expense;
import edu.matc.persistence.ExpenseCategoryDao;
import edu.matc.persistence.ExpenseDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
      int userId = (int) request.getSession().getAttribute("userId");

        // If not authenticated, redirect to login
       if (userId == 0) {
           response.sendRedirect("login.jsp");
           return;
       }

        // Fetch spending totals by category
        Map<String, Double> categoryTotals = expenseDao.getAllCategoryTotalsForUser(userId);
        request.setAttribute("categoryTotals", categoryTotals);

        // Fetch expenses from the past 7 days
        List<Expense> recentExpenses = expenseDao.getRecentExpenses(userId, 7);
        request.setAttribute("recentExpenses", recentExpenses);

        // Forward the data to dashboard.jsp for display
        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp"); //issue with dashboard mapping
        dispatcher.forward(request, response);
    }
}

