

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

    /* 판매상세 하위탭 전환 처리 S */
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

    /* 판매상세 하위탭 전환 처리 E */

    /* 구매 수량 변경  S */

    const changeEaEls = document.getElementsByClassName("change_ea");

    for (const el of changeEaEls) {
        el.addEventListener("click", productDetails.changeEa);
    }
    /* 구매 수량 변경  E */
});