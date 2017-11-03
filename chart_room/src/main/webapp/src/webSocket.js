let wsUri ="ws://echo.websocket.org/";
let output;

let serverData;
let websocket;
export default {
    init:function (serverData) {
        serverData=serverData;
        openConnect();
    },
    send:function (userId,message,time) {
        doSend({
            fromUser:websocket.user.id,
            toUser:userId,
            messages:message,
            time:time
        })
    }
}
function openConnect() {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) {
        onOpen(evt)
    };
    websocket.onclose = function(evt) {
        onClose(evt)
    };
    websocket.onmessage = function(evt) {
        onMessage(evt)
    };
    websocket.onerror = function(evt) {
        onError(evt)
    };
}

function onOpen(evt) {

}

function onClose(evt) {

}

function onMessage(evt) {
    let type=evt.type;

    if(type==='login'){

    }else {
        checkeMessage(evt.fromUser, evt.message, evt.time);
    }
}

function onError(evt) {

}
function checkeMessage(fromUser,message,time) {
    let userIndex = -1;
    for(let $i=0;$i<serverData.userList.length; $i++){
        if(serverData.userList[$i].id === fromUser){
            break;
        }
    }
    if(userIndex===-1){
        userIndex=serverData.userList.length;
        serverData.userList.push({
            id: fromUser,
            name: fromUser,
            img: 'dist/images/2.png'
        });
        doSend()
    }else{

    }
}

function doSend(message) {
    websocket.send(message);
}
