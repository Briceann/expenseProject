<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Search Results" />
<%@include file="head.jsp"%>

<script type="text/javascript" class="init">
  $(document).ready(function () {
    $('#userTable').DataTable();
  } );
</script>
<html>
<body>
<div class="container-fluid">
  <h2>Search Results:</h2>
  <table id="userTable"  class="table table-striped table-bordered">
    <thead>
    <tr>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Username</th>
      <th>Email</th>
      <th>Expenses</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
      <tr>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td>
          <c:forEach var="expense" items="${user.expenses}">
            ${expense.description}
          </c:forEach>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<a href="index.jsp">Return to Home </a>
</body>
</html>