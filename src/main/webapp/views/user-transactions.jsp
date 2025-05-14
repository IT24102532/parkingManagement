<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/assets/images/site.webmanifest">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profiile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard_global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user-dashboard.css">


    <title>park.me | @me | Log</title>
</head>
<body>
<input type="hidden" id="userId" value="${user}">
<div class="container">
    <div class="sidebar">
        <%--        dashboard icon--%>
        <a href="${pageContext.request.contextPath}/dashboard">
            <svg width="24" height="26" viewBox="0 0 24 26" fill="none" xmlns="http://www.w3.org/2000/svg" class="dash-icon">
                <rect width="11" height="14" rx="3" fill="#F1D521"/>
                <rect x="13" y="12" width="11" height="14" rx="3" fill="#F1D521"/>
                <rect x="13" width="11" height="9" rx="3" fill="#EADFF9"/>
                <rect y="17" width="11" height="9" rx="3" fill="#EADFF9"/>
            </svg>
        </a>
        <%--        user icon--%>
        <a href="${pageContext.request.contextPath}/profile">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="user-icon">
                <g clip-path="url(#clip0_200_1350)">
                    <path d="M12 12C13.1867 12 14.3467 11.6481 15.3334 10.9888C16.3201 10.3295 17.0892 9.39246 17.5433 8.2961C17.9974 7.19975 18.1162 5.99335 17.8847 4.82946C17.6532 3.66558 17.0818 2.59648 16.2426 1.75736C15.4035 0.918247 14.3344 0.346802 13.1705 0.115291C12.0067 -0.11622 10.8003 0.00259972 9.7039 0.456726C8.60754 0.910851 7.67047 1.67989 7.01118 2.66658C6.35189 3.65328 6 4.81331 6 6C6.00159 7.59081 6.63424 9.11602 7.75911 10.2409C8.88399 11.3658 10.4092 11.9984 12 12ZM12 2C12.7911 2 13.5645 2.2346 14.2223 2.67412C14.8801 3.11365 15.3928 3.73836 15.6955 4.46927C15.9983 5.20017 16.0775 6.00444 15.9231 6.78036C15.7688 7.55629 15.3878 8.26902 14.8284 8.82843C14.269 9.38784 13.5563 9.7688 12.7804 9.92314C12.0044 10.0775 11.2002 9.99827 10.4693 9.69552C9.73836 9.39277 9.11365 8.88008 8.67412 8.22228C8.2346 7.56449 8 6.79113 8 6C8 4.93914 8.42143 3.92172 9.17157 3.17158C9.92172 2.42143 10.9391 2 12 2V2Z" fill="#374957"/>
                    <path d="M12 14.0005C9.61386 14.0031 7.32622 14.9522 5.63896 16.6395C3.95171 18.3267 3.00265 20.6144 3 23.0005C3 23.2657 3.10536 23.5201 3.29289 23.7076C3.48043 23.8951 3.73478 24.0005 4 24.0005C4.26522 24.0005 4.51957 23.8951 4.70711 23.7076C4.89464 23.5201 5 23.2657 5 23.0005C5 21.144 5.7375 19.3635 7.05025 18.0507C8.36301 16.738 10.1435 16.0005 12 16.0005C13.8565 16.0005 15.637 16.738 16.9497 18.0507C18.2625 19.3635 19 21.144 19 23.0005C19 23.2657 19.1054 23.5201 19.2929 23.7076C19.4804 23.8951 19.7348 24.0005 20 24.0005C20.2652 24.0005 20.5196 23.8951 20.7071 23.7076C20.8946 23.5201 21 23.2657 21 23.0005C20.9974 20.6144 20.0483 18.3267 18.361 16.6395C16.6738 14.9522 14.3861 14.0031 12 14.0005V14.0005Z" fill="#374957"/>
                </g>
                <defs>
                    <clipPath id="clip0_200_1350">
                        <rect width="24" height="24" fill="white"/>
                    </clipPath>
                </defs>
            </svg>
        </a>
        <%--    bookings icon--%>
            <a href="${pageContext.request.contextPath}/logs">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="bookings-icon">
                    <g clip-path="url(#clip0_200_1351)">
                        <path d="M19 2V0H17V2H15V0H13V2H11V0H9V2H7V0H5V2H3V21C3 21.7956 3.31607 22.5587 3.87868 23.1213C4.44129 23.6839 5.20435 24 6 24H18C18.7956 24 19.5587 23.6839 20.1213 23.1213C20.6839 22.5587 21 21.7956 21 21V2H19ZM19 21C19 21.2652 18.8946 21.5196 18.7071 21.7071C18.5196 21.8946 18.2652 22 18 22H6C5.73478 22 5.48043 21.8946 5.29289 21.7071C5.10536 21.5196 5 21.2652 5 21V4H19V21ZM17 9H7V7H17V9ZM17 13H7V11H17V13ZM13 17H7V15H13V17Z" fill="#374957"/>
                    </g>
                    <defs>
                        <clipPath id="clip0_200_1351">
                            <rect width="24" height="24" fill="white"/>
                        </clipPath>
                    </defs>
                </svg>
            </a>

            <%--    paths icon--%>
            <div class="settings-container">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" id="pathsTrigger">
                    <path d="M23 10.9997H1C0.447715 10.9997 0 11.4474 0 11.9997C0 12.552 0.447715 12.9997 1 12.9997H23C23.5523 12.9997 24 12.552 24 11.9997C24 11.4474 23.5523 10.9997 23 10.9997Z" fill="#374957"/>
                    <path d="M23 4.00031H1C0.447715 4.00031 0 4.44802 0 5.0003C0 5.55259 0.447715 6.0003 1 6.0003H23C23.5523 6.0003 24 5.55259 24 5.0003C24 4.44802 23.5523 4.00031 23 4.00031Z" fill="#374957"/>
                    <path d="M23 18H1C0.447715 18 0 18.4477 0 19C0 19.5523 0.447715 20 1 20H23C23.5523 20 24 19.5523 24 19C24 18.4477 23.5523 18 23 18Z" fill="#374957"/>
                </svg>

                <div class="settings-tooltip" id="pathsMenu">
                    <a href="${pageContext.request.contextPath}/">home</a>
                    <a href="${pageContext.request.contextPath}/about">about</a>
                    <a href="${pageContext.request.contextPath}/services">services</a>
                    <a href="${pageContext.request.contextPath}/contact">contact</a>

                </div>
            </div>
        <%--   settings icon--%>
            <div class="settings-container">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="settings-icon" id="settingsTrigger">
                    <g clip-path="url(#clip0_200_1353)">
                        <path d="M15.0004 24H9.00042V20.487C7.95768 20.1181 6.99187 19.5601 6.15142 18.841L3.10742 20.6L0.107422 15.4L3.15042 13.645C2.95053 12.5574 2.95053 11.4426 3.15042 10.355L0.107422 8.6L3.10742 3.4L6.15142 5.159C6.99187 4.43993 7.95768 3.88194 9.00042 3.513V0H15.0004V3.513C16.0432 3.88194 17.009 4.43993 17.8494 5.159L20.8934 3.4L23.8934 8.6L20.8504 10.355C21.0503 11.4426 21.0503 12.5574 20.8504 13.645L23.8934 15.4L20.8934 20.6L17.8494 18.842C17.0089 19.5607 16.0431 20.1184 15.0004 20.487V24ZM11.0004 22H13.0004V18.973L13.7514 18.779C14.9834 18.4598 16.1048 17.8101 16.9944 16.9L17.5374 16.347L20.1604 17.862L21.1604 16.13L18.5404 14.617L18.7464 13.871C19.0851 12.6439 19.0851 11.3481 18.7464 10.121L18.5404 9.375L21.1604 7.862L20.1604 6.13L17.5374 7.649L16.9944 7.1C16.1043 6.19134 14.983 5.54302 13.7514 5.225L13.0004 5.027V2H11.0004V5.027L10.2494 5.221C9.01741 5.54015 7.89603 6.18988 7.00642 7.1L6.46342 7.653L3.84042 6.134L2.84042 7.866L5.46042 9.379L5.25442 10.125C4.91578 11.3521 4.91578 12.6479 5.25442 13.875L5.46042 14.621L2.84042 16.134L3.84042 17.866L6.46342 16.351L7.00642 16.904C7.89651 17.8127 9.01785 18.461 10.2494 18.779L11.0004 18.973V22ZM12.0004 16C11.2093 16 10.4359 15.7654 9.77814 15.3259C9.12034 14.8864 8.60765 14.2616 8.3049 13.5307C8.00215 12.7998 7.92294 11.9956 8.07728 11.2196C8.23162 10.4437 8.61258 9.73098 9.17199 9.17157C9.7314 8.61216 10.4441 8.2312 11.2201 8.07686C11.996 7.92252 12.8003 8.00173 13.5312 8.30448C14.2621 8.60723 14.8868 9.11992 15.3263 9.77772C15.7658 10.4355 16.0004 11.2089 16.0004 12C16.0004 13.0609 15.579 14.0783 14.8288 14.8284C14.0787 15.5786 13.0613 16 12.0004 16ZM12.0004 10C11.6049 10 11.2182 10.1173 10.8893 10.3371C10.5604 10.5568 10.304 10.8692 10.1527 11.2346C10.0013 11.6001 9.96168 12.0022 10.0389 12.3902C10.116 12.7781 10.3065 13.1345 10.5862 13.4142C10.8659 13.6939 11.2223 13.8844 11.6102 13.9616C11.9982 14.0387 12.4003 13.9991 12.7658 13.8478C13.1312 13.6964 13.4436 13.44 13.6634 13.1111C13.8831 12.7822 14.0004 12.3956 14.0004 12C14.0004 11.4696 13.7897 10.9609 13.4146 10.5858C13.0396 10.2107 12.5309 10 12.0004 10Z" fill="#374957"/>
                    </g>
                    <defs>
                        <clipPath id="clip0_200_1353">
                            <rect width="24" height="24" fill="white"/>
                        </clipPath>
                    </defs>
                </svg>
                <div class="settings-tooltip" id="settingsMenu">
                    <a href="${pageContext.request.contextPath}/logout">Log Out</a>
                    <a href="${pageContext.request.contextPath}/logout?action=switch">Switch Account</a>
                    <a href="#" id="deleteAcc" style="color: darkred" onclick="openDeleteModal()">Delete Account</a>
                </div>
            </div>
    </div>
    <div class="dashboard">
        <div class="action-bar">
            <div class="action-left">
                <h1 class="title">welcome back <span id="user"></span></h1>
            </div>
            <div class="action-right">
                <button class="new-insta-btn" id="newInstaBtn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-3"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
                    new insta~</button>
                <button class="new-book-btn" id="newBookBtn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-3"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
                    new booking</button>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bell"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>
                <img class="profile-btn" src="https://avatar.iran.liara.run/public/boy" alt="user image">
            </div>
        </div>
        <div class="log-content">
            <div class="booking-history log-history">
                <h2 class="section-header">recent bookings</h2>
                <table class="booking-table" id="booking-table">
                    <thead>
                    <tr>
                        <th>Location</th>
                        <th>Date</th>
                        <th>Charge</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Downtown Hub</td>
                        <td>2023-03-15</td>
                        <td>$18.00</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="booking-history">
                <h2 class="section-header">recent transactions</h2>
                <table class="booking-table" id="transaction-table">
                    <thead>
                    <tr>
                        <th>Location</th>
                        <th>Date</th>
                        <th>Charge</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Downtown Hub</td>
                        <td>2023-03-15</td>
                        <td>$18.00</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-modal" id="deleteModal">
            <div class="modal-content">
                <span class="close" onclick="closeDeleteModal()">&times;</span>
                <h3>Delete account</h3>
                <p style="font-weight: 500; color: darkred; padding: 20px 0"> You're attempting to delete your account, are you sure?</p>
                <form id="deleteForm" action="${pageContext.request.contextPath}/delete" method="post">
                    <input type="hidden" name="id" value="${user}">
                    <div class="form-group">
                        <label>password</label>
                        <input type="password" id="deletePassword" name="password" required>
                    </div>
                    <button type="submit" class="save-btn" style="background: darkred; font-weight: 500; color: white">delete my account</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/profile.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/user_logs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/user_search_ajax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/tooltip.js"></script>
<script>
    document.getElementById("newBookBtn").addEventListener("click", () => {
        console.log("Button clicked!")
        window.location.href = "${pageContext.request.contextPath}/search";
    });
    document.getElementById("newInstaBtn").addEventListener("click", () => {
        console.log("Button clicked!")
        window.location.href = "${pageContext.request.contextPath}/insta";
    });
</script>
</body>
</html>

