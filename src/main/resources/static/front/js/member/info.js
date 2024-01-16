window.addEventListener("DOMContentLoaded", function() {

    const cancelBtn = document.querySelector('#cancelBtn') ;
    cancelBtn.addEventListener("click", function() {
        history.back() ;
    });
})