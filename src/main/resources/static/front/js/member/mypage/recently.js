window.addEventListener("DOMContentLoaded", async function(){

    // localStorage에 있는 product 불러오기
    const productItems = JSON.parse(localStorage.getItem("productItems"));

    const { ajaxLoad } = commonLib;

    if(productItems != null){

        const qs = Object.values(productItems).sort((a, b) => b[1] - a[1]).map(a => `seq=${a[0]}`).join("&");
        const url = `/api/product/recent?${qs}`;


        try {
            const result = await ajaxLoad('GET', url, null, 'json');

        } catch (err) {
            console.error(err);
        }


       // const key = Object.keys(productItems);
        const recentlyViewed = document.querySelector("#recentlyViewed");


        /*
        for(let el of key){

            el = el.replace(/^p\_/g, '');
            const html =  "<div class='item'>"
                             +"<a href='@{/product/detail/[seq])}' >"
                            +"    <div class='imgBox'>"
                            +"        <th:block th:if='*{listImages != null && listImages.size() != 0}' th:utext='*{@utils.printThumb(listImages[0].seq, 1000, 1000, 'product_main')}'></th:block>"
                            +"    </div>"
                            +"    <th:block th:unless='*{listImages != null && listImages.size() != 0}' width='100px' height='100px'></th:block>"

                            +"    <div class='cate  text-16px' th:each='cate : *{category}'>"
                            +"        <span th:text='${cate.cateNm}'></span>"
                            +"    </div>"
                            +"    <div  class='font-semibold text-16px'th:text='*{name}'></div>"
                            +"    <div class='flex items-center gap-2px'>"
                            +"        <div class='font-semibold pink  text-16px' th:if='*{discount != 0}'>"
                            +"           <th:block th:text='*{discount}'></th:block>"
                            +"           <th:block th:text='#{%}'></th:block>"
                            +"        </div>"
                            +"        <div class='font-semibold text-16px'>"
                            +"            <th:block th:text='*{consumerPrice}'></th:block>"
                            +"            <th:block th:text='#{돈단위}'></th:block>"
                            +"        </div>"
                            +"        <img class='discount_arrow' th:src='@{/image/discount-arrow.svg}'>"
                            +"    </div>"
                            +"    <div class='star'>"
                            +"        <i class='xi-star'></i>"
                            +"        <span th:text='${평점}'></span>"
                            +"    </div>"
                            +"</a>"
                         +"</div>";

            console.log(html.replace(/\[seq\]/g, el));
            recentlyViewed.innerHtml = html.replace(/\[seq\]/g, el);
        }
        */
    }
});