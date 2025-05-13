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
  <input type="hidden" id="userId" value="${user}">
  <input type="hidden" id="slotId" value="${slotId}">
  <input type="hidden" id="startDate" value="${startDate}">
  <input type="hidden" id="endDate" value="${endDate}">
  <input type="hidden" id="vehicleId" value="${vehicle}">
  <input type="hidden" id="type" value="${type}">

  <div class="left">
    <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="logo">
    <img class="bg-img" src="${pageContext.request.contextPath}/assets/images/bg3.jpg" alt="Car">
  </div>
  <div class="right">
    <h2 class="title">book your spot</h2>
    <p class="desc">your choice~</p>

    <form method="post" action="/checkout">
      <table class="booking-details">
        <tr><td>location</td><td id="location"></td></tr>
        <tr><td>type</td><td id="slotType"></td></tr>
        <tr><td>start date</td><td id="startDateDisplay">${startDate}</td></tr>
        <tr><td>end date</td><td id="endDateDisplay">${endDate}</td></tr>
        <tr><td>vehicle</td><td id="vehicleType"></td></tr>
        <tr><td>registration number</td><td id="registration"></td></tr>
      </table>

      <!-- Hidden inputs to carry the values to backend -->
      <input type="hidden" name="location" id="locationInput">
      <input type="hidden" name="slotType" id="slotTypeInput">
      <input type="hidden" name="vehicleType" id="vehicleTypeInput">
      <input type="hidden" name="registration" id="registrationInput">
      <input type="hidden" name="slotId" value="${slotId}">
      <input type="hidden" name="userId" value="${user}">
      <input type="hidden" name="startDate" value="${startDate}">
      <input type="hidden" name="endDate" value="${endDate}">
      <input type="hidden" name="vehicleId" value="${vehicle}">
      <input type="hidden" name="tyoe" value="${type}">

      <button type="submit" class="checkout-btn">continue to checkout</button>
    </form>

    <button class="change-btn" onclick="history.back()">← find a different choice</button>
    <p class="info">
      did you know? <br> for every long duration parking, you get a 2-hour grace period where you're not overcharged for overstaying
    </p>
  </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/booking.js"></script>
</body>
</html>
