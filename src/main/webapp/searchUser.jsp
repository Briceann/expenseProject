<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 3/28/25
  Time: 7:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<html>
<body>
<h2>Search Users</h2>
<form action="user" method="get">
    <div class="form-group">
        <label for="searchTerm">Search by Last Name:</label>
        <input type="text" id="searchTerm" name="searchTerm" placeholder="Enter last name">
    </div>
    <div>
        <button type="submit" name="submit" value="search" class="btn btn-primary">Search</button>
        <button type="submit" name="submit" value="view" class="btn btn-primary">View All Users</button>
    </div>
</form>
</body>
</html>
