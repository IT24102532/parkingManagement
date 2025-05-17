<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Boolean status = (Boolean) request.getAttribute("status");
%>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/assets/images/site.webmanifest">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">

    <title>park.me | delete →</title>
</head>
<body>
<style>
    body {
        display: grid;
        justify-content: center;
        align-content: center;
    }
    .notice {
        width: 300px;
        height: auto;
        display: grid;
        grid-auto-columns: 1fr;
        border: 1px solid gray;
        border-radius: 5px;
        padding: 20px;
        justify-items: center;
        box-shadow: 0px 2px 4px rgba(0,0,0, 0.2);
    }

    .notice svg {
        width: 40px;
        height: 40px;
    }

    .notice p {
        font-family: "Inter", sans-serif;
        padding: 20px;
        font-size: 0.9rem;
        text-align: center;
    }

    .notice img {
        margin: auto;
        width: 250px;
        height: auto;
    }

    .notice button {
        padding: 10px;
        width: inherit;
        font-family: "Inter", sans-serif;
        color: black;
        background: #FFD700;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
</style>
<% if (status != null && status) { %>
<div class="notice" id="success">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="green" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-check-circle"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg>
    <p>your account has been successfully deleted!</p>
    <img src="${pageContext.request.contextPath}/assets/images/sad-cat.jpg" alt="sad-cat">
    <p>we're sad to see you go!</p>
    <button id="successfwd">Bye 👋</button>
</div>
<% } else { %>
<div class="notice" id="failure">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="red" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-alert-circle"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
    <p>There was an error updating your account, please try again!</p>
    <button id="failurefwd">OK!</button>
</div>
<% } %>
<script>
    const successBtn = document.getElementById("successfwd");
    const failureBtn = document.getElementById("failurefwd");

    if (successBtn) {
        successBtn.addEventListener('click', () => {
            window.location.href = "${pageContext.request.contextPath}/";
        });
    }

    if (failureBtn) {
        failureBtn.addEventListener('click', () => {
            window.location.href = "${pageContext.request.contextPath}/profile";
        });
    }
</script>
</body>
</html>
