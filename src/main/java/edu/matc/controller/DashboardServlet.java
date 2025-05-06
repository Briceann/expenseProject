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


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final ExpenseDao expenseDao = new ExpenseDao();
    private final ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      int userId = (int) request.getSession().getAttribute("userId");
       if (userId == 0) {
           response.sendRedirect("login.jsp");
           return;
       }

        Map<String, Double> categoryTotals = expenseDao.getAllCategoryTotalsForUser(userId);
        request.setAttribute("categoryTotals", categoryTotals);

        List<Expense> recentExpenses = expenseDao.getRecentExpenses(userId, 7);
        request.setAttribute("recentExpenses", recentExpenses);

       // System.out.println("recentExpenses = " + recentExpenses); // Tobe deleted
        System.out.println("👀 [DEBUG] recentExpenses.size = " + recentExpenses.size());
        for (Expense e : recentExpenses) {
            System.out.println("→ " + e.getDescription() + " | $" + e.getAmount() + " | " + e.getDate());
        }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}

