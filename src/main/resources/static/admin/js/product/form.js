window.addEventListener("DOMContentLoaded", function() {
    const { loadEditor } = commonLib;

    loadEditor("description")
        .then(editor => window.editor = editor)
        .catch(err => console.error(err));


    /* 이미지 본문 추가 이벤트 처리 S */
    const insertImages = document.getElementsByClassName("insert_image");
    for (const el of insertImages) {
        el.addEventListener("click", (e) => insertImage(e.currentTarget.dataset.url));
    }
    /* 이미지 본문 추가 이벤트 처리 E */

});

/**
* 파일 업로드 후 후속처리 함수
*
*/
function callbackFileUpload(files) {
    if (!files || files.length == 0) {
        return;
    }

    const tpl = document.getElementById("editor_tpl").innerHTML;
    const tpl2 = document.getElementById("image1_tpl").innerHTML;

    const domParser = new DOMParser();
    const targetMain = document.getElementById("uploaded_files_product_main");
    const targetList = document.getElementById("uploaded_files_product_list");
    const targetDesc = document.getElementById("uploaded_files_description");

    const imageUrls = [];
    for (const file of files) {
        let target, html;
        const location = file.location;
        if(location == 'product_main'){
            target = targetMain;
            html = tpl2;
        }
        else if(location == 'product_list'){
            target = targetList;
            html = tpl2;
        }
        else{
            html = tpl;
            target = targetDesc;
            imageUrls.push(file.fileUrl);
        }

       /* 템플릿 데이터 치환 S */

        html = html.replace(/\[seq\]/g, file.seq)
                    .replace(/\[fileName\]/g, file.fileName)
                    .replace(/\[imageUrl\]/g, file.fileUrl);

        const dom = domParser.parseFromString(html, "text/html");
        const fileBox = location == 'description' ? dom.querySelector(".file_tpl_box") : dom.querySelector(".image1_tpl_box");
        target.appendChild(fileBox);


        const el = fileBox.querySelector(".insert_image")
        if (el) {
            // 이미지 본문 추가 이벤트
            el.addEventListener("click", () => insertImage(file.fileUrl));
        }
       /* 템플릿 데이터 치환 E */
    }

    insertImage(imageUrls);

}



/**
* 에디터에 이미지 추가
*
*/
function insertImage(source) {
    editor.execute('insertImage', { source });
}

/**
* 파일 삭제 후 후속 처리
*
* @param seq : 파일 등록 번호
*/
function callbackFileDelete(seq) {
   const fileBox = document.getElementById(`file_${seq}`);
       fileBox.parentElement.removeChild(fileBox);
}
/**
*
* 카테고리 선택 후 후속 처리
*
* @param data : 선택한 데이터
*/
