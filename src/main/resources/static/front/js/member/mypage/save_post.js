window.addEventListener("DOMContentLoaded", function() {
    const subjects = document.getElementsByClassName("subject");
    for (const el of subjects) {
        // 제목 클릭 -> 내용이 이미 보이고 있으면 감추기, 감춰진 상태 -> 보이기 (토글)
        el.addEventListener("click", function() {
           const contentEl = el.nextElementSibling;
           const classList = contentEl.classList;
           classList.toggle('hide');
        });
    }
});