    document.addEventListener('DOMContentLoaded', async () => {
    const userId = document.getElementById('userId').value;
    const slotId = document.getElementById('slotId').value;
    const vehicleId = document.getElementById('vehicleId').value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    // Assign directly to the start/end display areas
    document.getElementById('startDateDisplay').innerText = startDate;
    document.getElementById('endDateDisplay').innerText = endDate;
    document.querySelector('input[name="startDate"]').value = startDate;
    document.querySelector('input[name="endDate"]').value = endDate;

    try {
    // Fetch slot
    const slotRes = await fetch(`/get/slot/id?id=${slotId}`);
    const slotData = await slotRes.json();

    document.getElementById('location').innerText = slotData.locationName;
    document.getElementById('slotType').innerText = slotData.type;

    document.getElementById('locationInput').value = slotData.locationName;
    document.getElementById('slotTypeInput').value = slotData.type;
    document.querySelector('input[name="slotId"]').value = slotData.slot_id;

    // Fetch user (includes vehicle)
    const userRes = await fetch(`/get/user?user=${userId}`);
    const userData = await userRes.json();

    const vehicle = userData.VehicleDetails;

    document.getElementById('vehicleType').innerText = vehicle.vehicle_type;
    document.getElementById('registration').innerText = vehicle.reg_number;

    document.getElementById('vehicleTypeInput').value = vehicle.vehicle_type;
    document.getElementById('registrationInput').value = vehicle.reg_number;
    document.querySelector('input[name="vehicleId"]').value = vehicle.vehicle_id;
} catch (err) {
    console.error('Error loading booking data:', err);
}
});
