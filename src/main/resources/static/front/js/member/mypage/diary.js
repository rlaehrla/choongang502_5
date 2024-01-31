window.addEventListener("DOMContentLoaded", function(){
    /* 판매상세 하위탭 전환 처리 S */
    const tabs = document.querySelectorAll(".recipe_tabs .tabs .tab input[type='radio']");
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

    /* 판매상세 하위탭 전환 처리 E */
});