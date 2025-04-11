<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Details | park.me</title>
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
        <img src="images/bg3.jpg" alt="Payment" class="w-full h-full object-cover">
    </div>

    <!-- Right Form -->
    <div class="w-full md:w-1/2 p-8">
        <h2 class="text-3xl font-bold text-gray-800 mb-6">Payment Details</h2>

        <form action="Signup" method="post" class="space-y-4">
            <input type="hidden" name="step" value="payment">

            <div>
                <label for="cardNumber" class="block text-yellow font-medium mb-1">Card Number</label>
                <input type="text" id="cardNumber" name="cardNumber" required
                       class="w-full border border-gray-300 px-4 py-2 rounded-xl focus:outline-none focus:ring-2 focus:ring-green-400"
                       placeholder="1234 5678 9012 3456">
            </div>

            <div class="grid grid-cols-2 gap-4">
                <div>
                    <label for="expiry" class="block text-gray-600 font-medium mb-1">Expiry Date</label>
                    <input type="text" id="expiry" name="expiry" required
                           class="w-full border border-gray-300 px-4 py-2 rounded-xl focus:outline-none focus:ring-2 focus:ring-green-400"
                           placeholder="MM/YY">
                </div>

                <div>
                    <label for="cvv" class="block text-gray-600 font-medium mb-1">CVV</label>
                    <input type="text" id="cvv" name="cvv" required
                           class="w-full border border-yellow px-4 py-2 rounded-xl focus:outline-none focus:ring-2 focus:ring-green-400"
                           placeholder="123">
                </div>
            </div>

            <button type="submit"
                    class="w-full bg-yellow-500 text-white font-semibold py-3 px-4 rounded-xl hover:bg-yello transition text-lg">
                Take me to park | me
            </button>
        </form>
    </div>
</div>

</body>
</html>
