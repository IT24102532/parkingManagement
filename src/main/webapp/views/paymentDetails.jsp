<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Details | park.me</title>
    <link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="manifest" href="images/site.webmanifest">
    <link rel="stylesheet" href="assets/css/dual_container_global.css">
    <link rel="stylesheet" href="assets/css/paymentdetails.css">
</head>
<body>
<div class="container">
    <!-- Left Side -->
    <div class="left">
        <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="logo">
        <img class="bg-img" src="${pageContext.request.contextPath}/assets/images/bg3.jpg" alt="Payment">
    </div>

    <!-- Right Side -->
    <div class="right">
        <h1 class="title">add your payment details</h1>

        <form action="./signup" method="post" class="form">
            <input type="hidden" name="step" value="payment">

            <label for="cardHolder">name on the card</label>
            <input type="text" id="cardHolder" name="cardHolder" required
                   class="input" placeholder="John Doe">

            <label for="cardNumber">card number</label>
            <input type="text" id="cardNumber" name="cardNumber" required
                   class="input" placeholder="1234 5678 9012 3456">


            <h3 class="subtitle">card details</h3>
            <div class="split-inputs">
                <input type="text" id="expiry" name="expiry" required
                       class="input" placeholder="MM/YY">
                <input type="text" id="cvv" name="cvv" required
                       class="input" placeholder="CVV">
            </div>

            <div class="form-buttons">
                <button type="submit" class="form-btn">take me to park | me</button>
                <a href="?step=vehicle" class="back-btn">← Go Back</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
