document.addEventListener("DOMContentLoaded", () => {
    const userId = document.getElementById("userId").value;
    const startDateRaw = document.getElementById("reqStartDate").value;
    const endDateRaw   = document.getElementById("reqEndDate").value;
    const locationRaw  = document.getElementById("reqLocation").value;

    function formatDateForAPI(dateStr) {
        const [datePart] = dateStr.split(' ');
        const [day, month, year] = datePart.split('/');
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    }

    async function fetchLocationSlots() {
        const qs = new URLSearchParams({
            type:      "long_term",
            startDate: formatDateForAPI(startDateRaw),
            endDate:   formatDateForAPI(endDateRaw),
            location:  locationRaw
        });
        const res = await fetch(`/get/slot?${qs}`);
        if (!res.ok) throw new Error("API Error");
        const payload = await res.json();

        const slots = payload.slots || [];
        const container = document.getElementById("resultContainer");
        const template  = document.getElementById("resultTemplate");

        container.innerHTML = "";

        slots.forEach(slot => {
            const clone = template.content.cloneNode(true);
            const form      = clone.querySelector("form");
            const titleH2   = clone.querySelector(".title");
            const locP      = clone.querySelector(".location_name");
            const startP    = clone.querySelector(".startDate");
            const endP      = clone.querySelector(".endDate");
            const amountP   = clone.querySelector(".amount");
            const hiddenId  = clone.querySelector(".slotId");
            const hiddenSD  = clone.querySelector(".inputStartDate");
            const hiddenED  = clone.querySelector(".inputEndDate");

            titleH2.textContent = slot.locationName;
            locP.textContent    = slot.slotName;
            startP.textContent  = startDateRaw;
            endP.textContent    = endDateRaw;
            amountP.textContent = `$${slot.price}`;

            hiddenId.value = slot.slot_id;
            hiddenSD.value = formatDateForAPI(startDateRaw);
            hiddenED.value = formatDateForAPI(endDateRaw);

            const vehInput = document.createElement("input");
            vehInput.type  = "hidden";
            vehInput.name  = "vehicle";
            vehInput.value = document.getElementById("reqVehicle").value;
            form.appendChild(vehInput);

            container.appendChild(clone);
        });
    }

    fetchLocationSlots().catch(console.error);
});
