<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up | park.me</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="manifest" href="images/site.webmanifest">
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen p-4">

<div class="flex flex-col md:flex-row w-full max-w-5xl bg-white rounded-3xl shadow-xl overflow-hidden">

    <!-- Left Image -->
    <div class="md:w-1/2 hidden md:block">
        <img src="images/bg1.jpg" alt="Parking" class="w-full h-full object-cover">
    </div>

    <!-- Right Form -->
    <div class="w-full md:w-1/2 p-8">
        <h2 class="text-3xl font-bold text-gray-800 mb-6 text-center">Create Your Account</h2>

        <% if (request.getAttribute("error") != null) { %>
        <div class="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="Signup"  method="post" class="space-y-4" onsubmit="return validatePassword();">

            <input  type="hidden" name="step" value="user">

            <input type="text" name="username" placeholder="Username"
                   class="w-full p-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-yellow-400" required>

            <input type="email" name="email" placeholder="Email"
                   class="w-full p-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-yellow-400" required>

            <input type="password" name="password" id="password" placeholder="Password"
                   class="w-full p-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-yellow-400" required>

            <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password"
                   class="w-full p-3 border rounded-xl focus:outline-none focus:ring-2 focus:ring-yellow-400" required>



            <button type="submit"

                    class="w-full bg-yellow-400 hover:bg-yellow-500 text-white font-bold py-3 rounded-xl transition duration-300">
                Continue
            </button>

            <div class="text-center text-gray-600 mt-4">
                Already have an account? <a href="login.jsp" class="text-yellow-500 hover:underline">Log In</a>
            </div>
        </form>
    </div>
</div>

<!-- JS for Confirm Password validation -->
<script>
    function validatePassword() {
        const pass = document.getElementById("password").value;
        const confirm = document.getElementById("confirmPassword").value;

        if (pass !== confirm) {
            alert("Passwords do not match!");
            return false;
        }
        return true;
    }
</script>
</body>
</html>