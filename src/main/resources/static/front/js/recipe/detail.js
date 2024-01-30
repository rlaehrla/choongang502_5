window.addEventListener("DOMContentLoaded", function() {

    /* 메인 썸네일 이벤트 처리 S */
    const thumbs = document.querySelectorAll(".thumbs .thumb");

    const mainImage = document.querySelector(".recipe_images .main_image img");
    if (mainImage) {
        for (const el of thumbs) {
            el.addEventListener("mouseenter", function() {
                const url = this.dataset.url;
                mainImage.src = url;
            });
        }
    }
    /* 메인 썸네일 이벤트 처리 E */


    const tabs = document.querySelectorAll(".product_tabs .tabs .tab input[type='radio']");
        const tabContents = document.getElementsByClassName("tab_content");
        for (const tab of tabs) {
            tab.addEventListener("click", function() {
                const value = this.value;
                console.log(value);
                for (const el of tabContents) {
                    el.classList.remove("dn");
                    el.classList.add("dn");
                }

                const el2 = document.getElementById("tab_content_" + value);
                if (el2) el2.classList.remove("dn");
            });
        }
    });