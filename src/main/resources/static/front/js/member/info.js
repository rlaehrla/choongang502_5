window.addEventListener("DOMContentLoaded", function() {

    const cancelBtn = document.querySelector('#cancelBtn') ;
    cancelBtn.addEventListener("click", function() {
        history.back() ;
    });
})

// 핸드폰 ###-####-#### 자동 하이픈 생성 코드
const autoHyphen = (target) => {
 target.value = target.value
   .replace(/[^0-9]/g, '')
   .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}
