<%@include file="head.jsp"%>
<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Expense Management</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-dark text-light">

<div class="container mt-5">
    <c:choose>
        <c:when test="${empty sessionScope.userName}">
            <h1>Welcome to Expense Tracker</h1>
            <p>Please <a href="logIn">log in</a> to continue.</p>
        </c:when>
        <c:otherwise>
            <h2>Welcome back, ${sessionScope.userName}!</h2>
            <p>Go to your <a href="dashboard" class="btn btn-primary">Dashboard</a></p>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
