document.addEventListener("DOMContentLoaded", function() {

    const userId = document.getElementById("userId").value;
    let query = `/getuser?user=${encodeURIComponent(userId)}`;

    fetch(query)
        .then(response => {
            if (!response.ok) throw Error("Api Error");
            return response.json();
        })
        .then(data => {
            console.log("Fetched user:", data);
            document.getElementById("username").innerText = "@" + data.f_name + data.l_name;
            document.getElementById("fname").innerText = data.f_name;
            document.getElementById("lname").innerText = data.l_name;
            document.getElementById("email").innerText = data.email;
            document.getElementById("vehicleType").innerText = data.VehicleDetails.vehicle_type;
            document.getElementById("regNum").innerText = data.VehicleDetails.reg_state + data.VehicleDetails.reg_number;
        })
        .catch(err => console.log("Fetch failed", err));
});