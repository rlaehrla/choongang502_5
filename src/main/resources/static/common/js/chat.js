window.addEventListener("DOMContentLoaded", function() {
    const webSocket = new WebSocket("ws://localhost:3000/chat");

    webSocket.onopen = function(e) {
       console.log("연결 성립!", e);
    };

    webSocket.onclose = function(e) {
       console.log("연결 종료!", e);
    };
});