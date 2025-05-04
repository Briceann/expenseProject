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

        // Get totals by category
        Map<String, Double> categoryTotals = expenseDao.getExpenseCategoryTotalsByUser(userId);

        // Get expenses from past week
        List<Expense> recentExpenses = expenseDao.getRecentExpenses(userId, 7);

        request.setAttribute("categoryTotals", categoryTotals);
        request.setAttribute("recentExpenses", recentExpenses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}

