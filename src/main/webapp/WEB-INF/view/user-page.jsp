<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
    <link href="../../resources/styles.css" rel="stylesheet">
</head>
<body>
<div class="myform">
    <h1 align="center">Hello, ${fn:escapeXml(user.firstName)}</h1>
    <h3 align="center">Click <a href="/logout">here</a> to log out</h3>
</div>
</body>
</html>
