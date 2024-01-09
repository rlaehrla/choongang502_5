var widget = widget || {};

widget.tab = {
    /**
    * 탭 내용 불러오기
    *
    * @Param url : ajax 요청 URL
    */
    loadContent(url){
        const contentEl = document.querySelector(".widget_tab .tab_content");
        if(!contentEl) return;

        const { ajaxLoad } = commonLib;

        ajaxLoad("GET", url)
            .then(res => {
                contentEl.innerHTML =res;
            })
            .catch(err => console.log(err));
    }
};

window.addEventListener("DOMContentLoaded", function(){
    const tabs = document.querySelectorAll(".widget_tab .tabs .tab");

    const { loadContent} = widget.tab;

    for(const el of tabs){
        el.addEventListener("click", function(){
            const url = this.value;
            loadContent(url);
        })
    }

    tabs[0].click();
})