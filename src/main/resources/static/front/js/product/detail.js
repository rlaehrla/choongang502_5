

window.addEventListener("DOMContentLoaded", function() {

    /* 상품 메인 썸네일 이벤트 처리 S */
    const thumbs = document.querySelectorAll(".thumbs .thumb");

    const mainImage = document.querySelector(".product_images .main_image img");
    if (mainImage) {
        for (const el of thumbs) {
            el.addEventListener("mouseenter", function() {
                const url = this.dataset.url;
                mainImage.src = url;
            });
        }
    }
    /* 상품 메인 썸네일 이벤트 처리 E */


    /** 찜하기, 장바구니, 주문하기 버튼 처리 S */
    const productActions = document.getElementsByClassName("product_action");
        for (const el of productActions) {
            el.addEventListener("click", function() {
                const mode = this.dataset.mode;
                if (mode == 'WISH') { // 찜하기

                } else { // 장바구니, 바로구매
                    frmSave.mode.value = mode;
                    frmSave.submit();
                }
            });
        }
    /** 찜하기, 장바구니, 주문하기 버튼 처리 E */

    /* 판매자 문의 채팅 연결 버튼 처리 S */
    const chatBtn = document.querySelector(".chat_btn");

    chatBtn.addEventListener("click", function(){
        location.href='/';
    });


    /* 판매자 문의 채팅 연결 버튼 처리 E */

});