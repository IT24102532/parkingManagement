<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Card Details</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">

<div class="bg-white shadow-lg rounded-xl overflow-hidden flex max-w-4xl w-full h-[600px]">
    <!-- Image section -->
    <div class="w-1/2 hidden md:block">
        <img src="./images/bg5.jpg"
             alt="Card Image"
             class="h-full w-full object-cover border-r-4 border-yellow-400 rounded-l-xl">
    </div>

    <!-- Form section -->
    <div class="w-full md:w-1/2 p-10 flex flex-col justify-center">
        <h2 class="text-3xl font-bold mb-6 text-gray-800">Enter Your Card Details</h2>

        <form action="submitCardDetails" method="post" class="space-y-5">
            <div>
                <label class="block text-sm font-medium text-gray-700">Cardholder Name</label>
                <input type="text" name="cardHolderName" required
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-yellow-400 focus:outline-none">
            </div>

            <div>
                <label class="block text-sm font-medium text-gray-700">Card Number</label>
                <input type="text" name="cardNumber" required
                       class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-yellow-400 focus:outline-none">
            </div>

            <div class="flex space-x-4">
                <div class="w-1/2">
                    <label class="block text-sm font-medium text-gray-700">Expiry Date</label>
                    <input type="text" name="expiryDate" placeholder="MM/YY" required
                           class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-yellow-400 focus:outline-none">
                </div>

                <div class="w-1/2">
                    <label class="block text-sm font-medium text-gray-700">CVV</label>
                    <input type="password" name="cvv" required
                           class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-yellow-400 focus:outline-none">
                </div>
            </div>

            <button type="submit"
                    class="w-full mt-4 bg-yellow-400 text-white font-semibold py-2 px-4 rounded-md hover:bg-yellow-500 transition">
                Take me to park.me
            </button>
            <button type="button"
                    class="w-full mt-4 bg-gray-400 text-white font-semibold py-2 px-4 rounded-md hover:bg-gray-500 transition">
                go back
            </button>
        </form>
    </div>
</div>

</body>
</html>
