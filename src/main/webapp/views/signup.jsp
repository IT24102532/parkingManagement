<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>park.me | Sign Up</title>
    <link rel="stylesheet" href="../assets/css/dual_container_global.css" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<div class="container">
    <!-- Left Side -->
    <div class="left">
        <img src="../assets/images/logo_purple.png" alt="Logo" class="logo" />
        <img src="../assets/images/bg2.jpg" alt="Background" class="bg-img" />
    </div>

    <!-- Right Side (Sign Up Form) -->
    <div class="right">
        <h2 class="title text-2xl font-semibold mb-10">Create Account</h2>
        <form action="..//signup" method="post" class="flex flex-col w-[340px] items-center">
            <input type="hidden" name="step" value="user">
            <div class="w-full mb-6">
                <label for="name" class="block text-sm text-gray-700 mb-2">Full Name</label>
                <input type="text" id="name" name="name" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <div class="w-full mb-6">
                <label for="email" class="block text-sm text-gray-700 mb-2">Email</label>
                <input type="email"  name="email" id="email" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <div class="w-full mb-6">
                <label for="password" class="block text-sm text-gray-700 mb-2">Password</label>
                <input type="password" name="password" id="password" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <div class="w-full mb-8">
                <label for="confirm-password" class="block text-sm text-gray-700 mb-2">Confirm Password</label>
                <input type="password" name="confirm-password" id="confirm-password" class="input w-full p-2 border border-gray-300 rounded shadow-sm" required />
            </div>

            <button type="submit" class="form-btn w-full bg-yellow-300 hover:bg-yellow-400 text-black font-medium py-3 px-4 rounded transition-all">
                Continue
            </button>

            <p class="text-sm text-gray-600 mt-6 underline">
                Already have an account?
                <a href="login.jsp" class="text-black hover:text-yellow-500">Log in</a>
            </p>
        </form>
    </div>
</div>
</body>
</html>
