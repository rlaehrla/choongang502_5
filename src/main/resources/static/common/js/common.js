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
                const resultData = responseType == 'json' ? xhr.response : xhr.responseText;

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