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

        <div class="mb-4 p-3 bg-dark rounded d-flex align-items-center gap-3">
        <form method="get" action="dashboard" class="d-flex align-items-center gap-2">
            <label for="currency" class="form-label text-white mb-0">Display currency:</label>
            <select name="currency" id="currency" class="form-select bg-dark text-white select2" style="width: 200px;">
                <c:forEach var="code" items="${availableCurrencies}">
                    <option value="${code}" ${code == currencyCode ? 'selected' : ''}>${code}</option>
                </c:forEach>
            </select>
            <button type="submit" class="btn btn-primary">Convert</button>
        </form>
    </div>


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

<!-- Select2 Initialization -->
<script>
    $(document).ready(function () {
        $('#currency').select2({
            placeholder: "Select a currency",
            allowClear: true,
            templateResult: function (data) {
                return data.id || data.text;
            },
            templateSelection: function (data) {
                return data.id || data.text;
            }
        });
    });
</script>

<%@ include file="includes/footer.jsp" %>
</body>
</html>
