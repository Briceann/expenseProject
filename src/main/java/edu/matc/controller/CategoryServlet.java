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

@WebServlet("/addCategory")
public class CategoryServlet extends HttpServlet {
    private final ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();
    private static final Logger logger = LogManager.getLogger(ExpenseServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug("doGet in CategoryServlet called");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addCategory.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("name");

        if (categoryName != null && !categoryName.isEmpty()) {
            ExpenseCategory category = new ExpenseCategory();
            category.setName(categoryName);

            categoryDao.insert(category);
        }

        response.sendRedirect("addCategory.jsp");
    }
}
