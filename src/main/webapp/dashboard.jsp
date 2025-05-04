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
    </div>

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

    <h3 class="section-title mt-5">Spending by Category</h3>
    <div class="bg-light p-3 rounded mt-4 text-dark">
        <canvas id="categoryChart"></canvas>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Build chart data from JSP
        const categoryLabels = [];
        const categoryData = [];

        <c:forEach var="entry" items="${categoryTotals}">
        categoryLabels.push("${entry.key}");
        categoryData.push(${entry.value});
        </c:forEach>

        const ctx = document.getElementById('categoryChart').getContext('2d');
        const categoryChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: categoryLabels,
                datasets: [{
                    label: 'Total Spent',
                    data: categoryData,
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(255, 206, 86, 0.6)',
                        'rgba(54, 162, 235, 0.6)',
                        'rgba(153, 102, 255, 0.6)',
                        'rgba(255, 159, 64, 0.6)'
                    ],
                    borderColor: '#222',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' },
                    title: { display: false }
                }
            }
        });
    </script>

</div>
</body>
</html>
