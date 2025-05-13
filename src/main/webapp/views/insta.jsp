<%--
  Created by IntelliJ IDEA.
  User: kavindu
  Date: 3/10/2025
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="../assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/images/favicon-16x16.png">
    <link rel="manifest" href="../assets/images/site.webmanifest">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/themes/airbnb.css">

    <link rel="stylesheet" href="../assets/css/dual_container_global.css">
    <link rel="stylesheet" href="../assets/css/floatingbutton.css">
    <link rel="stylesheet" href="../assets/css/search.css">

    <title>park.me | search insta</title>
</head>
<body>
<input type="hidden" id="userId" value="${user}">
<div class="container">
    <div class="left">
        <img class="logo" src="${pageContext.request.contextPath}assets/images/logo_purple.png" alt="logo">
        <img class="bg-img" src="${pageContext.request.contextPath}assets/images/bg3.jpg" alt="Car">
    </div>
    <div class="right">
        <h1 class="title">park/n go with insta~</h1>
        <img style="margin: 40px; width: 150px;" src="${pageContext.request.contextPath}assets/images/Park.me insta.png" alt="Car">
        <form name="SearchForm"  action="search" method="post" class="search-form">
            <input type="hidden" name="type" value="insta">

            <label for="startDate">when do you want it?</label>
            <input class="input startDate-input" type="text" id="startDate" name="startDate" placeholder="Start Date & Time">

            <label for="vehicleSelect">what vehicle will you leave behind?</label>
            <select id="vehicleSelect" name="vehicle" required class="input" style="margin-bottom: 20px">
                <option selected value="">Select a vehicle</option>
            </select>

            <label for="locationSelect">where do you want us to search?</label>
            <select id="locationSelect" name="location" required class="input" style="margin-bottom: 20px">
                <option selected value="">Select a location</option>
            </select>

            <input class="form-btn" type="submit" name="action" value="continue" placeholder="continue">
            <button class="sec-btn" type="submit" name="action" value="longTerm">looking for long term parking?</button>
        </form>
    </div>
</div>
<button class="floating-button" id="menu-toggle">
    <img src="${pageContext.request.contextPath}assets/images/fi-rs-grid.svg" alt="Park Me Insta" class="menu-icon">
</button>
<div class="menu" id="menu">
    <button onclick="location.href='./dashboard'">dashboard</button>
    <button onclick="location.href='./search'">search</button>
    <button onclick="location.href='./insta'">insta</button>
    <button onclick="location.href='./profile'">profile</button>
</div>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/search_insta_ajax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/hover-menu.js"></script>

<script>
    flatpickr("#startDate", {
        enableTime: true,
        dateFormat: "d/m/Y H:i",
        minDate: "today"
    });

    flatpickr("#endDate", {
        enableTime: true,
        dateFormat: "d/m/Y H:i",
        minDate: "today"
    });
</script>
</body>
</html>
