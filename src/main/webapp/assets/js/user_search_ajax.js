document.addEventListener("DOMContentLoaded", function() {
    const params = new URLSearchParams(window.location.search);
    const userId = params.get("user");
    let query = "/getuser?user=" + encodeURIComponent(userId);

    let username = document.getElementById("username").value;
    let fname = document.getElementById("fname").value;
    let lname = document.getElementById("lname").value;
    let email = document.getElementById("email").value;
    let user = username;


    fetch(query)
        .then(response => response.json())
        .then(data => {
            console.log("Fetched user:", data);
            // use the data here
        });
});