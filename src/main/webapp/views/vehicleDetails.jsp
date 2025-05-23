<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/assets/images/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/assets/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/images/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/assets/images/site.webmanifest">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dual_container_global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vehicleDetails.css">
    <title>Vehicle Details | park.me</title>
</head>
<body>
<div class="container">
    <div class="left">
        <img class="logo" src="${pageContext.request.contextPath}/assets/images/logo_purple.png" alt="logo">
        <img class="bg-img" src="${pageContext.request.contextPath}/assets/images/bg2.jpg" alt="Car">
    </div>
    <div class="right">
        <h1 class="title">let's add your vehicle details</h1>
        <form action="${pageContext.request.contextPath}/signup" method="post" class="form">
            <input type="hidden" name="step" value="vehicle">

            <label for="carType">car type</label>
            <select id="carType" name="carType" class="input">
                <option value="Sedan">Sedan</option>
                <option value="SUV">SUV</option>
                <option value="Truck">Truck</option>
                <option value="Van">Van</option>
                <option value="Hatchback">Hatchback</option>
            </select>

            <label for="regLocation">registered counrty</label>
            <select id="regLocation" name="regLocation" required class="input" style="margin-bottom: 20px">
                <option disabled selected value="">Select a country</option>
            </select>

            <h3 class="subtitle">liscence plate</h3>
            <div class="license">
                <input type="text" id="regState" name="regState"
                       value="<%= request.getParameter("regState") != null ? request.getParameter("regState") : "" %>"
                       placeholder="State" required>
                <input type="text" id="licensePlate" name="licensePlate"
                       value="<%= request.getParameter("licensePlate") != null ? request.getParameter("licensePlate") : "" %>"
                       placeholder="Plate Number" required>
            </div>


            <div class="form-buttons">
                <button type="submit" class="form-btn">continue to payment →</button>
                <a href="?step=user" class="back-btn">← go Back</a>
            </div>
        </form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', async () => {
        const countrySelect = document.getElementById('regLocation');
        await fetchCountries();

        document.querySelector('form').addEventListener('submit', function (e) {
            const selectedCountry = countrySelect.value;
            const matched = countries.find(c => c.code === selectedCountry);

            if (matched) {
                const hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.name = 'regCountry';
                hiddenInput.value = matched.code;

                this.appendChild(hiddenInput);

                countrySelect.removeAttribute('name');
            }
        });
    });

    let countries = [];

    async function fetchCountries() {
        const res = await fetch('https://restcountries.com/v3.1/all?fields=name,cca3');
        const data = await res.json();

        countries = data.map(c => ({
            name: c.name.common,
            code: c.cca3
        }));

        countries.sort((a, b) => a.name.localeCompare(b.name));
        const countrySelect = document.getElementById('regLocation');
        countries.forEach(c => {
            const option = document.createElement('option');
            option.value = c.code;
            option.textContent = c.name;
            countrySelect.appendChild(option);
        });
    }
</script>

</body>
</html>
