document.addEventListener("DOMContentLoaded", () => {
    const startDateRaw = document.getElementById("reqStartDate").value;
    const locationRaw  = document.getElementById("reqLocation").value;
    const nextBtn = document.getElementById("next");

    const slotTitle = document.querySelector(".result-title");
    const locName = document.querySelector(".location_name");
    const startP = document.querySelector(".startDate");
    const endP = document.querySelector(".endDate");
    const amountP = document.querySelector(".amount");
    const promoP = document.querySelector(".promoCode");
    const slotIdInput = document.querySelector(".slotId");
    const startInput = document.querySelector(".inputStartDate");
    const endInput = document.querySelector(".inputEndDate");

    function parseRaw(raw) {
        const [datePart, timePart] = raw.split(" ");
        const [day, month, year] = datePart.split("/");
        return {
            year, month: month.padStart(2,"0"), day: day.padStart(2,"0"),
            time: timePart
        };
    }

    function addHours(dateObj, hours) {
        const d = new Date(dateObj);
        d.setHours(d.getHours() + hours);
        return d;
    }

    function formatDisplay(dt) {
        const d = dt.getDate().toString().padStart(2,"0");
        const m = (dt.getMonth()+1).toString().padStart(2,"0");
        const y = dt.getFullYear();
        const hh = dt.getHours().toString().padStart(2,"0");
        const mm = dt.getMinutes().toString().padStart(2,"0");
        return `${d}/${m}/${y} at ${hh}:${mm}`;
    }

    function toISO(dt) {
        const y = dt.getFullYear();
        const m = (dt.getMonth()+1).toString().padStart(2,"0");
        const d = dt.getDate().toString().padStart(2,"0");
        const hh = dt.getHours().toString().padStart(2,"0");
        const mm = dt.getMinutes().toString().padStart(2,"0");
        return `${y}-${m}-${d}T${hh}:${mm}`;
    }

    async function fetchSlot(action = "") {
        const { year, month, day, time } = parseRaw(startDateRaw);
        const apiDate = `${year}-${month}-${day}`;

        const params = new URLSearchParams({
            type:      "insta",
            startDate: apiDate,
            location:  locationRaw,
            time
        });
        if (action) params.set("action", action);

        try {
            const res = await fetch(`/get/slot?${params}`);
            if (!res.ok) throw new Error(res.status);
            const { slot, remaining } = await res.json();

            const startDT = new Date(`${apiDate}T${time}`);
            const endDT   = addHours(startDT, slot.max_hours);

            slotTitle.textContent = slot.locationName;
            locName.textContent   = slot.slotName;
            startP.textContent    = formatDisplay(startDT);
            endP.textContent      = formatDisplay(endDT);
            amountP.textContent   = `$${slot.price.toFixed(2)}`;
            promoP.textContent    = slot.promo || "No available promotions";

            slotIdInput.value     = slot.slot_id;
            startInput.value      = toISO(startDT);
            endInput.value        = toISO(endDT);

            nextBtn.disabled      = remaining === 0;
        } catch (err) {
            console.error("API error:", err);
        }
    }

    fetchSlot();

    nextBtn.addEventListener("click", e => {
        e.preventDefault();
        fetchSlot("next");
    });
});