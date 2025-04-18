<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="apple-touch-icon" sizes="180x180" href="../assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="../assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/images/favicon-16x16.png">
    <link rel="manifest" href="../assets/images/site.webmanifest">
    <link rel="stylesheet" href="../assets/css/dual_container_global.css">
    <link rel="stylesheet" href="../assets/css/vehicleDetails.css">
    <title>Vehicle Details | park.me</title>
</head>
<body>
<div class="container">
    <div class="left">
        <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="logo">
        <img class="bg-img" src="${pageContext.request.contextPath}/assets/images/bg2.jpg" alt="Car">
    </div>
    <div class="right">
        <h1 class="title">let's add your vehicle details</h1>
        <form action="../signup" method="post" class="form">
            <input type="hidden" name="step" value="vehicle">

            <label for="carType">car type</label>
            <select id="carType" name="carType" class="input">
                <option value="Sedan">Sedan</option>
                <option value="SUV">SUV</option>
                <option value="Truck">Truck</option>
                <option value="Van">Van</option>
                <option value="Hatchback">Hatchback</option>
            </select>

            <label for="regLocation">registered counrty</label>
            <input type="text" id="regLocation" name="regCountry"
                   value="<%= request.getParameter("regCountry") != null ? request.getParameter("regCountry") : "" %>"
                   required class="input"
                   style="margin-bottom: 20px"
            >

            <h3 class="subtitle">liscence plate</h3>
            <div class="license">
                <input type="text" id="regState" name="regState"
                       value="<%= request.getParameter("regState") != null ? request.getParameter("regState") : "" %>"
                       placeholder="State" required>
                <input type="text" id="licensePlate" name="licensePlate"
                       value="<%= request.getParameter("licensePlate") != null ? request.getParameter("licensePlate") : "" %>"
                       placeholder="Plate Number" required>
            </div>


            <div class="form-buttons">
                <button type="submit" class="form-btn">continue to payment →</button>
                <a href="?step=user" class="back-btn">← go Back</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
