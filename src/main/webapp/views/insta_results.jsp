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
    <link rel="stylesheet" href="../assets/css/search_result.css">

    <title>park.me | search</title>
</head>
<body>
<input type="hidden" id="userId" value="${user}">
<input type="hidden" id="reqStartDate" value="${startDate}">
<input type="hidden" id="reqEndDate" value="${endDate}">
<input type="hidden" id="reqVehicle" value="${vehicle}">
<input type="hidden" id="reqLocation" value="${location}">

<div class="container">
    <div class="left">
        <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="logo">
        <img class="bg-img" src="${pageContext.request.contextPath}/assets/images/bg3.jpg" alt="Car">
    </div>
    <div class="right">
        <h1 class="title">park.me insta~</h1>
        <p class="subtitle">we found you a spot!</p>

        <form action="${pageContext.request.contextPath}/book/new" method="post" class="result">
            <input type="hidden" name="userId" value="${user}" />
            <input type="hidden" name="slotId" class="slotId" />
            <input type="hidden" name="endDate" class="inputEndDate" />
            <input type="hidden" name="startDate" class="inputStartDate" />
            <input type="hidden" name="type" value="insta">

            <h2 class="result-title"></h2>
            <p class="location_name"></p>
            <div class="dates">
                <p class="startDate"></p>
                <p class="endDate"></p>
            </div>
            <p class="promoCode">No available promotions</p>
            <p><span class="amount"></span> <span style="font-size: 14px; color: gray">/hour</span></p>

            <button type="submit" class="btn continue">Choose</button>
            <button type="submit" class="sec-btn btn" id="next">something different?</button>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/hover-menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/search_insta.js"></script>
</body>
</html>