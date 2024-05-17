<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="ex" %>
<html>
<head>
    <title>Admin page</title>
    <link href="../../resources/styles.css" rel="stylesheet">
    <script>
        function onBeforeDeleteUser(userId) {
            var shouldProceed = confirm('Are you sure you want to delete user #' + userId);

            if (shouldProceed) {
                fetch('http://localhost:8080/admin/delete-user/' + userId, {
                    method: 'DELETE'
                })
                location.reload();
            }
        }
    </script>
</head>
<body>
<div class="myform">
    <div class="logo mb-3">
        <div class="userName">
            <h3><a href="/logout" id="logout">(Log out)</a></h3>
        </div>
        <div class="userName">
            <h3>${fn:escapeXml(user.firstName)} ${fn:escapeXml(user.lastName)} </h3>
        </div>
    </div>
    <h3><a href="/admin/add-user">Add new user</a></h3>
    <ex:UsersTable users="${users}"/>
</div>
</body>
</html>
