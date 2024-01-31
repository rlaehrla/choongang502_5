const productLib = {

    /**
    * 찜하기
    * @param pSeq : 상품번호
    *
    */
    save(pSeq){
        const { ajaxLoad } = commonLib;

        ajaxLoad('GET', `/api/product/save_post/${pSeq}`);
    },

    /**
    * 찜 해제
    * @param pSeq : 상품번호
    *
    */
    deleteSave(pSeq){
        const { ajaxLoad } = commonLib;
        console.log('삭제중삭제중');
        ajaxLoad('DELETE', `/api/product/save_post/${pSeq}`);
    }

}



window.addEventListener("DOMContentLoaded", function(){
    const savePosts = document.querySelectorAll(".save_post");

    for(const savePost of savePosts){
        savePost.addEventListener("click", function(){
            const seq = this.dataset.seq;
            if(this.classList.contains('on')){
                this.classList.remove('on');
                productLib.deleteSave(seq);

            }else{
                this.classList.add('on');
                productLib.save(seq);
            }

            if(this.dataset.refresh == 'true'){
                location.reload();
            }

        });
    }
});