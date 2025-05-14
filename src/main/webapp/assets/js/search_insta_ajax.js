document.addEventListener("DOMContentLoaded", function () {
    const userId = document.getElementById("userId").value;
    const vehicleDropdown = document.getElementById("vehicleSelect");
    const locationSelect = document.getElementById("locationSelect");
    const startDate = document.getElementById("startDate");
    const endDate = document.getElementById("endDate");

    fetch('/get/vehicle?user=' + encodeURIComponent(userId))
        .then(response => {
            if (!response.ok) throw Error("API Error");
            return response.json();
        })
        .then(data => {
            console.log("Fetched vehicle:", data);
            vehicleDropdown.innerHTML = "";
            const vehicles = Array.isArray(data) ? data : [data];

            vehicles.forEach(vehicle => {
                const option = document.createElement("option");
                option.value = vehicle.vehicle_id;
                option.textContent = vehicle.vehicle_type;
                vehicleDropdown.appendChild(option);
            });
        })
        .catch(err => console.log("Fetch failed", err));

    [startDate, endDate].forEach(input => {
        input.addEventListener("change", () => {
            if (locationSelect.value) {
                locationSelect.dispatchEvent(new Event("change"));
            } else {
                fetchLocationSlots();
            }
        });
    });

    function fetchLocationSlots() {
        const formatDateForAPI = (dateStr) => {
            const [day, month, rest] = dateStr.split('/');
            const [year] = rest.split(' ');
            return `${year}-${month}-${day}`;
        };

        let query = '/get/slot?type=insta' +
            '&startDate=' + encodeURIComponent(formatDateForAPI(startDate.value));

        fetch(query)
            .then(response => {
                if (!response.ok) throw Error("API Error");
                return response.json();
            })
            .then(data => {
                console.log("Fetched slots:", data);
                locationSelect.innerHTML = '<option disabled selected value="">Select a location</option>';

                for (const [code, fullName] of Object.entries(data.locations)) {
                    const option = document.createElement("option");
                    option.value = code;
                    option.textContent = fullName;
                    locationSelect.appendChild(option);
                }
            })
            .catch(err => console.log("Fetch failed", err));
    }


    locationSelect.addEventListener("change", function () {
        console.log("Location selected:", locationSelect.value);
    });
});
