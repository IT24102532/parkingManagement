<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>welcome to park.me</title>

    <link rel="apple-touch-icon" sizes="180x180" href="assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/favicon-16x16.png">
    <link rel="manifest" href="assets/images/site.webmanifest">

    <link rel="stylesheet" href="assets/css/landingbase.css">
</head>
<body>
<div class="container" id="container">
    <div class="nav-bar">
        <div class="nav-links">
            <a href="./views/signup.jsp" class="nav-link">sign up</a>
            <a href="./login" class="nav-link ctr">sign in</a>
        </div>
    </div>
    <div class="hero-container">
        <img src="${pageContext.request.contextPath}assets/images/bg1.jpg" alt="hero image" class="hero-image">
        <img src="${pageContext.request.contextPath}assets/images/bg2.jpg" alt="hero image" class="hero-image">
        <img src="${pageContext.request.contextPath}assets/images/bg3.jpg" alt="hero image" class="hero-image">
        <img src="${pageContext.request.contextPath}assets/images/bg4.jpg" alt="hero image" class="hero-image">
        <img src="${pageContext.request.contextPath}assets/images/bg5.jpg" alt="hero image" class="hero-image">
        <img src="${pageContext.request.contextPath}assets/images/squibbles.svg" alt="squibbles" class="squibbles">
        <p class="hero-title">find the perfect spot for your car!</p>
        <p>
            <span class="hero-underline">
            <svg width="150" height="17" viewBox="0 0 150 17" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M82.0533 7.59234C64.7157 8.03691 47.3781 9.13637 30.1133 10.9266C20.6834 11.9042 9.73145 11.787 0.583165 15.1189C-0.137514 15.3818 0.00623316 16.1825 0.0219857 16.2614C0.0534908 16.4263 0.18542 16.9736 0.762357 16.9999C0.807645 17.0023 1.1168 16.9546 1.23691 16.9354C3.93059 16.5052 6.6164 16.0033 9.31205 15.5969C18.6238 14.1891 27.9591 13.1279 37.3102 12.1958C49.7232 10.9577 62.2406 10.2956 74.687 9.78169C81.502 9.49727 88.4784 9.87251 95.3169 9.2917C97.8216 9.27497 100.33 9.27263 102.839 9.28219C113.328 9.32761 123.805 9.81041 134.281 10.4725C137.648 10.6852 140.22 10.9147 143.52 11.07C144.774 11.1298 146.765 11.1704 148.147 11.1967C148.348 11.2015 148.852 11.2062 149.07 11.2086C149.1 11.2134 149.13 11.2134 149.159 11.2134C149.265 11.211 149.319 11.2038 149.328 11.2014C150.079 11.0676 150.01 10.2119 149.988 10.0781C149.984 10.0494 149.874 9.32517 149.232 9.29888C149.135 9.2941 148.44 9.28936 148.173 9.28458C146.8 9.25829 144.825 9.21766 143.581 9.16029C140.288 9.00493 137.723 8.77548 134.362 8.56276C123.863 7.90069 113.36 7.41549 102.845 7.37008C101.616 7.3653 100.389 7.3629 99.1645 7.36529C99.0739 7.22188 98.9341 7.09038 98.7116 7.02107C98.5088 6.95653 97.0517 6.89444 96.4885 6.83708C92.4204 6.43315 92.5406 6.44987 88.0629 6.11765C81.2814 5.61333 80.2496 5.49855 73.1984 5.28582C64.568 5.0253 55.9356 4.95361 47.3032 4.94644C59.7005 4.21984 72.1804 4.31782 84.5599 4.09793C90.158 3.99993 95.7541 3.82308 101.352 3.87566C103.274 3.89478 105.194 3.98084 107.116 4.00952C107.513 4.01669 108.535 4.10034 108.683 4.05971C109.171 3.92825 109.276 3.48606 109.305 3.27095C109.321 3.14666 109.392 2.41051 108.665 2.15238C105.422 1.00511 99.4382 1.05051 96.2877 0.823445C78.6449 -0.44333 60.9666 -0.0895963 43.3158 0.816268C36.0283 1.18913 28.7644 1.72216 21.5065 2.58262C18.9683 2.88138 16.4243 3.13713 13.896 3.53867C13.4865 3.60321 12.563 3.69638 12.116 3.79438C11.8856 3.84696 11.7222 3.92344 11.6454 3.97364C11.287 4.21982 11.226 4.58314 11.224 4.82693C11.222 5.0038 11.2594 5.66587 11.9841 5.83557C19.3543 7.58754 27.3723 6.90399 34.7819 6.88009C47.575 6.84184 60.37 6.81073 73.159 7.19794C77.4851 7.32939 79.5368 7.42264 82.0533 7.59234ZM85.7374 2.16434C71.6232 1.63134 57.4931 2.00178 43.3808 2.72599C36.1229 3.09885 28.8885 3.62949 21.6601 4.48516C20.5082 4.6214 19.3582 4.74806 18.2083 4.88429C23.7276 5.36471 29.4162 4.98709 34.778 4.96797L38.4582 4.9584C38.4286 4.86758 38.409 4.76959 38.403 4.6692C38.3696 4.14337 38.6944 3.68206 39.1276 3.64143C54.1949 2.25276 69.4395 2.45351 84.5383 2.18581C84.938 2.17864 85.3377 2.17151 85.7374 2.16434Z" fill="white"/></svg>
            </span>
        </p>
    </div>
    <div class="content-sec">
        <div class="main-sec">
            <img src="${pageContext.request.contextPath}assets/images/logo_purple.svg" alt="Logo">
            <p class="content-title">find the <span style="font-weight: 600">perfect spot</span>—fast & secure!</p>
            <img src="${pageContext.request.contextPath}assets/images/thums_up.png" alt="thumbs up" class="thumbs-up">
            <p class="content-desc">Your car deserves a safe place while you're away. Reserve a spot in seconds and park stress-free</p>
            <button class="btn secondary">find out how it works</button>
            <button class="btn ctr" id="getStarted">get started</button>
            <img src="${pageContext.request.contextPath}assets/images/excited.svg" alt="thumbs up" class="excited-yellow">

        </div>
        <div class="insta-sec">
            <img src="${pageContext.request.contextPath}assets/images/insta_logo_yellow.png" alt="Park Me Insta" class="insta-logo">
            <img src="${pageContext.request.contextPath}assets/images/fi-rs-car.png" alt="Park Me Insta" class="small-car">
            <p class="insta-title"><span style="font-weight: 600">not staying for long?</span>—we got you!</p>
            <p class="insta-desc">coming for a drop by? don’t worry, with our introduced <span style="font-weight: 500; color: hsl(250, 100%, 69%, 100%)">park.me insta</span> , you can park for shorter durations!</p>
            <button class="btn secondary" id="instaBtn">get started with insta~</button>
            <img src="${pageContext.request.contextPath}assets/images/stars.svg" alt="Park Me Insta" class="insta-stars">

        </div>
    </div>


</div>
<button class="floating-button" id="menu-toggle">
    <img src="${pageContext.request.contextPath}assets/images/fi-rs-grid.svg" alt="Park Me Insta" class="menu-icon">
</button>
<div class="menu" id="menu">
    <button onclick="location.href='${pageContext.request.contextPath}/'">Home</button>
    <button onclick="location.href='${pageContext.request.contextPath}/about'">About</button>
    <button onclick="location.href='${pageContext.request.contextPath}/services'">Services</button>
    <button onclick="location.href='${pageContext.request.contextPath}/contact'">Contact</button>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}assets/js/slideshow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}assets/js/hover-menu.js"></script>
<script>
    document.getElementById("instaBtn").addEventListener("click", () => {
        console.log("Button clicked!")
        window.location.href = "./insta";
    });
    document.getElementById("getStarted").addEventListener("click", () => {
        console.log("Button clicked!")
        window.location.href = "./search";
    });
</script>
</body>
</html>