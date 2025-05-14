const settingsTrigger = document.getElementById('settingsTrigger');
const pathsTrigger = document.getElementById('pathsTrigger');
const pathsMenu = document.getElementById('pathsMenu');
const settingsMenu = document.getElementById('settingsMenu');

settingsTrigger.addEventListener('click', (e) => {
    e.stopPropagation();
    settingsMenu.classList.toggle('active');
});

document.addEventListener('click', (e) => {
    if (!settingsTrigger.contains(e.target) && !settingsMenu.contains(e.target)) {
        settingsMenu.classList.remove('active');
    }
});

settingsMenu.addEventListener('click', (e) => {
    e.stopPropagation();
});

pathsMenu.addEventListener('click', (e) => {
    e.stopPropagation();
    settingsMenu.classList.toggle('active');
});

document.addEventListener('click', (e) => {
    if (!pathsTrigger.contains(e.target) && !pathsTrigger.contains(e.target)) {
        pathsMenu.classList.remove('active');
    }
});

pathsMenu.addEventListener('click', (e) => {
    e.stopPropagation();
});