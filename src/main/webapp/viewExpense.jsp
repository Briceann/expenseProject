<%@include file="taglib.jsp"%>
<%@ include file="includes/nav.jsp" %>

<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 3/9/25
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <%@include file="head.jsp"%>
    <title>View Expenses</title>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#expenseTable').DataTable({
                "order": [[0, "desc"]],
                "pageLength": 10
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <div class="container mt-4">
        <h1 class="mb-4">View Expenses</h1>

        <div class="mb-3 d-flex justify-content-between">
            <div>
                <a href="expense?action=add" class="btn btn-success me-2">Add New Expense</a>
                <a href="dashboard" class="btn btn-outline-secondary">Back to Dashboard</a>
            </div>
        </div>

        <!-- Filter -->
        <div class="card mb-4">
            <form action="expense" method="get" class="row g-2 align-items-center">
                <input type="hidden" name="action" value="view"/>
                <input type="hidden" name="userId" value="${sessionScope.userId}"/>
                <div class="col-auto">
                    <label for="category" class="form-label mb-0">Filter by Category:</label>
                </div>
                <div class="col-auto">
                    <select name="category" id="category" class="form-select">
                        <option value="">All</option>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.name}"
                                    <c:if test="${param.category == cat.name}">selected</c:if>>
                                    ${cat.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-outline-primary">Filter</button>
                </div>
            </form>
        </div>

        <!-- Table -->
        <div class="card">
            <table id="expenseTable" class="table table-dark table-striped">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="expense" items="${expenses}">
                    <tr>
                        <td>${expense.date}</td>
                        <td>${expense.category.name}</td>
                        <td>$${expense.amount}</td>
                        <td>${expense.description}</td>
                        <td>
                            <a href="expense?action=edit&id=${expense.expenseId}" class="btn btn-primary me-1">Edit</a>
                            <a href="expense?action=delete&id=${expense.expenseId}" onclick="return confirm('Are you sure?')" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
