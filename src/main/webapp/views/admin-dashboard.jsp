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
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-map-pin" width="16" height="16" style="margin-right: 10px;"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>
                    <p>Bandaranayake Int</p>
                </div>
            </div>
            <div class="action-right">
                <p class="a-r-greeting">
                    <span style="font-weight: 500;">Good Morning!</span> ${user.username}
                </p>
                <button class="new-slot-btn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-3"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
                    New Slot</button>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bell"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>
                <img class="profile-btn" src="https://avatar.iran.liara.run/username?username=${user.username}" alt="user image">
            </div>
        </div>
        <div class="dash-top">
            <div class="section sc-gray">
                <p class="section-sc-gray-title">reservations</p>
                <div class="sect-sc-gray-items">
                    <div class="sc-gray-active">
                        <p class="sc-gray-numbers">60</p>
                        <p>active</p>
                    </div>
                </div>
            </div>
            <div class="section sc"></div>
            <div class="section sc-white"></div>
        </div>
        <div class="dash-bottom">
            <div class="chart"></div>
            <div class="customers"></div>
        </div>
    </div>
</div>
</body>
</html>
