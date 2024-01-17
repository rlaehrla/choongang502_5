var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

/**
* 파일 업로드 콜백 함수
*/
function callbackFileUpload(files) {
     if (!files || files.length == 0) {
            return;
        }

    for(const file of files) {
        const seq = file.seq ;
        const fileName = file.fileName ;

            // location으로 구분
            /* 사업자등록증 파일 업로드하는 경우 S */
            if (file.location == "business_permit") {
                let html = document.getElementById("attach_tpl").innerHTML ;
                html = html.replace(/\[seq\]/g, seq)
                            .replace(/\[fileName\]/g, fileName);

                const domParser = new DOMParser();
                const dom = domParser.parseFromString(html, "text/html");

                const fileAttachEl = dom.querySelector(".file_tpl_box") ;

                const attachedFile = document.getElementById("attached_file") ;
                attachedFile.innerHTML += "" ;

                attachedFile.appendChild(fileAttachEl) ;
            }
            /* 사업자등록증 파일 업로드하는 경우 E */


            /* 프로필 이미지 업로드하는 경우 S */
            else if (file.location == "profile_img") {

                const imageUrl = file.thumbsUrl.length > 0 ? file.thumbsUrl.pop() : file.fileUrl;
                let html = document.getElementById("image1_tpl").innerHTML;

                html = html.replace(/\[seq\]/g, seq)
                            .replace(/\[imageUrl\]/g, imageUrl);

                const domParser = new DOMParser();
                const dom = domParser.parseFromString(html, "text/html");

                const imageTplEl = dom.querySelector(".image1_tpl_box");


                const profileImage = document.getElementById("profile_image");
                profileImage.innerHTML = "";

                profileImage.appendChild(imageTplEl);
            }
            /* 프로필 이미지 업로드하는 경우 E */
    }
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