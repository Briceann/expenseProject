<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<%
  session.invalidate();
  response.sendRedirect("index.jsp");
%>

</body>
</html>
