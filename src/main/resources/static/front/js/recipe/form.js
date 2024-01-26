const recipeForm = {
    /**
    * 입력항목 추가
    *
    */
    addItem(e) {
        const el = e.currentTarget;

        const inputBox = recipeForm.createItem(el.dataset.name);
        const targetEl = document.getElementById(el.dataset.id);
        if (targetEl) {
            targetEl.appendChild(inputBox);
        }

    },
    /**
    * 입력항목 제거
    *
    */
    deleteItem(e) {
    const el = e.currentTarget;
    const parentEl = el.parentElement;
    parentEl.parentElement.removeChild(parentEl);

    closeButton.className = "remove_item";
    buttonIcon.className = "xi-close";

    closeButton.appendChild(buttonIcon);
    },
    /**
    * 입력항목 생성
    *
    */
    createItem(name) {
        const rows = document.createElement("div");
        const inputText = document.createElement("input");
        const inputEa = document.createElement("input");
        const closeButton = document.createElement("button");
        const buttonIcon = document.createElement("i");

        if(name === 'requiredIng') {
                   inputText.placeholder="예) 당근";
                   inputEa.placeholder="1 개";

               } else if (name === 'subIng') {
                   inputText.placeholder="예) 달걀";
                   inputEa.placeholder="1 개";

               } else if (name === 'condiments') {
                   inputText.placeholder="예) 소금";
                   inputEa.placeholder="1 큰술";

               } else if (name === 'how' || name === 'tip') {
                   inputText.placeholder="만드는 방법을 입력하세요.";
               }

                rows.className = "item_box";
                inputText.type = "text";
                inputText.name = name;

                closeButton.type = "button";
                closeButton.className = "remove_item";
                buttonIcon.className = "xi-close";

                closeButton.appendChild(buttonIcon);

                rows.appendChild(inputText);

                   if(name !== 'how' && name !== 'tip') {
                       inputEa.type="text";
                       inputEa.name = `${name}Ea`;
                       rows.appendChild(inputEa);
                   }


                rows.appendChild(closeButton);
                closeButton.addEventListener("click", this.deleteItem);

                return rows;


    /*   if(name === 'requiredIng') {
                   inputText.placeholder="예) 당근";
                   inputEa.placeholder="1 개";

               } else if (name === 'subIng') {
                   inputText.placeholder="예) 달걀";
                   inputEa.placeholder="1 개";

               } else if (name === 'condiments') {
                   inputText.placeholder="예) 소금";
                   inputEa.placeholder="1 큰술";

               } else if (name === 'howto') {
                   inputText.placeholder="만드는 방법을 입력하세요.";
               } else if (name === 'tip') {
                   inputText.placeholder="TIP을 입력하세요.";
               }
               rows.className = "item_box";

               inputText.type = "text";


               inputText.name = name;
               inputText.id = 'content_' + name;
               console.log("id : "  + inputText.id);

               closeButton.type = "button";
               closeButton.className = "remove_item"
               buttonIcon.className = "xi-close";

               closeButton.appendChild(buttonIcon);

               rows.appendChild(inputText);


               if(name !== 'howto' && name !== 'tip') {
                   inputEa.type="text";
                   inputEa.name = `${name}Ea`;
                   rows.appendChild(inputEa);
               }
               rows.appendChild(closeButton);
               closeButton.addEventListener("click", this.deleteItem);

               return rows;*/
           }
       };

       window.addEventListener("DOMContentLoaded", function() {

           const thumbs = document.getElementsByClassName("image1_tpl_box");
           for (const el of thumbs) {
               thumbsClickHandler(el);
           }

            /* 입력 항목 추가 버튼 처리 S */
            const buttons = document.getElementsByClassName("add_input_item");
            for (const el of buttons) {
               el.addEventListener("click", recipeForm.addItem);
            }
            /* 입력 항목 추가 버튼 처리 E */

             /* 입력 항목 제거 버튼 처리 S */
                const closeButtons = document.getElementsByClassName("remove_item");
                for (const el of closeButtons) {
                    el.addEventListener("click", recipeForm.deleteItem)
                }
                /* 입력 항목 제거 버튼 처리 E */

                /* 기준량 버튼 이벤트 처리 S */
                const amountButtons = document.querySelectorAll(".amount_box button");
                const amountInputEa = document.querySelector(".amount_box input[type='number']");
                for (const el of amountButtons) {
                    el.addEventListener("click", function() {
                        let ea = amountInputEa.value;
                        ea = isNaN(ea) ? 1 : Number(ea);
                        if (this.classList.contains("down")) { // 수량 감소
                            ea--
                        } else { // 수량 증가
                            ea++;
                        }

                        ea = ea < 1 ? 1 : ea;

                        amountInputEa.value = ea;
                    });
                }
                /* 기준량 버튼 이벤트 처리 E */
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
           const url = this.dataset.url;
           mainImageEl.style.backgroundImage=`url('${url}')`;
         });
       }