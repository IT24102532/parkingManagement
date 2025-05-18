document.addEventListener('DOMContentLoaded', () => {
    const adminId = "sam";
    fetch("/get/user/all") // Replace with your actual API endpoint
        .then(res => res.json())
        .then(data => {
            const users = data.users;

            const userContainer = document.getElementById("user-container");
            userContainer.innerHTML = "";

            users.reverse().slice(0, 5).forEach(u => {
                console.log(u);
                const userPill = document.createElement("div");
                userPill.className = "user-pill";
                userPill.innerHTML = `
                    <div style="display: flex; gap: 20px; align-items: center;">
                        <img src="https://avatar.iran.liara.run/public/" alt="user-icon" class="user-pill-icon">
                        <p class="user-pill-name">${u.name}</p>
                        <input type="hidden" value="${u.id}">
                    </div>
                    <p class="user-pill-date">${u.joined}</p>
                `;
                userContainer.appendChild(userPill);
            });
        })
        .catch(err => {
            console.error("Failed to fetch summary data:", err);
        });

    fetch('/get/user/count')
        .then(res => res.json())
        .then(data => {
            const totalUsers = document.getElementById('totalUsers');
            const todayUsers = document.getElementById('todayUserCount');
            const todayDate = document.getElementById('todayUserDate');
            const monthUsers = document.getElementById('monthUserCount');
            const thisMonth = document.getElementById('monthUserDate');

            totalUsers.innerText = data.totalUsers;
            todayUsers.innerText = data.newUsersToday;
            monthUsers.innerText = data.newUsersMonth;

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

    async function fetchUsers(searchTerm = '') {
        const loader = document.getElementById('loader');
        loader.style.display = 'block';

        try {
            const url = searchTerm
                ? `/get/user/search?param=${encodeURIComponent(searchTerm)}`
                : '/get/user/all?limit=10';

            const response = await fetch(url);
            const data = await response.json();
            const users = data.users || (data.user ? [data.user] : []);
            renderUsers(users)
        } catch (error) {
            console.error('Error fetching users:', error);
        } finally {
            loader.style.display = 'none';

        }
    }

    function renderUsers(users) {
        const container = document.getElementById('usersContainer');
        const template = document.getElementById('user-template');
        container.innerHTML = '';

        users.forEach(user => {
            const clone = template.content.cloneNode(true);
            const accordion = clone.querySelector('.user-accordion');

            const header = accordion.querySelector('.accordion-header');
            const form = accordion.querySelector('.user-form');

            accordion.querySelector('.username').textContent = user.name || user.username;
            accordion.querySelector('.joined-date').textContent = `Joined: ${user.joined || new Date(user.createdAt).toLocaleDateString()}`;

            const usernameInput = form.querySelector('.username-input');
            const emailInput = form.querySelector('.email-input');
            const locationInput = form.querySelector('.location-input');
            const statusInput = form.querySelector('.status-input');
            const firstnameInput = form.querySelector('.first-name-input');
            const lastnameInput = form.querySelector('.last-name-input');

            usernameInput.value = user.name || user.username;
            emailInput.value = user.email;
            locationInput.value = user.location;
            statusInput.value = user.status;
            firstnameInput.value = user.fname;
            lastnameInput.value = user.lname;

            form.dataset.userId = user.id;
            form.dataset.adminId = adminId;

            // Accordion toggle
            header.addEventListener('click', () => {
                accordion.querySelector('.accordion-content').classList.toggle('expanded');
            });

            // Edit functionality
            const editBtn = form.querySelector('.edit-btn');
            let originalValues = {};

            editBtn.addEventListener('click', () => {
                if (editBtn.textContent === 'Edit') {
                    originalValues = {
                        username: usernameInput.value,
                        email: emailInput.value,
                        location: locationInput.value,
                        firstname: firstnameInput.value,
                        lastname: lastnameInput.value,
                    };
                    [usernameInput, emailInput, locationInput, firstnameInput, lastnameInput].forEach(i => i.disabled = false);
                    editBtn.textContent = 'Save';
                    editBtn.style.backgroundColor = '#2196F3';

                    const cancelBtn = document.createElement('button');
                    cancelBtn.type = 'button';
                    cancelBtn.className = 'cancel-btn';
                    cancelBtn.textContent = 'Cancel';
                    form.querySelector('.form-actions').prepend(cancelBtn);

                    cancelBtn.addEventListener('click', () => {
                        usernameInput.value = originalValues.username;
                        emailInput.value = originalValues.email;
                        locationInput.value = originalValues.location;
                        firstnameInput.value = originalValues.firstname;
                        lastnameInput.value = originalValues.lastname;
                        [usernameInput, emailInput, locationInput, firstnameInput, lastnameInput].forEach(i => i.disabled = true);
                        editBtn.textContent = 'Edit';
                        editBtn.style.backgroundColor = '#4CAF50';
                        cancelBtn.remove();
                    });
                } else {
                    const formData = {
                        id: form.dataset.userId,
                        adminId: form.dataset.adminId,
                        username: usernameInput.value,
                        email: emailInput.value,
                        location: locationInput.value,
                        firstname: firstnameInput.value,
                        lastname: lastnameInput.value
                    };

                    fetch(`/post/user/${formData.id}/edit`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(formData)
                    })
                        .then(response => {
                            if (response.ok) {
                                [usernameInput, emailInput, locationInput, firstnameInput, lastnameInput].forEach(i => i.disabled = true);
                                editBtn.textContent = 'Edit';
                                editBtn.style.backgroundColor = '#4CAF50';
                                form.querySelector('.cancel-btn')?.remove();
                                alert("successfully edited");
                            }
                        })
                        .catch(error => console.error('Error updating user:', error));
                }
            });

            // Ban functionality
            form.querySelector('.ban-btn').addEventListener('click', () => {
                if (confirm('Are you sure you want to ban this user?')) {
                    fetch(`/post/user/${user.id}/ban`, { method: 'POST' })
                        .then(response => {
                            if (response.ok) alert(`${user.id} user has been banned!`);
                        })
                        .catch(error => console.error('Error banning user:', error));
                }
            });

            container.appendChild(clone);
        });
    }

    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', debounce(() => {
        fetchUsers(searchInput.value.trim());
    }));

    fetchUsers();
})