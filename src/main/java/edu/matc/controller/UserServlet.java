package edu.matc.controller;

import edu.matc.persistence.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDao userDao = new UserDao();

//        if (request.getParameter("submit").equals("search")) {
//            request.setAttribute("users", userDao.getByPropertyEqual("lastName", request.getParameter("searchTerm")));
//        } else {
//            request.setAttribute("users", userDao.getAllUsers());
//        }

        String submit = request.getParameter("submit");
        if ("search".equals(submit)) {
            request.setAttribute("users", userDao.getByPropertyEqual("lastName", request.getParameter("searchTerm")));
        } else if ("view".equals(submit)) {
            request.setAttribute("users", userDao.getAllUsers());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-list.jsp");
        dispatcher.forward(request, response);

    }

}

