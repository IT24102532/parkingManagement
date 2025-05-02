function openEditModal() {
    const modal = document.getElementById('editModal');
    modal.style.display = 'flex';

    // Populate current values
    document.getElementById('editFname').value = document.getElementById('fname').textContent;
    document.getElementById('editLname').value = document.getElementById('lname').textContent;
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

document.getElementById('profileForm').addEventListener('submit', function(e) {
    e.preventDefault();

    // Update values
    document.getElementById('fname').textContent = document.getElementById('editFname').value;
    document.getElementById('lname').textContent = document.getElementById('editLname').value;

    // Handle password change logic here

    closeEditModal();
});

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        closeEditModal();
    }
}