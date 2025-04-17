<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="apple-touch-icon" sizes="180x180" href="../assets/images/apple-touch-icon.png">
  <link rel="icon" type="image/png" sizes="32x32" href="../assets/images/favicon-32x32.png">
  <link rel="icon" type="image/png" sizes="16x16" href="../assets/images/favicon-16x16.png">
  <link rel="manifest" href="../assets/images/site.webmanifest">

  <link rel="stylesheet" href="../assets/css/booking.css">
  <link rel="stylesheet" href="../assets/css/dual_container_global.css">

  <title>park.me | booking</title>
</head>
<body>
<div class="container">
  <div class="left">
    <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="logo">
    <img class="bg-img" src="${pageContext.request.contextPath}/assets/images/bg3.jpg" alt="Car">
  </div>
  <div class="right">
    <h2 class="title">book your spot</h2>
    <p class="desc">your choice~</p>
    <table class="booking-details">
      <tr><td>location</td><td><%= request.getAttribute("location") %></td></tr>
      <tr><td>type</td><td><%= request.getAttribute("type") %></td></tr>
      <tr><td>start date</td><td><%= request.getAttribute("startDateTime") %></td></tr>
      <tr><td>end date</td><td><%= request.getAttribute("endDate") %></td></tr>
      <tr><td>vehicle</td><td>${user.carType}</td></tr>
      <tr><td>registration number</td><td>${user.licensePlate}</td></tr>
    </table>

    <button class="checkout-btn">continue to checkout</button>
    <button class="change-btn">← find a different choice</button>
    <p class="info">did you know? <br> for every long duration parking, you get a 2-hour grace period where you're not overcharged for overstaying</p>
  </div>
</div>
</body>
</html>
