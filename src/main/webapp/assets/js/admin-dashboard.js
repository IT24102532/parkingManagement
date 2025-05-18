document.addEventListener("DOMContentLoaded", function() {

    const ctx = document.getElementById("chart");
    const active = document.getElementById("activeReservationsCount");
    const etx = document.getElementById("earning-chart");
    const etxAmount = document.getElementById("earning-amount");

    fetch('/get/bookings/count')
        .then(res => res.json())
        .then(data => {
            const labels = data.map(entry => entry.date);
            const values = data.map(entry => entry.count);

            const maxValue = Math.max(...values);
            const paddedMax = Math.ceil(maxValue * 1.1);

            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels,
                    datasets: [{
                        label: 'Bookings per Day',
                        data: values,
                        backgroundColor: 'rgba(187, 173, 250, 0.3)',
                        borderColor: 'rgba(105, 89, 222, 0.8)',
                        borderWidth: 2,
                        tension: 0.3,
                        pointBackgroundColor: 'rgba(105, 89, 222, 1)',
                    }]
                },
                options: {
                    legend: {
                        display: true,
                        position: 'top',
                        align: 'center'
                    },
                    y: {
                        beginAtZero: true,
                        max: paddedMax
                    }
                }
            });
        });

    fetch('/get/bookings/active')
        .then(res => res.json())
        .then(data => {
            active.innerHTML = data.active;
        })


    fetch('/get/transaction/data')
        .then(res => res.json())
        .then(data => {
            // Separate earnings entry
            const earningsEntry = data.find(e => e.earnings !== undefined);
            const chartData = data.filter(e => e.earnings === undefined);

            etxAmount.innerText = "$"+ earningsEntry.earnings;

            const elabels = chartData.map(entry => entry.date);
            const evalues = chartData.map(entry => entry.total);

            const maxValue = Math.max(...evalues);
            const paddedMax = Math.ceil(maxValue * 1.1);

            new Chart(etx, {
                type: 'line',
                data: {
                    labels: elabels,
                    datasets: [{
                        label: 'Earnings per Day',
                        data: evalues,
                        fill: true,
                        backgroundColor: 'rgba(187, 173, 250, 0.3)',
                        borderColor: 'rgba(105, 89, 222, 0.8)',
                        tension: 0.3,
                        pointBackgroundColor: 'rgba(105, 89, 222, 1)',
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: paddedMax,
                            ticks: {
                                callback: function(value) {
                                    return '$' + value;
                                }
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: true,
                            position: 'top',
                            align: 'center'
                        }
                    }
                }
            });
        });

    fetch("/get/data/recent") // Replace with your actual API endpoint
        .then(res => res.json())
        .then(data => {
            const bookings = data.bookings;
            const users = data.users;

            const bookingContainer = document.getElementById("booking-container");
            const userContainer = document.getElementById("user-container");

            bookingContainer.innerHTML = "";
            userContainer.innerHTML = "";

            // Render Booking Pills
            bookings.forEach(b => {
                console.log(b);
                const bookingPill = document.createElement("div");
                bookingPill.className = "booking-pill";
                bookingPill.innerHTML = `
                    <div class="booking-pill-left">
                        <p class="booking-pill-title">${b.location}</p>
                        <p class="booking-pill-slot">${b.slotName}</p>
                        <p class="booking-pill-amount">$${b.amount}</p>
                    </div>
                    <div class="booking-pill-right">
                        <p class="booking-time">${b.date}</p>
                    </div>
                `;
                bookingContainer.appendChild(bookingPill);
            });

            // Render User Pills
            users.forEach(u => {
                console.log(u);
                const userPill = document.createElement("div");
                userPill.className = "user-pill";
                userPill.innerHTML = `
                    <div style="display: flex; gap: 20px; align-items: center;">
                        <img src="https://avatar.iran.liara.run/public/" alt="user-icon" class="user-pill-icon">
                        <p class="user-pill-name">${u.name}</p>
                    </div>
                    <p class="user-pill-date">${u.joined}</p>
                `;
                userContainer.appendChild(userPill);
            });
        })
        .catch(err => {
            console.error("Failed to fetch summary data:", err);
        });


    function updateOccupationChart() {
        const vacant = parseInt(document.getElementById("vacantCount").textContent);
        const occupied = parseInt(document.getElementById("occupiedCount").textContent);
        const notAvailable = parseInt(document.getElementById("notAvailableCount").textContent);

        const total = vacant + occupied + notAvailable;
        if (total === 0) return;

        const vacantPercent = (vacant / total) * 100;
        const occupiedPercent = (occupied / total) * 100;
        const notReadyPercent = (notAvailable / total) * 100;

        document.getElementById("vacantBar").style.width = `${vacantPercent}%`;
        document.getElementById("occupiedBar").style.width = `${occupiedPercent}%`;
        document.getElementById("notReadyBar").style.width = `${notReadyPercent}%`;
    }

    updateOccupationChart();
})