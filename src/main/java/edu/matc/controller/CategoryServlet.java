package edu.matc.controller;

import edu.matc.entity.ExpenseCategory;
import edu.matc.persistence.ExpenseCategoryDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Handles both GET and POST requests for adding new expense categories.
 * Displays the form to add a new category (GET)
 * Processes the form submission to insert a new category into the database (POST)
 * It uses  ExpenseCategoryDao to interact with the database.
 */
@WebServlet("/addCategory")
public class CategoryServlet extends HttpServlet {
    private final ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();
    private static final Logger logger = LogManager.getLogger(ExpenseServlet.class);

    /**
     * Handles GET requests to show the "Add Category" form.
     * Forwards the request to addCategory.jsp so the user can input a new category.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addCategory.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests to insert a new category into the database.
     * Retrieves the category name from the form, creates a new ExpenseCategory object,
     * saves it using the DAO, and redirects to the dashboard.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("name"); // Read the submitted category name from the form
        ExpenseCategory category = new ExpenseCategory();  // Create and populate the ExpenseCategory entity
        category.setName(categoryName);
        categoryDao.insert(category);

        // Redirect back to the dashboard after successful insert
        response.sendRedirect("dashboard.jsp");//issue with dashboard servlet path
    }
}
