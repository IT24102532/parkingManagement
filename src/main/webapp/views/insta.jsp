<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Park.me Insta</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="flex justify-center items-center min-h-screen bg-[#fefef5]">

<!-- Main Container -->
<div class="flex w-4/5 h-[90vh] shadow-lg rounded-xl overflow-hidden">

    <!-- Left side (Image) -->
    <div class="w-1/2 relative">
        <img src="../assets/images/bg2.jpg
" alt="Car" class=" w-full object-cover">
        <img src="../assets/images/logo_purple.png" alt="Logo" class="absolute top-10 left-10 w-20">
        <button class="absolute bottom-5 left-5 bg-yellow-400 p-3 rounded-md">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-white" viewBox="0 0 20 20" fill="currentColor">
                <path d="M4 3a2 2 0 00-2 2v1a2 2 0 002 2v7a2 2 0 002 2h8a2 2 0 002-2V8a2 2 0 002-2V5a2 2 0 00-2-2H4zm0 2h12v1H4V5zm0 3h12v7H4V8z"/>
            </svg>
        </button>
    </div>

    <!-- Right side (Form) -->
    <div class="w-1/2 bg-white p-12 flex flex-col justify-center items-center">
        <h1 class="text-2xl font-semibold mb-10 text-gray-800">
            park.me <span class="italic text-gray-500">insta~</span>
        </h1>
        <form class="w-full max-w-sm space-y-5">
            <input type="text" placeholder="when?" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-yellow-400">
            <input type="text" placeholder="where?" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-yellow-400">
            <button type="submit" class="w-full bg-yellow-400 hover:bg-yellow-500 text-white font-bold py-2 rounded-md transition duration-200">
                search
            </button>
        </form>
    </div>

</div>

</body>
</html>
