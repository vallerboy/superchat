    function onLoad() {
        var ws = "ws://localhost:8080/chat";
        websocket = new WebSocket(ws);
        websocket.onopen = function (ev) {
            onOpen(ev)
        };
        websocket.onmessage =  function (ev) {
            onMessage(ev);
        }
    }

    function onOpen(ev) {
        console.log("Połączono");
    }

    function onMessage(ev) {
        var message = ev.data;

        messages.innerHTML = messages.innerHTML + "<li class = \"message\">" + message + "</li>";
        messages.scrollTop = messages.scrollHeight;
    }

    function sendMessage() {
        var message = writer.value;
        writer.value = "";

        websocket.send(message);
    }