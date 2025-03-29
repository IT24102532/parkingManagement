<%--
  Created by IntelliJ IDEA.
  User: kavindu
  Date: 3/10/2025
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
  <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
  <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
  <link rel="manifest" href="images/site.webmanifest">

  <link rel="stylesheet" href="css/booking.css">

    <title>park.me | booking</title>
</head>
<body>
<div class="container">
  <div class="left">
    <img class="logo" src="${pageContext.request.contextPath}/images/logo_purple.png" alt="logo">
    <img class="bg-img" src="${pageContext.request.contextPath}/images/bg3.jpg" alt="Car">
  </div>
  <div class="right">
    <h2 class="title">book a spot</h2>
    <p>your choice~</p>
    <table>
      <tr><td>location</td><td><%= request.getAttribute("location") %></td></tr>
      <tr><td>type</td><td><%= request.getAttribute("type") %></td></tr>
      <tr><td>start date</td><td><%= request.getAttribute("startDate") %></td></tr>
      <tr><td>end date</td><td><%= request.getAttribute("endDate") %></td></tr>
      <tr><td>vehicle</td><td><%= request.getAttribute("vehicle") %></td></tr>
      <tr><td>registration number</td><td><%= request.getAttribute("registrationNumber") %></td></tr>
    </table>

    <button class="checkout-btn">continue to checkout</button>
    <div class="choice-container">
      <button class="change-btn">← find a different choice</button>
    </div>
    <p class="info">did you know? <br> for every long duration parking, you get a 2-hour grace period where you're not overcharged for overstaying</p>
  </div>
</div>
</body>
</html>
