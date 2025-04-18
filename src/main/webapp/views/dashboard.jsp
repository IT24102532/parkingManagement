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

        <div class="bg-white rounded-xl shadow-md p-6 max-w-4xl">
            <div class="flex justify-between items-center mb-4">
                <h2 class="text-lg font-medium">add a new slot</h2>
                <div class="flex space-x-2">
                    <button class="bg-gray-200 text-gray-700 px-4 py-1 rounded">cancel</button>
                    <button class="bg-green-600 text-white px-4 py-1 rounded">save</button>
                </div>
            </div>

            <table class="table-auto w-full text-left">
                <tbody>
                <tr class="border-t">
                    <td class="py-2 font-medium">Location</td>
                    <td class="py-2">Los Angeles International</td>
                    <td class="py-2 text-right"><span class="material-icons text-gray-400 cursor-pointer">more_vert</span></td>
                </tr>
                <tr class="border-t">
                    <td class="py-2 font-medium">Proximity</td>
                    <td class="py-2">Southern Terminal</td>
                    <td class="py-2 text-right"><span class="material-icons text-gray-400 cursor-pointer">more_vert</span></td>
                </tr>
                <tr class="border-t">
                    <td class="py-2 font-medium">Owner Affiliation</td>
                    <td class="py-2">Los Angeles International</td>
                    <td class="py-2 text-right"><span class="material-icons text-gray-400 cursor-pointer">more_vert</span></td>
                </tr>
                <tr class="border-t">
                    <td class="py-2 font-medium">Lot Type</td>
                    <td class="py-2"><span class="bg-gray-200 px-2 py-1 rounded text-sm">long-term</span></td>
                    <td class="py-2 text-right"><span class="material-icons text-gray-400 cursor-pointer">more_vert</span></td>
                </tr>
                <tr class="border-t">
                    <td class="py-2 font-medium">Price</td>
                    <td class="py-2 italic">$10.00</td>
                    <td class="py-2 text-right"><span class="material-icons text-gray-400 cursor-pointer">more_vert</span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>