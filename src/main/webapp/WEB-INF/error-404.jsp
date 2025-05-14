<%@ page isErrorPage="true" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
    <link rel="manifest" href="../assets/images/site.webmanifest">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/error.css">
    <title>not found! 😟</title>
</head>
<body>
<div class="container">
    <h1>404 - Page Not Found</h1>
    <p>Sorry, the page you're looking for doesn't exist.</p>
    <button id="backHome">let's get you back home &#128522;</button>
</div>
</body>
<script>
    document.getElementById("backHome").addEventListener('click', () => {
        window.location.href = "${pageContext.request.contextPath}/dashboard";
    });
</script>
</html>
