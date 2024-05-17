<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Signing up</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../../resources/styles.css" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src='https://www.google.com/recaptcha/api.js'></script>

    <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
</head>
<body>
<div id="singingUp">
    <div class="myform form ">
        <div class="col-md-12 text-center">
            <h1>Sign up</h1>
        </div>
        <form:form action="/register" method="post" modelAttribute="registerUser">
            <div class="form-group">
                <form:label path="login" for="login" class="control-label required">Login</form:label>
                <form:input type="text" path="login" class="form-control" id="login" placeholder="Enter login *"/>
                <form:errors path="login" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="password" for="password" class="control-label required">Password</form:label>
                <form:input path="password" type="password" name="password" id="password" class="form-control"
                            placeholder="Enter password *"/>
                <form:errors path="password" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="confirmPassword" for="password-again"
                            class="control-label required">Password again</form:label>
                <form:input path="confirmPassword" type="password" name="passwordAgain" id="password-again"
                            class="form-control"
                            placeholder="Enter password again *"/>
                <form:errors path="confirmPassword" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="email" for="email" class="control-label required">Email</form:label>
                <form:input path="email" type="email" name="email" class="form-control" id="email"
                            placeholder="Enter email *"/>
                <form:errors path="email" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="firstName" for="firstname" class="control-label required">First Name</form:label>
                <form:input path="firstName" type="text" name="firstName" class="form-control" id="firstname"
                            placeholder="Enter first name *"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="lastName" for="lastname" class="control-label required">Last Name</form:label>
                <form:input path="lastName" type="text" name="lastName" class="form-control" id="lastname"
                            placeholder="Enter last name *"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
            <div>
                <form:label path="birthday" for="birthday" class="control-label required">Birthday</form:label>
                <br>
                <% Date today = Date.valueOf(LocalDate.now()); %>
                <form:input path="birthday" type="date" name="birthday" id="birthday" value="<%= today %>"
                            pattern="yyyy-MM-dd"
                            min="1970-01-01"
                            max="<%= today %>"/>
            </div>
            <div>
                <div class="g-recaptcha" data-sitekey="6Ld2GFwaAAAAAPS0MsntSXRvqjQ81dseW0ICcJB3"></div>
                <form:errors path="captcha" cssClass="error"/>
            </div>
            <div align="center">
                <div class="buttons">
                    <button type="submit" name="ok" id="signin" class=" btn btn-block mybtn btn-primary tx-tfm">Ok
                    </button>
                </div>
                <a class="buttons" href="/">
                    <button type="button" name="cancel" class=" btn btn-block mybtn btn-primary tx-tfm">Cancel</button>
                </a>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
