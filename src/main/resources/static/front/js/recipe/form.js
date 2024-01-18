window.addEventListener("DOMContentLoaded", function() {
    const thumbs = document.getElementsByClassName("image1_tpl_box");
    for (const el of thumbs) {
        thumbsClickHandler(el);
    }
});

/**
* 파일 업로드 처리 콜백
*
*/
function callbackFileUpload(files) {
    const tpl = document.getElementById("image1_tpl").innerHTML;
    const domParser = new DOMParser();
    const targetEl = document.getElementById("main_images");
    const thumbsEl = document.querySelector(".main_image_box .thumbs");
    for (const file of files) {

        let html = tpl;
        html = html.replace(/\[seq\]/g, file.seq)
                    .replace(/\[imageUrl\]/g, file.fileUrl)
                    .replace(/\[fileName\]/g, file.fileName);

        const dom = domParser.parseFromString(html, "text/html");
        const imageBox = dom.querySelector(".image1_tpl_box");

        if (!targetEl.classList.contains("uploaded")) {
            targetEl.classList.add("uploaded")
        }

        targetEl.style.backgroundImage=`url('${file.fileUrl}')`;
        targetEl.style.backgroundSize='cover';
        targetEl.style.backgroundRepeat='no-repeat';
        targetEl.style.backgroundPosition="center center";

        thumbsEl.appendChild(imageBox);
        thumbsClickHandler(imageBox);
    }
}


/**
* 파일 삭제 처리 콜백
*
*/
function callbackFileDelete(seq) {
    const el = document.getElementById(`file_${seq}`);
    el.parentElement.removeChild(el);

    const thumbs = document.getElementsByClassName("image1_tpl_box");
    if (thumbs.length > 0) {
        thumbs[0].click();
    } else {
        const mainImageEl = document.getElementById("main_images");
        mainImageEl.style.backgroundImage = null;
        mainImageEl.classList.remove("uploaded");
    }

}

/**
* 썸네일 클릭 이벤트 처리
*
*/
function thumbsClickHandler(thumb) {

   const mainImageEl = document.getElementById("main_images");

   thumb.addEventListener("click", function() {
    console.log("클릭!")
    const url = this.dataset.url;
    mainImageEl.style.backgroundImage=`url('${url}')`;
  });
}