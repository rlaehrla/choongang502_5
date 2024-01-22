window.addEventListener("DOMContentLoaded", function() {
    // eqOrder 버튼 클릭시 주문자정보 == 받는분 정보

    const eqOrder = document.querySelector("#eqOrder");

    eqOrder.addEventListener("click", function() {
        if(eqOrder.checked){
            const orderName = document.querySelector("#orderName").value;
            const orderCellPhone = document.querySelector("#orderCellPhone").value;
            console.log(orderCellPhone);
            const receiverName = document.querySelector("#receiverName");
            const receiverCellPhone = document.querySelector("#receiverCellPhone");

            receiverName.value = orderName;
            receiverCellPhone.value = orderCellPhone;

            receiverName.setAttribute("readonly", true);
            receiverCellPhone.setAttribute("readonly", true);
        }else{
            receiverName.removeAttribute("readonly");
            receiverCellPhone.removeAttribute("readonly");
        }



    });

});