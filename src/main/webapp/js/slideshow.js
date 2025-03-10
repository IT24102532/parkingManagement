document.addEventListener("DOMContentLoaded", function () {
    const images = document.querySelectorAll(".hero-image");
    let currentIndex = 0;

    function showNextImage() {
        // Get current image
        const currentImage = images[currentIndex];
        currentImage.classList.remove("active");

        // Delay before sliding out
        setTimeout(() => {
            currentImage.classList.add("exit");
        }, 1000); // Wait 2s before sliding out

        // Move to the next image
        currentIndex = (currentIndex + 1) % images.length;

        // Prepare next image
        const nextImage = images[currentIndex];
        nextImage.classList.remove("exit");
        nextImage.classList.add("active");
    }

    // Show the first image initially
    if (images.length > 0) {
        images[currentIndex].classList.add("active");
    }

    // Set interval for the slideshow
    setInterval(showNextImage, 5000); // Change image every 5 seconds
});

