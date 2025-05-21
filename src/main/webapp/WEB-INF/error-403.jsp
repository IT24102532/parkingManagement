<%@ page isErrorPage="true" %>
<%
    String referer = request.getHeader("referer");
    if (referer == null) {
        referer = request.getContextPath() + "/"; // fallback to home if no referer
    }
%>
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
    <h1>You're not supposed to go here!</h1>
    <img src="${pageContext.request.contextPath}/assets/images/forbidden-cat.jpg" alt="forbidden-cat">
    <p>Sorry, the page you're looking for is not available for you.</p>
    <button id="backHome">let's get you back home &#128522;</button>
</div>
</body>
<script>
    document.getElementById("backHome").addEventListener('click', () => {
        window.location.href = "${pageContext.request.contextPath}/<%= referer %>";
    });
</script>
</html>
