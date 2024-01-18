window.addEventListener("DOMContentLoaded", function() {
    const cartActions = document.getElementsByClassName("cart_action");
    for (const el of cartActions) {
        el.addEventListener("click", function() {
            const mode = this.dataset.mode;
            frmCart.mode.value = mode.replace("_all", "");

            if (mode.indexOf("_all") != -1) { // 전체 선택(전체 삭제, 전체 주문)
                const chks = document.getElementsByName("chk");
                for (const chk of chks) {
                    chk.checked = true;
                }
            }

            // 체크 상태인 항목 체크
            const chks = document.querySelectorAll("input[name='chk']:checked");
            if (chks.length == 0) {
                alert("상품을 선택하세요.");
                return;
            }

            frmCart.submit();
        });
    }
});