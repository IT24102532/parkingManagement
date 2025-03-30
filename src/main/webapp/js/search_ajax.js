document.addEventListener("DOMContentLoaded", function () {
    const locationDropdown = document.getElementById("location");
    const lotsDropdown = document.getElementById("lots");
    const startDate = document.getElementById("startDate");
    const endDate = document.getElementById("endDate");

    // Fetch locations on page load
    fetch("/findlocations")
        .then(response => response.json())
        .then(data => {
            data.forEach(loc => {
                let option = new Option(loc.name, loc.id); // Fix ordering
                locationDropdown.appendChild(option);
            });
        })
        .catch(error => console.error("Error fetching locations:", error));

    // Validate dates
    [startDate, endDate].forEach(input => {
        input.addEventListener("change", () => {
            if (locationDropdown.value) {
                locationDropdown.dispatchEvent(new Event("change"));
            }
        });
    });

    // Fetch slots when location changes
    locationDropdown.addEventListener("change", function () {
        let locationId = locationDropdown.value;
        let start = startDate.value;
        let end = endDate.value;

        if (!locationId || !start || !end) {
            lotsDropdown.disabled = true;
            lotsDropdown.innerHTML = '<option value="">Select a slot</option>';
            return;
        }

        let query = "/findavailable?location=" + encodeURIComponent(locationId) +
            "&startDate=" + encodeURIComponent(start) +
            "&endDate=" + encodeURIComponent(end);

        fetch(query)
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                lotsDropdown.innerHTML = '<option value="">Select a slot</option>';
                if (data.length > 0) {
                    lotsDropdown.disabled = false;
                    data.forEach(slot => {
                        let option = new Option(slot.slotId, slot.slotId);
                        lotsDropdown.appendChild(option);
                    });
                } else {
                    lotsDropdown.disabled = true;
                }
            })
            .catch(error => {
                console.error("Error fetching slots:", error);
                lotsDropdown.disabled = true;
            });
    });

    // Handle form submission
    document.getElementById("search_booking").addEventListener("submit", function (e) {
        e.preventDefault();
        let slotID = lotsDropdown.value;
        let startDateValue = startDate.value;
        let endDateValue = endDate.value;

        if (!slotID) {
            document.getElementById("error").textContent = "Please select a slot.";
            return;
        }

        fetch("/book", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ slotID, startDate: startDateValue, endDate: endDateValue, locationId: locationDropdown.value })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Booking successful!");
                } else {
                    document.getElementById("error").textContent = data.error;
                }
            })
            .catch(error => {
                console.error("Error booking slot:", error);
                document.getElementById("error").textContent = "An error occurred.";
            });
    });
});