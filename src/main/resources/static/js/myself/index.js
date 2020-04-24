$(function () {
    var images = window.document.images;
    for (var img of images) {
        console.log(img.src);
    }
});