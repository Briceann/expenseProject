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
    <title>Dashboard</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .card-grid { display: flex; flex-wrap: wrap; gap: 1rem; }
        .card { flex: 1 1 200px; padding: 1rem; background: #1e1e1e; color: #fff; border-radius: 10px; }
        .section-title { margin-top: 2rem; margin-bottom: 1rem; color: #fff; }
        body { background-color: #121212; color: #fff; }
        .filter-bar { margin-bottom: 1rem; }
    </style>
</head>
<body>
<div class="container">
    <h1 class="my-4">Expense Tracker</h1>
    <p>Logged in as: ${sessionScope.userName}</p>

    <!-- Quick Actions -->
    <div class="mb-3">
        <a href="expense?action=add" class="btn btn-success"> Add Expense</a>
        <a href="addCategory.jsp" class="btn btn-secondary"> Add Category</a>
        <a href="expense?action=view" class="btn btn-secondary">View Expenses</a>
    </div>
    <p>categoryTotals: <c:out value="${categoryTotals}" /></p>
    <!-- Categories Overview -->
    <h3 class="section-title">Categories</h3>
    <div class="card-grid">
        <c:forEach var="entry" items="${categoryTotals}">
            <div class="card">
                <h5>${entry.key}</h5>
                <p>$${entry.value}</p>
            </div>
        </c:forEach>
    </div>
    <p>recentExpenses: <c:out value="${recentExpenses}" /></p>
    <!-- Recent Expenses -->
    <h3 class="section-title">Today</h3>
    <div class="filter-bar">
        <select class="form-select w-25 d-inline">
            <option selected>Past Week</option>
            <option>Past Day</option>
            <option>Past Month</option>
        </select>
    </div>

    <table class="table table-dark table-striped">
        <thead>
        <tr><th>Name</th><th>Amount</th><th>Date</th></tr>
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

    <!-- Spending Totals by Category (Table instead of Chart) -->
    <h3 class="section-title mt-5">Spending by Category</h3>
    <div class="bg-light p-3 rounded mt-4 text-dark">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Category</th>
                <th>Total Spent ($)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${categoryTotals}">
                <tr>
                    <td>${entry.key}</td>
                    <td>${entry.value}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>
</body>
</html>
