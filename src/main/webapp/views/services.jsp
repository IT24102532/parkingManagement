<%--
  Created by IntelliJ IDEA.
  User: Kavindu
  Date: 5/14/2025
  Time: 5:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
<link rel="manifest" href="${pageContext.request.contextPath}/assets/images/site.webmanifest">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/business_styles.css">
<head>
    <title>park.me | our services</title>
</head>
<body>
<nav class="floating-navbar">
    <a href="/" class="logo">
        <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="Logo" />
        <span class="logo-title">park.me</span>
    </a>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/services">Services</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
    </div>
    <div class="auth-buttons">
        <a href="${pageContext.request.contextPath}/login" class="login-btn">Login</a>
        <a href="${pageContext.request.contextPath}/signup" class="signup-btn">Sign Up</a>
    </div>
</nav>
<section class="services-section">
    <h1 class="page-title">Our Services</h1>
    <p class="intro-text">We offer a wide range of services to meet your needs.</p>

    <div class="services-grid">
        <div class="service-card card">
            <div class="service-icon">💡</div>
            <h3>Consulting</h3>
            <p>Expert advice and strategic planning for your business.</p>
        </div>

        <div class="service-card card">
            <div class="service-icon">🛠️</div>
            <h3>Implementation</h3>
            <p>Turn-key solutions tailored to your requirements.</p>
        </div>
        <!-- Add more services -->
    </div>
</section>
</body>
</html>
