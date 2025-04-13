<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Vehicle Details | park.me</title>
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
        <img src="images/bg2.jpg" alt="Vehicle" class="w-full h-full object-cover">
    </div>

    <!-- Right Form -->
    <div class="w-full md:w-1/2 p-8">
        <h2 class="text-3xl font-bold text-gray-800 mb-6">Vehicle Details</h2>

        <form action="Signup" method="post" class="space-y-4">
            <input type="hidden" name="step" value="vehicle">

            <div>
                <label for="carType" class="block text-gray-600 font-medium mb-1">Car Type</label>
                <input type="text" id="carType" name="carType" value="<%= request.getParameter("carType") != null ? request.getParameter("carType") : "" %>" required
                       class="w-full border border-gray-300 px-4 py-2 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400">
            </div>

            <div>
                <label for="licensePlate" class="block text-gray-600 font-medium mb-1">License Plate</label>
                <input type="text" id="licensePlate" name="licensePlate" value="<%= request.getParameter("licensePlate") != null ? request.getParameter("licensePlate") : "" %>" required
                       class="w-full border border-gray-300 px-4 py-2 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-400">
            </div>

            <div class="flex justify-col items-center pt-4">



                <!-- Continue Button -->
                <button type="submit"
                        class="bg-yellow-500 text-white font-semibold py-2 px-6 rounded-xl hover:bg-blue-700 transition">
                    Continue to Payment →
                </button>
                <form action="Signup" method="post">
                    <input type="hidden" name="step" value="user">
                    <button type="submit"
                            class="bg-gray-300 text-gray-800 font-semibold py-2 px-4 rounded-xl hover:bg-gray-400 transition">
                        ← Go Back
                    </button>
                </form>
            </div>
        </form>
    </div>
</div>

</body>
</html>
