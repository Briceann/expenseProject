<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="taglib.jsp"%>
<%@ include file="includes/nav.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:set var="title" value="Dashboard" />
    <%@include file="head.jsp"%>
    <title>Dashboard</title>
</head>
<body>
<div class="wrapper">
    <div class=" container mt-4">
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
        <h3 class="section-title mt-5">Today</h3>
        <form method="get" action="dashboard" class="mb-3 d-inline">
            <select name="days" class="form-select w-25 d-inline" onchange="this.form.submit()">
                <option value="1" ${selectedDays == 1 ? 'selected' : ''}>Past Day</option>
                <option value="7" ${selectedDays == 7 ? 'selected' : ''}>Past Week</option>
                <option value="30" ${selectedDays == 30 ? 'selected' : ''}>Past Month</option>
            </select>
        </form>

        <form method="get" action="dashboard">
            <label for="currency">Display currency:</label>
            <select name="currency" id="currency">
                <option value="USD" ${currencyCode == 'USD' ? 'selected' : ''}>USD</option>
                <option value="EUR" ${currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                <option value="JPY" ${currencyCode == 'JPY' ? 'selected' : ''}>JPY</option>
                <option value="GBP" ${currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
                <!-- Add more as needed -->
            </select>

            <input type="submit" value="Convert">
        </form>


        <table class="table table-dark table-striped">
            <thead>
            <tr>
                <th>Name</th>
                <th>Amount</th>
                <th>Date</th>
                <th>Converted Amount</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="expense" items="${recentExpenses}">
                <tr>
                    <td>${expense.description}</td>
                    <td>$${expense.amount} USD</td>
                    <td>${expense.date}</td>
                    <td>${expense.convertedAmount} ${currencyCode}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
