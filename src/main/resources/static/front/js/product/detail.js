

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

    /* 상품 수량 증가, 감소 처리 S */
    const changeEaEls = document.querySelectorAll(".selected_products .change_ea");
    for (const el of changeEaEls) {
        el.addEventListener("click", productDetails.changeEa);
    }

    const eaEls = document.querySelectorAll(".selected_products input[type='number']");
        for (const el of eaEls) {
            el.addEventListener("blur", function() {
                let ea = parseInt(this.value);
                ea = ea < 1 ? 1 : ea;
                this.value = ea;
            });
        }

    /* 상품 수량 증가, 감소 처리 E */


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

    /* money 클래스 숫자 -> 세자리수마다 콤마 추가 S */
    const { numberComma } = commonLib;
    const numbers = document.querySelectorAll(".money");

    for(const num of numbers){
        const el = parseInt(num.innerText).toLocaleString();

        num.innerText = el;
    }

    /* money 클래스 숫자 -> 세자리수마다 콤마 추가 E */


});