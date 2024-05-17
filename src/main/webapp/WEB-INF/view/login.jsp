<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="../../resources/styles.css" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Kaushan+Script" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-5 mx-auto">
            <div id="loginning">
                <div class="myform form ">
                    <div class="logo mb-3">
                        <div class="col-md-12 text-center">
                            <h1>Authorization</h1>
                        </div>
                    </div>
                    <form action="login" method="post">
                        <div class="form-group">
                            <label for="login" class="control-label required">Login</label>
                            <input type="text" name="login" class="form-control" id="login"
                                   aria-describedby="emailHelp" required placeholder="Enter login *">
                        </div>
                        <div class="form-group">
                            <label for="passw" class="control-label required">Password</label>
                            <input type="password" name="password" id="passw" class="form-control"
                                   aria-describedby="emailHelp" required placeholder="Enter password *">
                        </div>
                        <c:if test="${param.error!=null}">
                            <div class="error" role="alert">
                                <c:out value="Invalid login and/or password"></c:out>
                            </div>
                        </c:if>
                        <div class="col-md-12 text-center ">
                            <button type="submit" id="signin" class=" btn btn-block mybtn btn-primary tx-tfm">Sign in
                            </button>
                        </div>
                        <div class="form-group" align="center">
                            <a href="/register">Sign up</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
