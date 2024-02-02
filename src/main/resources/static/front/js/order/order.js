window.addEventListener("DOMContentLoaded", function() {
    // eqOrder 버튼 클릭시 주문자정보 == 받는분 정보

    const eqOrder = document.querySelector("#eqOrder");

    eqOrder.addEventListener("click", function() {
        if(eqOrder.checked){
            const orderName = document.querySelector("#orderName").value;
            const orderCellPhone1 = document.querySelector("#orderCellPhone1").value;
            const orderCellPhone2 = document.querySelector("#orderCellPhone2").value;
            const orderCellPhone3 = document.querySelector("#orderCellPhone3").value;
            const receiverName = document.querySelector("#receiverName");
            const receiverCellPhone1 = document.querySelector("#receiverCellPhone1");
            const receiverCellPhone2 = document.querySelector("#receiverCellPhone2");
            const receiverCellPhone3 = document.querySelector("#receiverCellPhone3");

            receiverName.value = orderName;
            receiverCellPhone1.value = orderCellPhone1;
            receiverCellPhone2.value = orderCellPhone2;
            receiverCellPhone3.value = orderCellPhone3;

            receiverName.setAttribute("readonly", true);
            receiverCellPhone1.setAttribute("readonly", true);
            receiverCellPhone2.setAttribute("readonly", true);
            receiverCellPhone3.setAttribute("readonly", true);
        }else{
            receiverName.removeAttribute("readonly");
            receiverCellPhone1.removeAttribute("readonly");
            receiverCellPhone2.removeAttribute("readonly");
            receiverCellPhone3.removeAttribute("readonly");
        }

    });


    /* 포인트 사용 S */
    const pointBtn = document.querySelector("#pointBtn");
    const totalPoint = document.querySelector("#avail_point").innerText;
    const usePoint = document.querySelector("#use_point");
    const payPrice = document.querySelector("#pay_price");
    const totalPrice = document.querySelector("#total_price").innerText.replace(/,/g, "");
    const payPriceVal = document.querySelector("#pay_price_val");
    pointBtn.addEventListener("click", function(){
        let total = Number(totalPoint.replace(/,/g, ""));
        let use = Number(usePoint.innerText);
        if(Number(totalPrice) < use){
            alert('상품 합계보다 적게 입력해주세요.');
        }else{
            if(total >= use){
                payPrice.innerText = "";
                const authBoxEl = document.querySelector(".auth_box");
                authBoxEl.innerHTML = "<span class='confirmed'>포인트가 적용되었습니다.</span>";
                usePoint.setAttribute("readonly", true);
                const price = payPrice.innerText.replace(/,/g, "");

               let _payPrice = Number(frmOrder.totalPrice.value) + Number(frmOrder.totalDeliveryPrice.value) - Number(frmOrder.totalDiscount.value) - Number(frmOrder.usePoint.value);

               if(!_payPrice){
                 _payPrice = 0;
               }
              if (payPriceVal) payPriceVal.value = _payPrice;
              if (payPrice) payPrice.innerText = _payPrice.toLocaleString();
            }else{
                alert('보유포인트보다 적게 입력해주세요.');
            }

        }

    });

    /* 포인트 사용 E */

});