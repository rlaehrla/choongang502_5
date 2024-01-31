window.addEventListener("DOMContentLoaded", function() {
    const { ajaxLoad } = commonLib;

    const chks = document.querySelectorAll(".cateCd");

    for(const chk of chks){
        chk.addEventListener("click", function() {
            if (typeof parent.callbackCategorySelect == 'function') {
                const cateCd = this.value;
                const cateNm = this.dataset.cateNm;
                parent.callbackCategorySelect(cateCd, cateNm);
            }
        });
    }

    const selectDone = document.getElementById("select_done");
    selectDone.addEventListener("click", function() {
        const { popup } = parent.commonLib;
        popup.close();
    });
});