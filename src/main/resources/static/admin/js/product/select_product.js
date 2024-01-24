window.addEventListener("DOMContentLoaded", function(){

    const selectProducts = document.getElementById("select_products");

    selectProducts.addEventListener("click", function(){
        const targetEl = document.querySelector("input[name='target']");
        const target = targetEl ? targetEl.value : "";


        const chks = document.querySelectorAll("input[name='chk']:checked");


        const items = [];
        for(const chk of chks){
            const item = JSON.parse(chk.dataset.item);
            items.push(item);
        }

        if(typeof parent.callbackPopupSelect == 'function'){
            parent.callbackPopupSelect(items, target);
        }

        parent.commonLib.popup.close();
    });

});