const menuToggle = document.getElementById('menu-toggle');
const menu = document.getElementById('menu');
const container = document.getElementById('container');

menuToggle.addEventListener('click', () => {
    menu.classList.toggle('show');
    container.classList.toggle('blur');
});

// Close menu when clicking outside
document.addEventListener('click', (event) => {
    if (!menu.contains(event.target) && !menuToggle.contains(event.target)) {
        menu.classList.remove('show');
        container.classList.remove('blur');
    }
});