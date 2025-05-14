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

