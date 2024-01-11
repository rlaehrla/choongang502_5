var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

function callbackFileUpload(files) {
     if (!files || files.length == 0) {
            return;
        }

        const file = files[0];

        let html = document.getElementById("image1_tpl").innerHTML;

        const imageUrl = file.thumbsUrl.length > 0 ? file.thumbsUrl.pop() : file.fileUrl;
        const seq = file.seq;

        html = html.replace(/\[seq\]/g, seq)
                    .replace(/\[imageUrl\]/g, imageUrl);

        const domParser = new DOMParser();
        const dom = domParser.parseFromString(html, "text/html");

        const imageTplEl = dom.querySelector(".image1_tpl_box");


        const profileImage = document.getElementById("profile_image");
        profileImage.innerHTML = "";

        profileImage.appendChild(imageTplEl);
}

/**
* 파일 삭제 후 후속처리 함수
*
* @param seq : 파일 등록 번호
*/
function callbackFileDelete(seq) {
    const fileEl = document.getElementById(`file_${seq}`);
    fileEl.parentElement.removeChild(fileEl);
}

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=	30c3d31753d6d4b0cc09566b021bca87"></script>
<script type="text/javascript">

	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
		level: 3 //지도의 레벨(확대, 축소 정도)
	};

	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

</script>