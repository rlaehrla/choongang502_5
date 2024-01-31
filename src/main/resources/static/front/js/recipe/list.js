/**
*
* 카테고리 선택 후 후속 처리
*
* @param data : 선택한 데이터
*/
function callbackCategorySelect(cateCd, cateNm) {

    const cate_select = document.querySelector("#cate_select");
    const cateCdVal = document.querySelector("#cateCd");

    if(cateCd != null && cateNm != null){
        cateCdVal.value = cateCd;
        cate_select.innerText = cateNm;
        cate_select.classList.add("on");
    }
}

const recipeLib = {

    /**
    * 찜하기
    * @param reSeq : 레시피번호
    *
    */
    save(reSeq){
        const { ajaxLoad } = commonLib;

        ajaxLoad('GET', `/api/recipe/saveReci/${reSeq}`);
    },

    /**
    * 찜 해제
    * @param reSeq : 레시피번호
    *
    */
    deleteSave(reSeq){
        const { ajaxLoad } = commonLib;
        ajaxLoad('DELETE', `/api/recipe/saveReci/${reSeq}`);
    }

}



window.addEventListener("DOMContentLoaded", function(){
    const savePosts = document.querySelectorAll(".save_post");

    for(const savePost of savePosts){
        savePost.addEventListener("click", function(){
            const seq = this.dataset.seq;
            if(this.classList.contains('on')){
                this.classList.remove('on');
                recipeLib.deleteSave(seq);

            }else{
                this.classList.add('on');
                recipeLib.save(seq);
            }

            if(this.dataset.refresh == 'true'){
                location.reload();
            }

        });
    }
});