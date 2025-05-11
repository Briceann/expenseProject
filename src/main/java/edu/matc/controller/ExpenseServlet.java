package edu.matc.controller;

import edu.matc.entity.Expense;
import edu.matc.entity.ExpenseCategory;
import edu.matc.entity.User;
import edu.matc.persistence.ExpenseCategoryDao;
import edu.matc.persistence.ExpenseDao;
import edu.matc.persistence.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Handles expense-related operations for the expense tracker application.
 * Servlet views all expenses or user-specific expenses
 * Add a new expense
 * Edit an existing expense
 * Delete an expense
 * Update an existing expense via POST
 * The servlet uses DAO classes to interact with the database and stores data in request attributes
 * to be displayed in corresponding JSPs.
 */
@WebServlet("/expense")
public class ExpenseServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ExpenseServlet.class);
    private final ExpenseDao expenseDao = new ExpenseDao();
    private final ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();
    private final UserDao userDao = new UserDao();

    /**
     * Handles GET requests for viewing, adding, editing, and deleting expenses.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            listExpenses(request, response);
        } else {
            switch (action) {
                case "add":
                    showAddForm(request, response);
                    break;
                case "delete":
                    deleteExpense(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listExpenses(request, response);
            }
        }
    }

    /**
     * Handles POST requests for adding or updating expenses.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateExpense(request, response);
        } else {
            addExpense(request, response);
        }
    }

    /**
     * Lists all expenses or filters by userId if provided.
     * Populates request attributes: expenses, categories, and optionally userId.
     */
    private void listExpenses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            logger.warn("User ID not found in session. Redirecting to login.");
            response.sendRedirect("logIn");
            return;
        }
        String selectedCategory = request.getParameter("category");
        List<Expense> expenses;

        // Category filter when selected
        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            expenses = expenseDao.getExpensesByCategory(userId, selectedCategory);
            logger.info("Filtered expenses by category: " + selectedCategory);
        } else {
            expenses = expenseDao.getExpensesByUserId(userId);
        }

        // Always load categories for the form
        List<ExpenseCategory> categories = categoryDao.getAllCategories();
        request.setAttribute("categories", categories);
        request.setAttribute("expenses", expenses);
        request.setAttribute("userId", userId);

        request.getRequestDispatcher("/viewExpense.jsp").forward(request, response);
    }

    /**
     * Adds a new expense for the currently logged-in user based on session userName and categoryId.
     */
    private void addExpense(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Expense expense = null;
        // Log all request parameters for debugging
        Map<String, String[]> params = request.getParameterMap();
        params.forEach((key, val) -> logger.info("PARAM: " + key + " = " + Arrays.toString(val)));

        try {
            String cognitoUserName = (String) request.getSession().getAttribute("userName");
            String email = (String) request.getSession().getAttribute("email");
            logger.debug("DEBUG: Cognito username from session = " + cognitoUserName);
            logger.debug("DEBUG: Email from session = " + email);

            if (cognitoUserName == null || cognitoUserName.isBlank()) {
                logger.error(" userName is missing from the request");
                response.sendRedirect("expense?action=add");
                return;
            }

            // Ensure user exist in the DB
            List<User> users = userDao.getByPropertyEqual("username", cognitoUserName);
            User user;
            if (users.isEmpty()) {
                logger.error(" No user found for email: " + cognitoUserName);

                user = new User();
                user.setUsername(cognitoUserName);
                user.setEmail(email != null? email : "placeholder@example.com"); // Set a placeholder or try to extract from token if available
                user.setFirstName("New");
                user.setLastName("User");
                int newUserId = userDao.insert(user);
                user.setUserId(newUserId);
            } else {
                user = users.get(0);
            }

            // Validate categoryId
            String categoryIdParam = request.getParameter("categoryId");
            if (categoryIdParam == null || categoryIdParam.isBlank()) {
                logger.error("categoryId is missing or blank — cannot parse.");
                response.sendRedirect("expense?action=add");
                return;
            }

            int categoryId = Integer.parseInt(categoryIdParam);
            ExpenseCategory category = categoryDao.getCategoryById(categoryId);
            if (category == null) {
                logger.error(" No category found for categoryId = " + categoryId);
                response.sendRedirect("expense?action=add");
                return;
            }

            //  Extract other fields
            double amount = Double.parseDouble(request.getParameter("amount"));
            String description = request.getParameter("description");
            LocalDate date = LocalDate.parse(request.getParameter("date"));

            //  Create and save the expense
            expense = new Expense(user, category, amount, date, description);
            expenseDao.insert(expense);
            logger.info(" Added expense for user: " + user.getEmail());

            response.sendRedirect("expense?userId=" + user.getUserId());

        } catch (Exception e) {
            logger.error(" Error adding expense: " + e.getMessage(), e);
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Deletes an expense by ID.
     * If found, deletes and redirects to the expense list for the user.
     */
    private void deleteExpense(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int expenseId = Integer.parseInt(request.getParameter("id"));
        Expense expense = expenseDao.getById(expenseId);

        if (expense != null) {
            int userId = expense.getUser().getUserId();
            expenseDao.delete(expense);
            logger.info("Deleted expense with ID: " + expenseId);
            response.sendRedirect("expense?userId=" + userId);
        } else {
            logger.warn("Expense ID not found: " + expenseId);
            response.sendRedirect("expense");
        }
    }

    /**
     * Shows the form to edit an existing expense.
     * Loads categories and the current expense to populate the form.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int expenseId = Integer.parseInt(request.getParameter("id"));
        Expense existingExpense = expenseDao.getById(expenseId);

        if (existingExpense != null) {
            //  Always load categories
            List<ExpenseCategory> categories = categoryDao.getAllCategories();
            request.setAttribute("categories", categories);

            request.setAttribute("expense", existingExpense);
            request.getRequestDispatcher("addExpense.jsp").forward(request, response);
        } else {
            logger.warn("Edit request for non-existing expense ID: " + expenseId);
            response.sendRedirect("expense");
        }
    }

    /**
     * Shows the add expense form.
     * Loads all available categories.
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ExpenseCategory> categories = categoryDao.getAllCategories();
        logger.info("Categories loaded for form: " + categories.size());
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/addExpense.jsp").forward(request, response);
    }


    /**
     * Updates an existing expense based on form input.
     * If valid, updates fields and redirects back to the user's expense list.
     */
    private void updateExpense(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Updating expense");

        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int expenseId = Integer.parseInt(request.getParameter("id"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String description = request.getParameter("description");
            LocalDate date = LocalDate.parse(request.getParameter("date"));

            Expense expense = expenseDao.getById(expenseId);
            ExpenseCategory category = categoryDao.getCategoryById(categoryId);

            if (expense == null || category == null) {
                logger.error("Invalid Expense or Category update request.");
                response.sendRedirect("expense");
                return;
            }

            expense.setCategory(category);
            expense.setAmount(amount);
            expense.setDate(date);
            expense.setDescription(description);

            expenseDao.update(expense);
            logger.info("Updated expense with ID: " + expenseId);

            response.sendRedirect("expense?userId=" + userId);
        } catch (Exception e) {
            logger.error("Error updating expense", e);
            response.sendRedirect("expense");
        }

    }

}
