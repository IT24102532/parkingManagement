<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Booking Test</h1>
<form id="search_booking">
    <!-- Date inputs -->
    <label>
        Start Date
        <input type="date" name="startDate" id="startDate" required>
    </label>
    <label>
        End Date
        <input type="date" name="endDate" id="endDate" required>
    </label>

    <!-- Location Dropdown -->
    <label for="location">Location</label>
    <select name="location" id="location">
        <option value="">Select a location</option>
    </select>

    <!-- Slots Dropdown (initially disabled) -->
    <label for="lots">Lots</label>
    <select name="lots" id="lots" disabled>
        <option value="">Select a slot</option>
    </select>

    <div id="error" class="error"></div>
    <input type="submit" value="Book">
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/search_ajax.js"></script>