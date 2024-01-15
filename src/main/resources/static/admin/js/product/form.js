window.addEventListener("DOMContentLoaded", function() {
    const { loadEditor } = commonLib;


    loadEditor("description")
        .then(editor => window.editor1 = editor)
        .catch(err => console.error(err));


    /* 이미지 본문 추가 이벤트 처리 S */
    const insertImages = document.getElementsByClassName("insert_image");
    for (const el of insertImages) {
        el.addEventListener("click", function() {
            const parentId = this.parentElement.parentElement.id;
            const editor = editor1;
            const url = this.dataset.url;

            insertImage(editor, url);
        });
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
    const domParser = new DOMParser();
    const target = document.getElementById("uploaded_files_description");

    for (const file of files) {

       const editor = editor1;

        insertImage(editor, file.fileUrl); // 에디터에 이미지 추가

       /* 템플릿 데이터 치환 S */
        let html = tpl;
        html = html.replace(/\[seq\]/g, file.seq)
                    .replace(/\[fileName\]/g, file.fileName)
                    .replace(/\[imageUrl\]/g, file.fileUrl);

        const dom = domParser.parseFromString(html, "text/html");
        const fileBox = dom.querySelector(".file_tpl_box");
        target.appendChild(fileBox);


        const el = fileBox.querySelector(".insert_image")
        if (el) {
            // 이미지 본문 추가 이벤트
            el.addEventListener("click", () => insertImage(editor, file.fileUrl));
        }

       /* 템플릿 데이터 치환 E */
    }

}



/**
* 에디터에 이미지 추가
*
*/
function insertImage(editor, source) {
    editor.execute('insertImage', { source });
}

/**
* 파일 삭제 후 후속 처리
*
* @param seq : 파일 등록 번호
*/
function callbackFileDelete(seq) {
    const fileBox = document.getElementById(`file_${seq}`);
    fileBox.classList.remove('uploaded');
    fileBox.style.backgroundImage = fileBox.style.backgroundPosition = fileBox.style.backgroundSize = null;
}