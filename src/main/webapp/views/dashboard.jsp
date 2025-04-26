<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard | park.me</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body class="bg-gray-50">

<div class="flex h-screen">
    <!-- Sidebar -->
    <div class="w-16 bg-yellow-50 flex flex-col items-center py-6 space-y-6">
        <span class="material-icons text-gray-600 text-3xl">dashboard</span>
        <span class="material-icons text-gray-600 text-2xl">person</span>
        <span class="material-icons text-gray-600 text-2xl">event_note</span>
        <span class="material-icons text-gray-600 text-2xl">receipt</span>
        <span class="material-icons text-gray-600 text-2xl">bar_chart</span>
        <span class="material-icons text-gray-600 text-2xl">settings</span>
    </div>

    <!-- Main content -->
    <div class="flex-1 p-8">
        <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-semibold">Dashboard</h1>
            <input type="text" placeholder="search a user" class="px-4 py-2 rounded-md border border-gray-300 focus:outline-none focus:ring focus:ring-gray-200 w-80">
        </div>

        <!-- Form Section -->
        <div class="bg-white rounded-xl shadow-md p-6 max-w-4xl">
            <h2 class="text-xl font-semibold mb-4">Lot Information</h2>
            <form action="../slot-creation" method="post" class="space-y-4">

                <!-- Lot Name -->
                <div>
                    <label for="lotName" class="block text-gray-700 font-medium mb-2">Lot Name</label>
                    <input type="text" id="lotName" name="lotName" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-yellow-100">
                </div>

                <!-- Location -->
                <div>
                    <label for="location" class="block text-gray-700 font-medium mb-2">Location</label>
                    <input type="text" id="location" name="location" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-yellow-100">
                </div>
                <div>
                    <label for="locationName" class="block text-gray-700 font-medium mb-2">Location Name</label>
                    <input type="text" id="locationName" name="locationName" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-yellow-100">
                </div>

                <!-- Owner affilication -->
                <div>
                    <label for="owner-affiliation" class="block text-gray-700 font-medium mb-2">Owner Affiliation </label>
                    <input type="text" id="owner-affiliation" name=`owner-affiliation` class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-yellow-100">
                </div>

                <!-- Lot Type Dropdown -->
                <div>
                    <label for="lotType" class="block text-gray-700 font-medium mb-2">Lot Type</label>
                    <select id="lotType" name="lot-type" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-yellow-100">
                        <option value="instaslot">Insta-Slot</option>
                        <option value="longterm">Long-Term</option>
                    </select>
                </div>

                <div>
                    <label for="price" class="block text-gray-700 font-medium mb-2">Price</label>
                    <input type="number" id="price" name="price" class="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-yellow-100">
                </div>



                <!-- Submit Button -->
                <div>
                    <button type="submit" class="bg-yellow-400 text-white px-6 py-2 rounded-md hover:bg-yellow-500 transition">Submit</button>
                </div>

            </form>
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null) { %>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
                <strong class="font-bold">Success!</strong>
                <span class="block sm:inline"><%= message %></span>
            </div>
            <% } %>

        </div>
    </div>
</div>

</body>
</html>
