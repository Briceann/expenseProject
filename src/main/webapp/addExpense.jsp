<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 2/25/25
  Time: 4:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="includes/nav.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="head.jsp"%>
    <title>
        <c:choose>
            <c:when test="${expense != null}">Edit Expense</c:when>
            <c:otherwise>Add Expense</c:otherwise>
        </c:choose>
    </title>
</head>
<body class="bg-dark text-light">
<div class="wrapper">
    <div class="container mt-5">

        <h2>
            <c:choose>
                <c:when test="${expense != null}">Edit Expense</c:when>
                <c:otherwise>Add New Expense</c:otherwise>
            </c:choose>
        </h2>

        <form action="expense" method="post">

            <!-- Action & user identification -->
            <c:choose>
                <c:when test="${expense != null}">
                    <input type="hidden" name="action" value="update" />
                    <input type="hidden" name="userName" value="${expense.user.email}" />
                    <input type="hidden" name="id" value="${expense.expenseId}" />
                    <input type="hidden" name="userId" value="${expense.user.userId}" />
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="action" value="add" />
                    <input type="hidden" name="userName" value="${sessionScope.userName}" />
                </c:otherwise>
            </c:choose>

            <!-- Amount -->
            <div class="mb-3">
                <label for="amount" class="form-label">Amount</label>
                <input type="number" id="amount" step="0.01" class="form-control" name="amount"
                       value="${expense != null ? expense.amount : ''}" required />
            </div>

            <!-- Date -->
            <div class="mb-3">
                <label for="date" class="form-label">Date</label>
                <input type="date" id="date" class="form-control" name="date"
                       value="${expense != null ? expense.date : ''}" required />
            </div>

            <!-- Category Dropdown -->
            <div class="mb-3">
                <label for="categoryId" class="form-label">Category</label>
                <select name="categoryId" id="categoryId" class="form-select" required>
                    <option value="" disabled
                            <c:if test="${expense == null}">selected</c:if>>-- Select Category --</option>

                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryId}"
                                <c:if test="${expense != null && expense.category.categoryId == category.categoryId}">
                                    selected
                                </c:if>>
                                ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Description -->
            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <input type="text" id="description" class="form-control" name="description"
                       value="${expense != null ? expense.description : ''}" />
            </div>

            <!-- Buttons -->
            <button type="submit" class="btn btn-success">
                <c:choose>
                    <c:when test="${expense != null}">Update Expense</c:when>
                    <c:otherwise>Add Expense</c:otherwise>
                </c:choose>
            </button>

            <a href="dashboard" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
