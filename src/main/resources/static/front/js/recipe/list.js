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