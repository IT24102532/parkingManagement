<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/users.css">
</head>
<body>
<div class="sidebar">
</div>

<div class="main-content">
    <header>
        <h2>Dashboard</h2>
        <input type="text" placeholder="search a user">
    </header>

    <div class="dashboard-content">
        <div class="left-section">
            <div class="card users">
                <div class="users-header">
                    <h2>users</h2>
                    <hr>
                </div>
                <div class="users-grid"> <div class="user-stat">
                    <span class="stat-number">5</span>
                    <span class="stat-label">new users today</span>
                </div>
                    <div class="user-stat">
                        <span class="stat-number">16</span>
                        <span class="stat-label">new cancellations</span>
                    </div>
                    <div class="user-stat">
                        <span class="stat-number">155</span>
                        <span class="stat-label">total users this month</span>
                    </div>
                    <div class="user-stat">
                        <span class="stat-number">80</span>
                        <span class="stat-label">new users this month</span>
                    </div>
                    <div class="user-stat">
                        <span class="stat-number">118</span>
                        <span class="stat-label">new cancellations this month</span>
                    </div>
                    <div class="user-stat">
                        <span class="stat-number">15,400</span>
                        <span class="stat-label">total users</span>
                    </div>
                </div>
            </div>

            <div class="new-accounts-card">
                <div class="new-accounts-header">
                    <h2>new account</h2>
                    <a href="#">view all →</a>
                </div>
                <ul class="new-accounts-list">
                    <li class="new-account-item">
                        <div class="new-account-avatar">
                            <img src="path/to/avatar.jpg" alt="Samantha Arias">
                        </div>
                        <div class="new-account-details">
                            <span class="new-account-name">samantha arias</span>
                            <span class="new-account-location">📍 London, England</span>
                        </div>
                    </li>
                    <li class="new-account-item">
                        <div class="new-account-avatar">
                            <img src="path/to/avatar.jpg" alt="Samantha Arias">
                        </div>
                        <div class="new-account-details">
                            <span class="new-account-name">samantha arias</span>
                            <span class="new-account-location">📍 London, England</span>
                        </div>
                    </li>
                    <li class="new-account-item">
                        <div class="new-account-avatar">
                            <img src="path/to/avatar.jpg" alt="Samantha Arias">
                        </div>
                        <div class="new-account-details">
                            <span class="new-account-name">samantha arias</span>
                            <span class="new-account-location">📍 London, England</span>
                        </div>
                    </li>
                    <li class="new-account-item">
                        <div class="new-account-avatar">
                            <img src="path/to/avatar.jpg" alt="Samantha Arias">
                        </div>
                        <div class="new-account-details">
                            <span class="new-account-name">samantha arias</span>
                            <span class="new-account-location">📍 London, England</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="right-section">
            <div class="card user-details">
                <div class="user-details-header">
                    <h2>User Details</h2>
                    <div class="actions">
                        <button class="edit">Edit</button>
                        <button class="ban">Ban</button>
                    </div>
                </div>
                <div class="user-info">
                    <div class="user-avatar">👤</div>
                    <div class="user-name">${user.name}</div>
                    <div class="user-location">${user.location}</div>
                    <div class="joined">joined: ${user.joinedDate}</div>
                </div>
                <table>
                    <tr><td>Name</td><td>${user.name}</td></tr>
                    <tr><td>Location</td><td>${user.location}</td></tr>
                    <tr><td>Email</td><td>${user.email}</td></tr>
                    <tr><td>Vehicles</td><td>${user.vehicles}</td></tr>
                    <tr><td>Account Status</td><td>${user.accountStatus}</td></tr>
                    <tr><td>Total Sales</td><td>${user.totalSales}</td></tr>
                    <tr><td>Total Bookings</td><td>${user.totalBookings}</td></tr>
                    <tr><td>Active Bookings</td><td>${user.activeBookings}</td></tr>
                    <tr><td>Satisfaction</td><td>${user.satisfaction}</td></tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>