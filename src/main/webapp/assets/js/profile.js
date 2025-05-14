function openEditModal() {
    const modal = document.getElementById('editModal');
    modal.style.display = 'flex';

    // Populate current values
    document.getElementById('editFname').value = document.getElementById('fname').textContent;
    document.getElementById('editLname').value = document.getElementById('lname').textContent;
}

function openDeleteModal() {
    const modal = document.getElementById('deleteModal');
    modal.style.display = 'flex';
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}

// document.getElementById('profileForm').addEventListener('submit', async function(e) {
//     e.preventDefault();
//
//     const fname = document.getElementById('editFname').value;
//     const lname = document.getElementById('editLname').value;
//     const password = document.getElementById('editPassword').value;
//     const id = document.getElementById('userId').value;
//
//     const formData = new URLSearchParams();
//     formData.append("fname", fname);
//     formData.append("lname", lname);
//     formData.append("password", password);
//     formData.append("id", id);
//
//     try {
//         const res = await fetch("/update", {
//             method: "POST",
//             headers: { "Content-Type": "application/x-www-form-urlencoded" },
//             body: formData.toString()
//         });
//
//         if (res.ok) {
//             document.getElementById('fname').textContent = fname;
//             document.getElementById('lname').textContent = lname;
//             closeEditModal();
//         } else {
//             alert("Failed to update.");
//         }
//     } catch (err) {
//         console.error("Error:", err);
//     }
// });

// document.getElementById('deleteForm').addEventListener('submit', async function(e) {
//     e.preventDefault();
//
//     const password = document.getElementById('deletePassword').value;
//     const id = document.getElementById('userId').value;
//
//     const formData = new URLSearchParams();
//     formData.append("password", password);
//     formData.append("id", id);
//
//     try {
//         const res = await fetch("/delete", {
//             method: "POST",
//             headers: { "Content-Type": "application/x-www-form-urlencoded" },
//             body: formData.toString()
//         });
//
//         if (res.ok) {
//             closeEditModal();
//         } else {
//             alert("Failed to delete.");
//         }
//     } catch (err) {
//         console.error("Error:", err);
//     }
// });

window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    const deleteModal = document.getElementById('deleteModal');
    if (event.target === modal) {
        closeEditModal();
    }
    else if (event.target === deleteModal) {
        closeDeleteModal();
    }
}

