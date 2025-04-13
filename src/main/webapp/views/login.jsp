<%--
  Created by IntelliJ IDEA.
  User: kavindu
  Date: 3/10/2025
  Time: 4:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="../assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/images/favicon-16x16.png">
    <link rel="manifest" href="../assets/images/site.webmanifest">

    <link rel="stylesheet" href="../assets/css/login.css">
    <link rel="stylesheet" href="../assets/css/dual_container_global.css">
    <title>park.me | continue →</title>
</head>
<body>
<div class="container">
    <div class="left">
        <img class="logo" src="${pageContext.request.contextPath}assets/images/logo_purple.png" alt="logo">
        <img class="bg-img" src="${pageContext.request.contextPath}assets/images/bg3.jpg" alt="Car">
    </div>
    <div class="right">
        <h1 class="title">sign in</h1>
        <form name="LoginForm"  action="login" method="post" class="login-form">
            <label for="email">email</label>
            <input class="input email-input" type="email" id="email" name="email" placeholder="example@email.com">

            <label for="password">password</label>
            <input class="input pw-input" type="password" id="password" name="password">
            <a class="forgot-pw" href="">forgot password?</a>

            <input class="form-btn" type="submit" value="continue">
        </form>
        <div class="other-options">
            <p class="register">don't have an account?</p>
            <p class="register-icon">
                <span>
                    <img src="${pageContext.request.contextPath}assets/images/fi-rr-user.svg" alt="">
                </span>
                <a href="">register</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>
