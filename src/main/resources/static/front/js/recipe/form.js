
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

        // 무조건 있어야 함
        const main_image = document.getElementById("main_image");
        // 없을 수도, 여러 개일 수도 있음
/*        const howTo_image = document.getElementById("howTo_image");*/
        main_image.innerHTML = "";
/*        howTo_image.innerHTML = "";*/

        main_image.appendChild(imageTplEl);
        /*howTo_image.appendChild(imageTplEl);*/
}

/*
* 파일 삭제 후 후속처리 함수
*
* @param seq : 파일 등록 번호
*/

function callbackFileDelete(seq) {
    const fileEl = document.getElementById(`file_${seq}`);
    fileEl.parentElement.removeChild(fileEl);
}



/*window.addEventListener("DOMContentLoaded", function() {
    const { loadEditor } = commonLib;


    loadEditor("html_top")
        .then(editor => window.editor1 = editor)
        .catch(err => console.error(err));

    loadEditor("html_bottom")
        .then(editor => window.editor2 = editor)
        .catch(err => console.error(err));
});

*//*
*/
/**
* 파일 업로드 후 후속처리 함수
*
*//*
*/
/*
function callbackFileUpload(files) {
    if (!files || files.length == 0) {
        return;
    }

    const tpl = document.getElementById("image1_tpl").innerHTML;
    const domParser = new DOMParser();
    const targetTop = document.getElementById("main_image");
    const targetBottom = document.getElementById("howTo_image");

    for (const file of files) {
        const editor = file.location == 'html_bottom' ? editor2 : editor1;
        const target = file.location == 'html_bottom' ? targetBottom : targetTop;

        editor.execute('insertImage', { source: file.fileUrl });

*//*
*/
/*        html = html.replace(/\[seq\]/g, seq)
                            .replace(/\[imageUrl\]/g, imageUrl);*//*
*/
/*

        *//*
*/
/* 템플릿 데이터 치환 S *//*
*/
/*
         let html = tpl;
         html = html.replace(/\[seq\]/g, file.seq)
                     .replace(/\[imageUrl\]/g, file.imageName)
                     .replace(/\[imageUrl\]/g, file.imageUrl);

         const dom = domParser.parseFromString(html, "text/html");
         const fileBox = dom.querySelector(".image1_tpl_box");
         target.appendChild(fileBox);

        *//*
*/
/* 템플릿 데이터 치환 E *//*
*/
/*
    }
}*//*



*/
