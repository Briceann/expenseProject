<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/nav.jsp" %>

<html>
<head>
    <%@include file="head.jsp"%>
    <title>Dashboard</title>
    <style>
        body {
            background-color: #121212;
            color: #fff;
        }
        .card {
            background-color: #1e1e1e;
            color: #fff;
            border: none;
            border-radius: 12px;
        }
        .section-title {
            margin-top: 2rem;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>

<div class="container mt-4">
    <h1 class="mb-3">Expense Tracker</h1>

    <!-- Category Totals Cards -->
    <h3 class="section-title">Categories</h3>
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="entry" items="${categoryTotals}">
            <div class="col">
                <div class="card h-100 p-3">
                    <h5 class="card-title">${entry.key}</h5>
                    <p class="card-text">$${entry.value}</p>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Recent Expenses -->
    <h3 class="section-title">Today</h3>
    <div class="filter-bar mb-2">
        <select class="form-select w-25 d-inline">
            <option selected>Past Week</option>
            <option>Past Day</option>
            <option>Past Month</option>
        </select>
    </div>

    <table class="table table-dark table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Amount</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="expense" items="${recentExpenses}">
            <tr>
                <td>${expense.description}</td>
                <td>$${expense.amount}</td>
                <td>${expense.date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
