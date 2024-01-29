$(document).ready(function(){
    // 버튼을 클릭했을 때 실행될 함수
    $("#paymentButton").click(function(){
        const IMP = window.IMP;
        IMP.init('iamport');
        let msg;

        const productNames = document.querySelector(".product_name");
        let prName = Array.isArray(productNames)
            ? productNames[0].innerText + '외 ' + (productNames.length - 1) + '건'
            : productNames.innerText;

        const payPrice = document.querySelector("#pay_price");
        const finalPrice = Number(payPrice.innerText.replace(/\,/g, ''));

        // 결제 금액이 0원 이상인 경우에만 결제 창 열기
        if (finalPrice > 0) {
            // 아임포트 결제 창 열기 코드 (이 부분은 원하시는 결제 API에 따라 수정해야 함)
            IMP.request_pay({
                pg: 'kakaopay',
                pay_method: 'card',
                merchant_uid: 'merchant_' + new Date().getTime(),
                name: prName,
                amount: finalPrice,
                buyer_email: 'gg',
                buyer_name: 'gg',
                buyer_tel: '33',
                buyer_addr: 'gg',
                buyer_postcode: '123-456',
                //m_redirect_url: 'http://www.naver.com'
            }, function(rsp) {
                if (rsp.success) {
                    // 결제 성공 시 서버로 결제 정보 전송
                    jQuery.ajax({
                        url: "/payments/complete",
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            imp_uid: rsp.imp_uid
                            // 기타 필요한 데이터가 있으면 추가 전달
                        }
                    }).done(function(data) {
                        // 서버에서 결제정보 확인 및 서비스 루틴이 정상적인 경우
                        if (everythings_fine) {
                            msg = '결제가 완료되었습니다.';
                            msg += '\n고유ID : ' + rsp.imp_uid;
                            msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                            msg += '\결제 금액 : ' + rsp.paid_amount;
                            msg += '카드 승인번호 : ' + rsp.apply_num;
                            alert(msg);
                        } else {
                            // 결제가 되지 않았을 경우의 처리
                        }
                    });
                    // 결제 성공 시 이동할 페이지
                    location.href = '/order/paySuccess?msg=' + msg;
                } else {
                    // 결제 실패 시 이동할 페이지
                    location.href = "/order/payFail";
                }
            });
        } else {
            // 결제 금액이 0원일 경우, 결제 완료 페이지로 이동
            location.href = '/order/paySuccess?msg=결제가 완료되었습니다.';
        }
    });
});