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
    <title>park.me | about us</title>
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
<section class="intro-section">
  <h1 class="page-title">Who We Are</h1>
  <p class="intro-text">
    At <strong>park.me</strong>, we're redefining the way urban spaces interact with vehicles. Founded with a vision to simplify parking and transportation logistics,
    our platform blends cutting-edge technology with human-centered design to create a smarter, more accessible city experience.
  </p>
</section>

<section class="mission-section">
  <h2 class="section-title">Our Mission</h2>
  <p class="mission-text">
    To provide seamless, reliable, and secure parking solutions for everyone—from local drivers to international travelers.
    We aim to reduce traffic, optimize space usage, and contribute to greener cities.
  </p>

  <div class="values-grid">
    <div class="value-card">
      <h3>Innovation</h3>
      <p>We constantly push boundaries with new technologies and smarter systems.</p>
    </div>
    <div class="value-card">
      <h3>Trust</h3>
      <p>Your safety and convenience are at the heart of our platform.</p>
    </div>
    <div class="value-card">
      <h3>Sustainability</h3>
      <p>We support eco-conscious urban development and efficient energy use.</p>
    </div>
  </div>
</section>
<section class="timeline-section">
  <h2 class="section-title">Our Journey</h2>
  <ul class="timeline">
    <li><strong>2021:</strong> park.me was born as a university research project.</li>
    <li><strong>2022:</strong> MVP launch with 100+ users in beta testing.</li>
    <li><strong>2023:</strong> Partnered with 10 major parking providers.</li>
    <li><strong>2024:</strong> Cross-country launch and real-time app release.</li>
    <li><strong>2025:</strong> Expanding across Southeast Asia with AI route suggestions.</li>
  </ul>
</section>
<section class="about-section">
  <h2 class="page-title">Our Team</h2>
  <div class="team-grid">
    <div class="team-member card">
      <img src="${pageContext.request.contextPath}/assets/images/team-kavindu.png" alt="team Member">
      <h3>Kavindu Nirmal</h3>
      <p class="role">Developer | IT24102532</p>
    </div>
    <div class="team-member card">
      <img src="${pageContext.request.contextPath}/assets/images/team-dilnaka.jpg" alt="Team Member">
      <h3>Dilnaka</h3>
      <p class="role">Developer | ITXXXXXXXX</p>
    </div>
    <div class="team-member card">
      <img src="${pageContext.request.contextPath}/assets/images/team-rayon.PNG" alt="Team Member">
      <h3>Rayon Senuka</h3>
      <p class="role">Developer | IT24104332 </p>
    </div>
    <div class="team-member card">
      <img src="${pageContext.request.contextPath}/assets/images/bg4.jpg" alt="Team Member">
      <h3>John Doe</h3>
      <p class="role">CEO & Founder</p>
    </div>
    <div class="team-member card">
      <img src="${pageContext.request.contextPath}/assets/images/bg4.jpg" alt="Team Member">
      <h3>John Doe</h3>
      <p class="role">CEO & Founder</p>
    </div>
    <div class="team-member card">
      <img src="${pageContext.request.contextPath}/assets/images/bg4.jpg" alt="Team Member">
      <h3>John Doe</h3>
      <p class="role">CEO & Founder</p>
    </div>
  </div>
</section>
</body>
</html>
