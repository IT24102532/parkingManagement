<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/assets/images/site.webmanifest">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/themes/airbnb.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dual_container_global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/floatingbutton.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/search.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/search_result.css">

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
        <h1 class="title">find a long term spot</h1>
        <p class="subtitle">we found you some spots!</p>

        <div class="scroll-buttons">
            <button id="scrollLeft" class="left-btn">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-arrow-left-circle"><circle cx="12" cy="12" r="10"/><polyline points="12 8 8 12 12 16"/><line x1="16" y1="12" x2="8" y2="12"/></svg>
            </button>
            <button id="scrollRight" class="right-btn">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-arrow-right-circle"><circle cx="12" cy="12" r="10"/><polyline points="12 16 16 12 12 8"/><line x1="8" y1="12" x2="16" y2="12"/></svg>
            </button>
        </div>

        <div class="result-container" id="resultContainer"></div>

        <template id="resultTemplate">
            <form action="${pageContext.request.contextPath}/book/new" method="post" class="result">
                <input type="hidden" name="userId" value="${user}" />
                <input type="hidden" name="slotId" class="slotId" />
                <input type="hidden" name="startDate" class="inputStartDate" />
                <input type="hidden" name="endDate" class="inputEndDate" />

                <h2 class="title"></h2>
                <p class="location_name"></p>
                <div class="dates">
                    <p class="startDate"></p>
                    <p class="endDate"></p>
                </div>
                <p class="promoCode">No available promotions</p>
                <p><span class="amount"></span> <span style="font-size: 14px; color: gray">/day</span></p>

                <button type="submit" class="btn continue">Choose</button>
            </form>
        </template>


    <%--        <div class="result">--%>
<%--            <h2 class="titile">Los Angelis Airport</h2>--%>
<%--            <p class="location_name">back terminal</p>--%>
<%--            <div class="dates">--%>
<%--                <p class="startDate">start date</p>--%>
<%--                <p class="endDate">End Date</p>--%>
<%--            </div>--%>
<%--            <p class="promoCode">No available promotions</p>--%>
<%--            <p class="amount">80$</p>--%>
<%--            <button class="btn continue">choose</button>--%>
<%--        </div>--%>
    </div>
</div>
<button class="floating-button" id="menu-toggle">
    <img src="${pageContext.request.contextPath}assets/images/fi-rs-grid.svg" alt="Park Me Insta" class="menu-icon">
</button>
<div class="menu" id="menu">
    <button onclick="location.href='${pageContext.request.contextPath}/dashboard'">dashboard</button>
    <button onclick="location.href='${pageContext.request.contextPath}/search'">search</button>
    <button onclick="location.href='${pageContext.request.contextPath}/insta'">insta</button>
    <button onclick="location.href='${pageContext.request.contextPath}/profile'">profile</button>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/hover-menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/search_long_term.js"></script>
<script>

    const scrollContainer = document.querySelector('.result-container');
    const scrollLeftBtn = document.getElementById('scrollLeft');
    const scrollRightBtn = document.getElementById('scrollRight');

    scrollLeftBtn.addEventListener('click', () => {
        scrollContainer.scrollBy({ left: -300, behavior: 'smooth' });
    });

    scrollRightBtn.addEventListener('click', () => {
        scrollContainer.scrollBy({ left: 300, behavior: 'smooth' });
    });
</script>
</body>
</html>