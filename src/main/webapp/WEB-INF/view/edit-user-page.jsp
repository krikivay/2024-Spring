<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Editing user</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../../resources/styles.css" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
</head>
<body>
<div id="singingUp">
    <div class="myform form ">
        <div class="logo mb-3">
            <div class="userName">
                <h5><a href="/logout" id="logout">(Log out)</a></h5>
            </div>
            <div class="userName">
                <h5>${fn:escapeXml(user.firstName)} ${fn:escapeXml(user.lastName)} </h5>
            </div>
        </div>
        <div class="col-md-12 text-center">
            <h1>Edit user</h1>
        </div>
        <form:form action="/admin/edit-user" method="post" modelAttribute="editUser">
            <form:input path="id" type="hidden"/>
            <form:input path="oldPassword" type="hidden"/>
            <div class="form-group">
                <form:label path="login" for="login">Login</form:label>
                <form:input path="login" readonly="true" type="text" name="login" class="form-control" id="login"
                            aria-describedby="emailHelp"
                            value="${fn:escapeXml(editUser.login)}"/>
                <form:errors path="login" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="password" for="password">Password</form:label>
                <input type="password" name="password" id="password" class="form-control"
                       aria-describedby="emailHelp"
                       placeholder="Enter password"/>
                <form:errors path="password" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="confirmPassword" for="password-again">Password again</form:label>
                <input type="password" name="confirmPassword" id="password-again"
                       class="form-control"
                       aria-describedby="emailHelp" placeholder="Enter password again"/>
                <form:errors path="confirmPassword" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="email" for="email">Email</form:label>
                <form:input path="email" type="email" name="email" class="form-control" id="email"
                            aria-describedby="emailHelp"
                            value="${fn:escapeXml(editUser.email)}"/>
                <form:errors path="email" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="firstName" for="firstname">First Name</form:label>
                <form:input path="firstName" type="text" name="firstName" class="form-control" id="firstname"
                            aria-describedby="emailHelp"
                            value="${fn:escapeXml(editUser.firstName)}"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="lastName" for="lastname">Last Name</form:label>
                <form:input path="lastName" type="text" name="lastName" class="form-control" id="lastname"
                            aria-describedby="emailHelp"
                            value="${fn:escapeXml(editUser.lastName)}"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
            <div>
                <form:label path="birthday" for="birthday">Birthday</form:label>
                <br>
                <% Date today = Date.valueOf(LocalDate.now()); %>
                <form:input path="birthday" type="date" name="birthday" id="birthday"
                            value='${editUser.birthday}' pattern="yyyy-MM-dd"
                            min="1970-01-01"
                            max="<%= today %>"/>
            </div>
            <div>
                <label class="select-box">Role</label>
                <br>
                <div class="select-box" align="left">
                    <label path="role.name" for="select-box1" class="label select-box1">
                        <span id="roleValue" class="label-desc">${editUser.role.name}</span></label>
                    <select path="role.name" id="select-box1" name="role" class="select">
                        <c:forEach var="role" items='${roles}'>
                            <option label="${role.name}"
                                ${editUser.role.id == role.id ? "selected" : ""}>${role.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <script src="../../resources/comboboxSelect.js"></script>
            <div align="center">
                <div class="buttons">
                    <button type="submit" name="ok" id="singin" class=" btn btn-block mybtn btn-primary tx-tfm">Ok
                    </button>
                </div>
                <div class="buttons">
                    <a href="/admin">
                        <button type="button" name="cancel" class=" btn btn-block mybtn btn-primary tx-tfm">Cancel
                        </button>
                    </a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>