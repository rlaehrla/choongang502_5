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
