<%--
  Created by IntelliJ IDEA.
  User: kavindu
  Date: 3/10/2025
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="manifest" href="images/site.webmanifest">
    <link rel="stylesheet" href="css/dashboard.css">
    <title>park.me | dashboard</title>
</head>
<body>
<div class="container">
    <div class="sidebar"></div>
    <div class="dashboard">
        <div class="action-bar">
            <div class="action-left">
                <h1 class="title">Dashboard</h1>
                <div class="location-info">
                    <p>Bandaranayake Int</p>
                </div>
            </div>
            <div class="action-right">
                <p class="a-r-greeting">Good Morning</p>
                <button class="new-slot-btn">New Slot</button>
                <img src="images/notification.svg" alt="notification img">
                <button class="profile-btn">
                    <span>Icon</span>
                </button>
            </div>
        </div>
        <div class="dash-top">
            <div class="section sc-gray"></div>
            <div class="section sc"></div>
            <div class="section sc-white"></div>
        </div>
        <div class="dash-bottom">
            <div class="chart"></div>
            <div class="customers"></div>
            <div class="recent-activity"></div>
        </div>
    </div>
</div>
</body>
</html>
