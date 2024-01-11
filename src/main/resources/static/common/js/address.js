var commonLib = commonLib || {};

commonLib.address = {
    /**
    * 다음 주소 API 동적 로딩
    *
    */
    init() {
        const el = document.getElementById("address_api_script");
        if (el) {
            el.parentElement.removeChild(el);
        }

        const script = document.createElement("script");
        script.src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
        script.id="address_api_script";

        document.head.insertBefore(script, document.getElementsByTagName("script")[0]);
    },
    /**
    * 주소 검색
    *
    */
    search(e) {
        const dataset = e.currentTarget.dataset;
        const zonecodeEl = document.getElementById(dataset.zonecodeId);
        const addressEl = document.getElementById(dataset.addressId);

        new daum.Postcode({
            oncomplete: function(data) {
                zonecodeEl.value = data.zonecode;
                addressEl.value = data.roadAddress
            }
        }).open();
    }

}

window.addEventListener("DOMContentLoaded", function(event) {
    const { address } = commonLib;

    // 초기 주소 API 로드
    address.init();

    const searchAddresses = document.getElementsByClassName("search_address");
    for (const el of searchAddresses) {
        el.addEventListener("click", address.search);
    }
});