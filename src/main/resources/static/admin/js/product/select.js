window.addEventListener("DOMContentLoaded", function() {
    const { ajaxLoad } = commonLib;

    const buttons = document.querySelectorAll(".category_button_area button");

    const tpl = document.getElementById("tpl").innerHTML;
    const domParser = new DOMParser();
    for (const el of buttons) {
        el.addEventListener("click", async function() {

            let mainCategory = this.dataset.filter;
            mainCategory = mainCategory == 'all' ? '' : mainCategory;

            for (const el of buttons) {
                el.classList.remove("active");
            }

            this.classList.add("active");

            try {
                const data = await ajaxLoad("GET", `/api/product/select?mainCategory=${mainCategory}`, null, 'json')
                if (!data.success) return;

                const targetEl = document.querySelector(".category_list ul");
                targetEl.innerHTML = "";

                const items = data.data;

                for (const item of items) {
                    let html = tpl;
                    html = html.replace(/\[cateCd\]/g, item.cateCd)
                            .replace(/\[cateNm\]/g, item.cateNm);

                    const dom = domParser.parseFromString(html, "text/html");
                    const li = dom.querySelector("li");
                    const chk = li.querySelector(".cateCd");
                    chk.addEventListener("click", function() {
                        if (typeof parent.callbackCategorySelect == 'function') {
                            const cateCd = this.value;
                            const cateNm = this.dataset.cateNm;
                            parent.callbackCategorySelect({ cateCd, cateNm });
                        }
                    });

                    targetEl.appendChild(li);
                }

            } catch (err) {
                console.log(err);
            }

        });
    }
    // 선택 완료 버튼 클릭시
    const select_done = document.querySelector("#select_done");
    select_done.addEventListener("click", function(){
        // 상위 파일에 선택사항 넘김

        // 창 닫기


    });

    // 처음 로딩되면 첫번째 탭(전체) 클릭
    buttons[0].click();
});


function callbackCategorySelect(data) {
    console.log(data);
}
/*
const filterMenuInit = () => {
        const filters = document.querySelectorAll('[data-filter-id]');

        filters.forEach(filter => {
            const filterBtns = [...filter.querySelectorAll('[data-filter]')].filter(el => el.nodeName === 'BUTTON');
            const filterLists = [...filter.querySelectorAll('[data-filter]')].filter(el => el.nodeName === 'LI');

            filterBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    const filterType = btn.getAttribute('data-filter');

                    filterBtns.forEach(btn => btn.classList.remove('active'));
                    btn.classList.add('active');

                    filterLists.forEach(list => {
                        if (filterType === 'all'){
                            list.style.display = 'list-item';
                            return;
                        }

                        list.style.display = list.getAttribute('data-filter') === filterType ? 'list-item' : 'none';
                    })
                });
            })
        })
    };

    filterMenuInit();
*/
