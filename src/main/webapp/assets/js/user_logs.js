document.addEventListener('DOMContentLoaded', () => {
    const transactionsTable = document.querySelector('#transaction-table');
    const bookingsTable = document.querySelector('#booking-table');
    const userId = document.getElementById("userId").value;

    const maxItems = 15;

    function paginateTable(data, tableElement, formatter, label) {
        let index = 0;

        const loadMoreBtn = document.createElement('button');
        loadMoreBtn.textContent = 'Load More';
        loadMoreBtn.classList.add('load-more-btn');
        loadMoreBtn.style.margin = '10px auto';
        loadMoreBtn.style.display = 'block';

        function loadItems() {
            const chunk = data.slice(index, index + maxItems);
            chunk.forEach(entry => {
                const row = formatter(entry);
                tableElement.appendChild(row);
            });
            index += maxItems;

            if (index >= data.length) {
                loadMoreBtn.remove();
            }
        }

        if (data.length === 0) {
            const row = document.createElement('tr');
            const cell = document.createElement('td');
            cell.setAttribute('colspan', '3');
            cell.textContent = `No ${label} found.`;
            cell.style.textAlign = 'center';
            row.appendChild(cell);
            tableElement.appendChild(row);
        } else {
            loadItems();
            tableElement.parentElement.appendChild(loadMoreBtn);
            loadMoreBtn.addEventListener('click', loadItems);
        }
    }

    // Fetch transactions
    fetch(`/get/booking/list?id=${userId}`)
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch booking data');
            return response.json();
        })
        .then(data => {
            transactionsTable.innerHTML = '';
            paginateTable(data, transactionsTable, entry => {
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

                return row;
            }, 'transactions');
        })
        .catch(error => {
            console.error('Error:', error);
            transactionsTable.innerHTML = `<tr><td colspan="3" style="text-align:center;">Error loading bookings</td></tr>`;
        });

    // Fetch bookings
    fetch(`/get/booking/all?id=${userId}`)
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch booking data');
            return response.json();
        })
        .then(data => {
            bookingsTable.innerHTML = '';
            paginateTable(data, bookingsTable, entry => {
                const row = document.createElement('tr');

                const locationCell = document.createElement('td');
                locationCell.textContent = entry.location;

                const dateCell = document.createElement('td');
                dateCell.textContent = entry.start;

                const chargeCell = document.createElement('td');
                chargeCell.textContent = entry.slot;

                row.appendChild(locationCell);
                row.appendChild(dateCell);
                row.appendChild(chargeCell);

                return row;
            }, 'bookings');
        })
        .catch(error => {
            console.error('Error:', error);
            bookingsTable.innerHTML = `<tr><td colspan="3" style="text-align:center;">Error loading bookings</td></tr>`;
        });
});
