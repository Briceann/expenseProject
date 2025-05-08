<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/nav.jsp" %>

<html>
<head>
    <%@include file="head.jsp"%>
    <title>Add Category</title>
</head>
<body class="bg-dark text-light">
<div class="container mt-5">
    <h2>Add New Category</h2>
    <form action="addCategory" method="post" class="mt-4">
        <div class="mb-3">
            <label for="name" class="form-label">Category Name</label>
            <input type="text" id="name" class="form-control" name="name" required>
        </div>

        <button type="submit" class="btn btn-success">Save</button>
        <a href="dashboard" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
