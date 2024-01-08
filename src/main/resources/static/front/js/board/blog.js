/*
const farmBlogSection = document.querySelector(".farmBlogSection");
const farmIntoduction = document.querySelector(".farmIntroduction");
farmIntoduction.addEventListener('click', () => {
    farmBlogSection.
});
*/

var commonLib = commonLib || {};

// 섹션 전환
commonLib.sectionManager = {

    changeSection(button, link, place){
        const { ajaxLoad} = commonLib;

        button.addEventListener('click', () => {
            place.setAttribute("th:replace", link);
        });
    }
}

window.addEventListener("DOMContentLoaded", function() {
    const intro = document.querySelector("#farmIntroduction");
    const link = "~{front/blog/_farmIntroduction::intoduction}";
    const farmBlogSection = document.querySelector("#farmBlogSection");

    commonLib.sectionManager.changeSection(intro,link, farmBlogSection);
});