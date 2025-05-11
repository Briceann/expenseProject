<%@include file="head.jsp"%>
<%@include file="taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
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
