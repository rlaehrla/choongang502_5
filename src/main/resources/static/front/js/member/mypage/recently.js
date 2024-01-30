window.addEventListener("DOMContentLoaded", async function(){

    // localStorage에 있는 product 불러오기
    const productItems = JSON.parse(localStorage.getItem("productItems"));

    const { ajaxLoad } = commonLib;

    if(productItems != null){

        const qs = Object.values(productItems).sort((a, b) => b[1] - a[1]).map(a => `seq=${a[0]}`).join("&");
        const url = `/api/mypage/recentlyview?${qs}`;

        const recentlyViewed = document.querySelector("#recentlyViewed");
        const tpl = document.getElementById("product_item_tpl").innerHTML;
        const domParser = new DOMParser();
        try {
            const result = await ajaxLoad('GET', url, null, 'json');
            if (result.success) {
                const items = result.data;
                for (const item of items) {
                    let html = tpl;
                    const type = item.discountType == 'PERCENT' ? '%' : '원';
                    html = html.replace(/\[seq\]/g, item.seq)
                                .replace(/\[image\]/g, item.listImages.length > 0 ?
                                             `<img src=${item.listImages[0].fileUrl} width='100' height='100'>`
                                             : "<div width='100' height='100'></div>")
                                .replace(/\[cate\]/g, item.category.cateNm)
                                .replace(/\[name\]/g, item.name)
                                .replace(/\[discount\]/g, item.discount != 0? item.discount : '')
                                .replace(/\[discountType\]/g, item.discount != 0? type : '')
                                .replace(/\[consumerPrice\]/g, item.consumerPrice);

                    const dom = domParser.parseFromString(html, "text/html");

                    const li = dom.querySelector("li");
                    recentlyViewed.appendChild(li);
                }
            }
        } catch (err) {
            console.error(err);
        }





    }
});


