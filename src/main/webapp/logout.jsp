<%--
  Created by IntelliJ IDEA.
  User: thelmawendy
  Date: 4/29/25
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="taglib.jsp"%>
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
