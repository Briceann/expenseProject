<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
  <a class="navbar-brand" href="dashboard.jsp"> Expense Tracker</a>

  <div class="collapse navbar-collapse">
    <ul class="navbar-nav ms-auto">
      <c:choose>
        <c:when test="${not empty sessionScope.userId}">
          <li class="nav-item">
            <a class="nav-link disabled">Welcome, ${sessionScope.userName}</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="dashboard">Dashboard</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="expense?userId=${sessionScope.userId}">My Expenses</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="expense?action=add">Add Expense</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="addCategory">Add Category</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-danger" href="logout.jsp">Logout</a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="nav-item">
            <a class="nav-link" href="logIn">Login</a>
          </li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</nav>

