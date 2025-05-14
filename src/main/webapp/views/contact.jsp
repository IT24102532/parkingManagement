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
    <title>park.me | contact us</title>
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
<section class="contact-section">
    <h1 class="page-title">Get in Touch</h1>
    <p class="intro-text">Have questions? We'd love to hear from you! Fill out the form below and we'll get back to you shortly.</p>

    <form class="contact-form">
        <label>
            <input type="text" placeholder="Your Name" required>
        </label>
        <label>
            <input type="email" placeholder="Your Email" required>
        </label>
        <label>
            <textarea rows="5" placeholder="Your Message" required></textarea>
        </label>
        <button type="submit">Send Message</button>
    </form>
</section>
</body>
</html>
