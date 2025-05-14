document.addEventListener("DOMContentLoaded", () => {
    const userId = document.getElementById("userId").value;

    function formatDate(isoString) {
        const d = new Date(isoString);
        const dd = String(d.getDate()).padStart(2, '0');
        const mm = String(d.getMonth() + 1).padStart(2, '0');
        const yyyy = d.getFullYear();

        const hh = String(d.getHours()).padStart(2, '0');
        const min = String(d.getMinutes()).padStart(2, '0');

        return `${dd}/${mm}/${yyyy} ${hh}:${min}`;
    }

    let startTime;
    let endTime;
    const countdownEl = document.getElementById('countdown');

    const start = new Date(startTime);
    const end = new Date(endTime);
    const duration = end - startTime;

    const tableBody = document.querySelector('.booking-table tbody');

    if (!tableBody) {
        console.error('Booking table body not found!');
        return;
    }

    fetch(`/get/booking/list?id=${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch booking data');
            }
            return response.json();
        })
        .then(data => {
            tableBody.innerHTML = '';

            data.slice(0,5).forEach(entry => {
                const row = document.createElement('tr');

                const locationCell = document.createElement('td');
                locationCell.textContent = entry.location;

                const dateCell = document.createElement('td');
                dateCell.textContent = entry.date;

                const chargeCell = document.createElement('td');
                chargeCell.textContent = `$${entry.amount.toFixed(2)}`;

                row.appendChild(locationCell);
                row.appendChild(dateCell);
                row.appendChild(chargeCell);

                tableBody.appendChild(row);
            });

            if (data.length === 0) {
                const row = document.createElement('tr');
                const cell = document.createElement('td');
                cell.setAttribute('colspan', '3');
                cell.textContent = 'No bookings found.';
                cell.style.textAlign = 'center';
                row.appendChild(cell);
                tableBody.appendChild(row);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            tableBody.innerHTML = `<tr><td colspan="3" style="text-align:center;">Error loading bookings</td></tr>`;
        });


    function updateCountdown() {
        const start = new Date(startTime).getTime();
        const end = new Date(endTime).getTime();

        let diff = end - start;

        if (diff <= 0) {
            countdownEl.textContent = 'Expired';
            clearInterval(intervalId);
            return;
        }

        const hours = Math.floor(diff / (1000 * 60 * 60));
        diff %= 1000 * 60 * 60;
        const minutes = Math.floor(diff / (1000 * 60));

        countdownEl.textContent = `${hours}h ${minutes}m`;
    }


    fetch('/get/booking/recent?id=' + encodeURIComponent(userId))
        .then(response => {
            if (!response.ok) throw Error("API Error");
            return response.json();
        })
        .then(data => {
            console.log("Fetched booking:", data);

            const bookingId = data.booking_id;
            const slotId = data.slot_id;

            document.getElementById("startTime").innerText = formatDate(data.startDateTime);
            document.getElementById("endTime").innerText = formatDate(data.timeOut);

            endTime = data.timeOut;
            startTime = data.startDateTime;
            intervalId = setInterval(updateCountdown, 1000);
            updateCountdown();

            // Now fetch slot info (after slotId is available)
            fetch('/get/slot/id?id=' + encodeURIComponent(slotId))
                .then(response => {
                    if (!response.ok) throw Error("API Error");
                    return response.json();
                })
                .then(slotData => {
                    console.log(slotData)
                    const type = (slotData?.type || "").toLowerCase();
                    document.getElementById("lotType").innerText = slotData.type === "insta" ? "Insta" : "Regular";
                    document.getElementById("location").innerText = slotData.locationName;
                })
                .catch(err => console.log("Slot fetch failed", err));

            // Now fetch booking cost (after bookingId is available)
            fetch('/get/booking/info?id=' + encodeURIComponent(bookingId))
                .then(response => {
                    if (!response.ok) throw Error("API Error");
                    return response.json();
                })
                .then(infoData => {
                    console.log(infoData);
                    const bookinginfo = infoData[0];
                    document.getElementById("lotPrice").innerText = "$" + bookinginfo.amount;
                })
                .catch(err => console.log("Booking info fetch failed", err));
        })
        .catch(err => console.log("Booking fetch failed", err));

    const interval = setInterval(updateCountdown, 1000);
});
