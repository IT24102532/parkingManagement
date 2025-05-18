document.addEventListener('DOMContentLoaded', () => {
    const adminId = "sam";
    const locationDataset = document.getElementById('locations');
    const managers = document.getElementById('managers');

    fetch('/get/slot/locations')
        .then(res => res.json())
        .then(data => {
            if (Array.isArray(data.locations)) {
                data.locations.forEach(location => {
                    const option = document.createElement('option');
                    option.value = location;
                    locationDataset.appendChild(option);
                });
            } else {
                console.error("No locations found in response:", data);
            }
        })
        .catch(error => {
            console.error("Failed to fetch location data:", error);
        });

    fetch('/get/slot/manager')
        .then(res => res.json())
        .then(data => {
            if (Array.isArray(data.managers)) {
                data.managers.forEach(manager => {
                    const option = document.createElement('option');
                    option.value = manager.id;
                    option.textContent = manager.name;
                    managers.appendChild(option);
                });
            } else {
                console.error("No managers found in response:", data);
            }
        })
        .catch(error => {
            console.error("Failed to fetch manager data:", error);
        });

    function debounce(func, delay = 300) {
        let timeout;
        return (...args) => {
            clearTimeout(timeout);
            timeout = setTimeout(() => func.apply(this, args), delay);
        };
    }

    async function fetchSlots(searchTerm = '') {
        const loader = document.getElementById('loader');
        loader.style.display = 'block';

        try {
            const url = searchTerm
                ? `/get/slot/search?param=${encodeURIComponent(searchTerm)}`
                : '/get/slot/all?limit=10';

            const response = await fetch(url);
            const data = await response.json();
            const slots = data.slots || (data.slots ? [data.slots] : []);
            renderSlots(slots);
        } catch (error) {
            console.error('Error fetching slots:', error);
        } finally {
            loader.style.display = 'none';

        }
    }

    function renderSlots(slots) {
        const container = document.getElementById('slotsContainer');
        const template = document.getElementById('slots-template');
        container.innerHTML = '';

        slots.forEach(slot => {
            const clone = template.content.cloneNode(true);
            const accordion = clone.querySelector('.slots-accordion');

            const header = accordion.querySelector('.accordion-header');
            const form = accordion.querySelector('.user-form');

            accordion.querySelector('.username').textContent = slot.location;
            accordion.querySelector('.joined-date').textContent = slot.slot;

            const locationInput = form.querySelector('.location-input');
            const slotInput = form.querySelector('.slot-input');
            const availaleInput = form.querySelector('.available-input');
            const chargeInput = form.querySelector('.charge-input');
            const managerInput = form.querySelector('.manager-input');

            locationInput.value = slot.location;
            slotInput.value = slot.slot;
            availaleInput.value = (slot.availability) ? "true" : "false";
            chargeInput.value = slot.amount.toFixed(2);
            managerInput.value = slot.manager;


            form.dataset.slotId = slot.id;
            form.dataset.adminId = adminId;

            header.addEventListener('click', () => {
                accordion.querySelector('.accordion-content').classList.toggle('expanded');
            });

            // Edit functionality
            const editBtn = form.querySelector('.edit-btn');
            let originalValues = {};

            editBtn.addEventListener('click', () => {
                if (editBtn.textContent === 'Edit') {
                    originalValues = {
                        location: locationInput.value,
                        slot: slotInput.value,
                        availability: availaleInput.value,
                        charge: chargeInput.value,
                        manager: managerInput.value,
                    };
                    [locationInput, slotInput, availaleInput, chargeInput, managerInput].forEach(i => i.disabled = false);
                    editBtn.textContent = 'Save';
                    editBtn.style.backgroundColor = '#2196F3';

                    const cancelBtn = document.createElement('button');
                    cancelBtn.type = 'button';
                    cancelBtn.className = 'cancel-btn';
                    cancelBtn.textContent = 'Cancel';
                    form.querySelector('.form-actions').prepend(cancelBtn);

                    cancelBtn.addEventListener('click', () => {
                        locationInput.value = originalValues.username;
                        slotInput.value = originalValues.email;
                        availaleInput.value = originalValues.location;
                        chargeInput.value = originalValues.firstname;
                        managerInput.value = originalValues.lastname;
                        [locationInput, slotInput, availaleInput, chargeInput, managerInput].forEach(i => i.disabled = true);
                        editBtn.textContent = 'Edit';
                        editBtn.style.backgroundColor = '#4CAF50';
                        cancelBtn.remove();
                    });
                } else {
                    const formData = {
                        id: form.dataset.slotId,
                        adminId: form.dataset.adminId,
                        location: locationInput.value,
                        slot: slotInput.value,
                        availability: availaleInput.value,
                        charge: chargeInput.value,
                        manager: managerInput.value
                    };

                    fetch(`/post/slot/${formData.id}/edit`, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(formData)
                    })
                        .then(response => {
                            if (response.ok) {
                                [locationInput, slotInput, availaleInput, chargeInput, managerInput].forEach(i => i.disabled = true);
                                editBtn.textContent = 'Edit';
                                editBtn.style.backgroundColor = '#4CAF50';
                                form.querySelector('.cancel-btn')?.remove();
                                alert("successfully edited");
                            }
                        })
                        .catch(error => console.error('Error updating user:', error));
                }
            });

            container.appendChild(clone);
        });
    }

    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('input', debounce(() => {
        fetchSlots(searchInput.value.trim());
    }));

    fetchSlots();

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