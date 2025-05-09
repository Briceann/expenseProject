<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 6:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp" %>
<%@ include file="includes/nav.jsp" %>

<body class="bg-dark text-light d-flex flex-column min-vh-100">
<div class="wrapper">
    <div class="container text-center mt-5">
        <h1 class="display-4 text-danger">Oops! Something went wrong.</h1>
        <p class="lead">An unexpected error occurred. Please try logging in again or return to the home page.</p>
        <div class="mt-4">
            <a href="logIn" class="btn btn-outline-light me-2">Return to Login</a>
            <a href="index.jsp" class="btn btn-outline-secondary">Home</a>
        </div>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>
</body>
</html>
