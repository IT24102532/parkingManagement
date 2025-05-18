document.addEventListener('DOMContentLoaded', () => {
    const adminId = "sam";
    fetch("/get/transactions/all")
        .then(res => res.json())
        .then(data => {
            console.log(data);
            const transactions = data.transactions;

            const userContainer = document.getElementById("trans-container");
            userContainer.innerHTML = "";

            transactions.reverse().slice(0, 5).forEach(t => {
                console.log(t);
                const userPill = document.createElement("div");
                userPill.className = "user-pill";
                userPill.innerHTML = `
                    <div style="display: flex; gap: 20px; align-items: center;">
                        <p class="user-pill-name">${t.username}</p>
                        <p class="user-pill-date" style="color: darkgreen">$${t.amount}</p>
                        <input type="hidden" value="${t.id}">
                    </div>
                    <p class="user-pill-date">${t.date}</p>
                `;
                userContainer.appendChild(userPill);
            });
        })
        .catch(err => {
            console.error("Failed to fetch summary data:", err);
        });

    fetch('/get/transactions/count')
        .then(res => res.json())
        .then(data => {
            const totalTransactions = document.getElementById('totalTransactions');
            const todayTransactions = document.getElementById('todayTransactionCount');
            const todayDate = document.getElementById('todayTransactionDate');
            const monthTransactions = document.getElementById('monthTransactionCount');
            const thisMonth = document.getElementById('monthTransactionDate');

            totalTransactions.innerText = "$" + data.totalTransactions;
            todayTransactions.innerText = "$" + data.newTransactionsToday;
            monthTransactions.innerText = "$" + data.newTransactionsMonth;

            const now = new Date();
            const options = { year: 'numeric', month: 'long', day: 'numeric' };
            todayDate.innerText = now.toLocaleDateString(undefined, options);

            thisMonth.innerText = now.toLocaleString(undefined, { month: 'long' });
        })


    function debounce(func, delay = 300) {
        let timeout;
        return (...args) => {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), delay);
        };
    }

    async function fetchTransactions(searchTerm = '') {
        const loader = document.getElementById('loader');
        loader.style.display = 'block';

        try {
            const url = searchTerm
                ? `/get/transactions/search?param=${encodeURIComponent(searchTerm)}`
                : '/get/transactions/all?limit=10';

            const response = await fetch(url);
            const data = await response.json();
            const transactions = data.transactions || (data.transactions ? [data.transactions] : []);
            renderTransactions(transactions);
        } catch (error) {
            console.error('Error fetching users:', error);
        } finally {
            loader.style.display = 'none';

        }
    }

    function renderTransactions(transactions) {
        const container = document.getElementById('TransactionContainer');
        const template = document.getElementById('transaction-template');
        container.innerHTML = '';

        transactions.forEach(transaction => {
            const clone = template.content.cloneNode(true);
            const accordion = clone.querySelector('.transaction-accordion');

            const header = accordion.querySelector('.accordion-header');
            const form = accordion.querySelector('.user-form');

            accordion.querySelector('.username').textContent = transaction.username;
            accordion.querySelector('.joined-date').textContent = transaction.date;

            const usernameInput = form.querySelector('.username-input');
            const dateInput = form.querySelector('.date-input');
            const locationInput = form.querySelector('.location-input');
            const slotInput = form.querySelector('.slot-input');
            const amountInput = form.querySelector('.amount-input');

            usernameInput.value = transaction.username;
            dateInput.value = transaction.date;
            locationInput.value = transaction.location;
            slotInput.value = transaction.slot;
            amountInput.value = transaction.amount;

            form.dataset.userId = transaction.id;
            form.dataset.adminId = adminId;

            header.addEventListener('click', () => {
                accordion.querySelector('.accordion-content').classList.toggle('expanded');
            });

            container.appendChild(clone);
        });
    }

    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', debounce(() => {
        fetchTransactions(searchInput.value.trim());
    }));

    fetchTransactions();

    document.addEventListener("click", function (e) {
        const dropdown = document.querySelector(".dropdown");
        const isClickInside = dropdown.contains(e.target);

        if (isClickInside && e.target.classList.contains("dropdown-toggle")) {
            dropdown.classList.toggle("open");
        } else {
            dropdown.classList.remove("open");
        }
    });
})