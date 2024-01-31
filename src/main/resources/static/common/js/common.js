var commonLib = commonLib || {};

/**
* ajax 처리 : 요청, 응답 편의 함수
*
* @param method : 요청 메서드 - GET, POST, PUT ...
* @param url : 요청 URL
* @param params : 요청 Body에 담길 데이터 (POST, PUT, PATCH ...)
* @param responseType : json ==> 응답 결과를 json 변환 | 아닌 경우는 문자열로 반환
*/
commonLib.ajaxLoad = function(method, url, params, responseType) {
    method = !method || !method.trim() ? "GET" : method.toUpperCase();
    params = params || null ;
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.setRequestHeader(header, token);

        xhr.send(params);    // 요청 Body에 담길 데이터 (키=값&키=값&... 또는 FormData 객체)
        responseType = responseType?responseType.toLowerCase():undefined;
        if (responseType == 'json') {
            xhr.responseType=responseType;
        }

        xhr.onreadystatechange = function() {
            if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                const resultData = responseType == 'json' && xhr.responseText ? xhr.response : xhr.responseText;

                resolve(resultData);    // 성공 시 응답 데이터
            }
        };

        xhr.onabort = function(err) {    // 중단 시
            reject(err);
        };

        xhr.onerror = function(err) {    // 요청 또는 응답 오류 발생 시
            reject(err);
        };

        xhr.ontimeout = function(err) {
            reject(err);
        };
    });
};

/**
* 이메일 인증 메일 보내기
*
* @param email : 인증할 이메일
*/
commonLib.sendEmailVerify = function(email) {
    const { ajaxLoad } = commonLib;

    const url = `/api/email/verify?email=${email}`;

    ajaxLoad("GET", url, null, "json")
        .then(data => {
            if (typeof callbackEmailVerify == 'function') { // 이메일 승인 코드 메일 전송 완료 후 처리 콜백
                callbackEmailVerify(data);
            }
        })
        .catch(err => console.error(err));
};

/**
* 인증 메일 코드 검증 처리
*
*/
commonLib.sendEmailVerifyCheck = function(authNum) {
    const { ajaxLoad } = commonLib;
    const url = `/api/email/auth_check?authNum=${authNum}`;

    ajaxLoad("GET", url, null, "json")
        .then(data => {
            if (typeof callbackEmailVerifyCheck == 'function') { // 인증 메일 코드 검증 요청 완료 후 처리 콜백
                callbackEmailVerifyCheck(data);
            }
        })
        .catch(err => console.error(err));
};

/**
* 위지윅 에디터 로드
*
*/

commonLib.loadEditor = function(id, height) {
    if (!id) {
        return;
    }

    height = height || 450;

    // ClassicEditor
    return ClassicEditor.create(document.getElementById(id), {
        height
    });
}


window.addEventListener("DOMContentLoaded", function() {
    /* money 클래스 숫자 -> 세자리수마다 콤마 추가 S */
    const numbers = document.querySelectorAll(".money");

    for(const num of numbers){
        let el = parseInt(num.innerText);
        if(el >= 1000){
            el = el.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

        num.innerText = el;
    }

    /* money 클래스 숫자 -> 세자리수마다 콤마 추가 E */

});

// 핸드폰 ###-####-#### 자동 하이픈 생성 코드
const autoHyphen = (target) => {
 target.value = target.value
   .replace(/[^0-9]/g, '')
   .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}


/** 상품 상세 유입시 상품번호 로컬 스토리지에 저장 */
if (location.href.indexOf("product/detail/") != -1) {
    const regex = /.*product\/detail\/(\d*)/;
    if (regex.test(location.href)) {
        const productNo = regex.exec(location.href)[1];

        const productItems = JSON.parse(localStorage.getItem("productItems")) || {};
        productItems[`p_${productNo}`] = [productNo, Date.now()];

        localStorage.setItem("productItems", JSON.stringify(productItems));

    }
}
