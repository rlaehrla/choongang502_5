const chat = {
    ws: null,
    roomId: null,
    messagesEl: null,
    init(ws) {
        this.ws = ws;
        this.roomId = frmChat.roomId.value;
        this.messagesEl = document.getElementById("messages");
    },
    send(message) {
        const nickName = frmChat.nickName.value.trim();
        if (!nickName) {
            alert("닉네임을 입력하세요.");
            frmChat.nickName.focus();
            return;
        }

        const roomId = this.roomId;
        const data = { roomId, nickName, message };

                 const { ajaxLoad } = commonLib;
                 const url = `/api/chat`;

                 const headerName = "Content-Type";

                 ajaxLoad('post', url, JSON.stringify(data), 'json', {[headerName]: "application/json; charset=UTF-8"})
                     .then(res => chat.ws.send(JSON.stringify(data)))
                     .catch(err => console.error(err));

                  frmChat.message.value = "";
                  frmChat.message.focus();
            },
            // 대화상자에 출력
            print(message) {
                const data = JSON.parse(message.data);
                if (data.roomId != this.roomId) { // 같은 방의 메세지만 출력
                    return;
                }

                const chat_message = `[${data.nickName}]${data.message}`;
                const li = document.createElement("li");
                const liText = document.createTextNode(chat_message);
                li.appendChild(liText);
                this.messagesEl.appendChild(li);
    }
};

window.addEventListener("DOMContentLoaded", function() {
    const ws = new WebSocket("ws://localhost:3000/chat");

    chat.init(ws);

    ws.onopen = function(e) {
        const message = `${frmChat.nickName.value}님 입장`;
        chat.send(message);
    };

    ws.onclose = function(e) {
        const message = `${frmChat.nickName.value}님 퇴장`;
        chat.send(message);
    };

    ws.onmessage = function(message) {
        chat.print(message);
    };

     frmChat.message.addEventListener("keyup", function(e) {
            if (e.keyCode == 13) { //  엔터키 눌렀을때 전송
                chat.send(this.value);
            }
        });

        frmChat.addEventListener("submit", function(e) {
            e.preventDefault();

            chat.send(frmChat.message.value);
        });
});