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
    <link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="manifest" href="images/site.webmanifest">

    <title>park.me | @me</title>
</head>
<body>
<h1>User Details</h1>
<p>Username: ${user.username}</p>
<p>Email: ${user.email}</p>
<p>User Type: ${user.userType}</p>
<p>Car Type: ${user.carType}</p>
<p>License Plate: ${user.licensePlate}</p>
<p>Card Number: ${user.paymentDetails.cardNumber}</p>
<p>Expiry Date: ${user.paymentDetails.expiryDate}</p>
</body>
</html>
