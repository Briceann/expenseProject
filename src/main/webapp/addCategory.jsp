<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="includes/nav.jsp" %>

<html>
<head>
    <title>Add Category</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-dark text-light">
<div class="container mt-5">
    <h2>Add New Category</h2>
    <form action="addCategory" method="post" class="mt-4">
        <div class="mb-3">
            <label for="name" class="form-label">Category Name</label>
            <input type="text" class="form-control" name="name" required>
        </div>

        <button type="submit" class="btn btn-success">Save</button>
        <a href="dashboard" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
