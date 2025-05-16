<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>park.me | Sign Up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dual_container_global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dual_container_global.css" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<div class="container">
    <!-- Left Side -->
    <div class="left">
        <img src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="Logo" class="logo" />
        <img src="${pageContext.request.contextPath}/assets/images/bg2.jpg" alt="Background" class="bg-img" />
    </div>

    <!-- Right Side (Sign Up Form) -->
    <div class="right">
        <h2 class="title text-2xl font-semibold mb-10">let's create your account</h2>
        <form action="${pageContext.request.contextPath}/signup" method="post" class="flex flex-col w-[400px] items-center">
            <input type="hidden" name="step" value="user">
            <div class="flex gap-2">
                <div class="w-full mb-6">
                    <label for="f_name" class="block text-sm text-gray-700 mb-2">first name</label>
                    <input type="text" id="f_name" name="f_name" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
                </div>
                <div class="w-full mb-6">
                    <label for="l_name" class="block text-sm text-gray-700 mb-2">last name</label>
                    <input type="text" id="l_name" name="l_name" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
                </div>
            </div>


            <div class="w-full mb-6">
                <label for="email" class="block text-sm text-gray-700 mb-2">email</label>
                <input type="email"  name="email" id="email" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <div class="w-full mb-6">
                <label for="password" class="block text-sm text-gray-700 mb-2">password</label>
                <input type="password" name="password" id="password" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <div class="w-full mb-8">
                <label for="confirm-password" class="block text-sm text-gray-700 mb-2">confirm password</label>
                <input type="password" name="confirm-password" id="confirm-password" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <button type="submit" class="form-btn w-full bg-yellow-300 hover:bg-yellow-400 text-black font-medium py-3 px-4 rounded transition-all">
                continue
            </button>

            <p class="text-sm text-gray-600 mt-6 underline">
                already have an account?
                <a href="login.jsp" class="text-black hover:text-yellow-500"> log in</a>
            </p>
        </form>
    </div>
</div>
</body>
</html>
