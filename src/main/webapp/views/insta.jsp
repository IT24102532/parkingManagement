<%--
  Created by IntelliJ IDEA.
  User: kavindu
  Date: 3/10/2025
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="apple-touch-icon" sizes="180x180" href="images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon-16x16.png">
    <link rel="manifest" href="images/site.webmanifest">

    <title>park.me | insta</title>
</head>
<body class="bg-[#fefeee]">
<div class="min-h-screen flex rounded-xl overflow-hidden shadow-lg max-w-6xl mx-auto mt-10">

    <!-- Left Side - Image -->
    <div class="w-1/2 relative">
        <img src="../assets/images/bg1.jpg" alt="Car" class="object-cover w-full h-full">
        <div class="absolute top-5 left-5">
            <span class="text-purple-600 text-xl font-bold bg-purple-100 rounded-full px-4 py-1">Park.me</span>
        </div>
        <div class="absolute bottom-5 left-5">
            <div class="bg-yellow-400 p-2 rounded">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-white" fill="none"
                     viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M4 6h16M4 12h16M4 18h16"/>
                </svg>
            </div>
        </div>
    </div>

    <!-- Right Side - Form -->
    <div class="w-1/2 bg-white flex flex-col justify-center px-16">
        <h2 class="text-2xl font-semibold text-gray-800 mb-8 text-center">
            <span class="text-gray-700">park.me</span> <em class="text-gray-400">insta~</em>
        </h2>

        <form action="/findlocation" method="post" class="space-y-6">
            <div>
                <input type="text" name="when" placeholder="when?" required
                       class="w-full border border-gray-300 px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-yellow-400"/>
            </div>
            <div>
                <input type="text" name="where" placeholder="where?" required
                       class="w-full border border-gray-300 px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-yellow-400"/>
            </div>
            <div>
                <button type="submit"
                        class="w-full bg-yellow-400 hover:bg-yellow-500 text-white font-semibold py-2 rounded transition duration-200">
                    search
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
