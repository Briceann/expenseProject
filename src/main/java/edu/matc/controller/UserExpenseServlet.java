package edu.matc.controller;

import edu.matc.entity.User;
import edu.matc.persistence.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet("userExpense")
public class UserExpenseServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserExpenseServlet.class);
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("IndexServlet called");

        List<User> users = userDao.getAllUsersWithExpenses();

        if (users.isEmpty()) {
            logger.warn("No users found.");
        } else {
            logger.info("Retrieved " + users.size() + " users.");
            for (User user : users) {
                logger.info("User: " + user.getUsername() + " | Expenses: " + user.getExpenses().size());
            }
        }

        request.setAttribute("users", users);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
