<%@ page isErrorPage="true" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
    <link rel="manifest" href="../assets/images/site.webmanifest">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/error.css">
    <title>oh no! 😟</title>
</head>
<body>
<div class="container">
    <h1>Uh Oh!</h1>
    <img src="${pageContext.request.contextPath}/assets/images/cat-banned.jpg" alt="forbidden-cat">
    <p style="max-width: 450px; text-align: center;">Sorry, looks like you aren't supposed to be here! you've been banned! Please reach us if you wish to be appealed or if you think this was a mistake!</p>
    <button id="backHome">let's get you in contact &#128221;</button>
</div>
</body>
<script>
    document.getElementById("backHome").addEventListener('click', () => {
        window.location.href = "${pageContext.request.contextPath}/contact";
    });
</script>
</html>
