/**
 * Created by nebios on 11.05.15.
 */

// establish the communication channel over a websocket
//var ws;
//var ws = new WebSocket("ws://127.0.0.1:7666/loot/ServerSocket");
//var ws = new WebSocket("ws://192.168.1.9:7666/loot/ServerSocket");
//var ws = new WebSocket("ws://echo.websocket.org");
var ws = new WebSocket("ws://phil-m.eu:7666/loot/ServerSocket");

var myUID = -1;

function connect() {
//  ws = new WebSocket("ws://127.0.0.1:7666/loot/ServerSocket");
    //ws =new WebSocket("ws://192.168.1.9:7666/loot/ServerSocket");
    ws = new WebSocket("ws://phil-m.eu:7666/loot/ServerSocket");
}
// called when socket connection established
ws.onopen = function() {
    appendLog("Connected ...")
};

// called when a message received from server
ws.onmessage = function (evt) {
    console.log(evt.data);
    appendLog(evt.data);
    var response = JSON.parse(evt.data);
    console.log(response);
    handleResponse(response);
    //if ( response.error != null ) 
    //    appendLog(response.error);
    //else {
        //if (response.result != "null")
    //    appendLog(response.result);
    //    handleResponse(response);    
        //else
        //    appendLog("Login Failed");
    //}
};

// called when socket connection closed
ws.onclose = function() {
    appendLog("Disconnected")
};

// called in case of an error
ws.onerror = function(err) {
    console.log("ERROR!", err )
};

// appends logText to log text area
function appendLog(logText) {
    var log = document.getElementById("log");
    log.value = log.value + logText + "\n";
}

// sends msg to the server over websocket
function sendToServer(msg) {
    ws.send(msg);
    appendLog("send: " + msg);
}


function handleResponse(response){
    if( response.error != null );
        //appendLog(response.error);
    else {
        switch(response.id) {
            case "0":
                myUID = response.result;
                console.log("myUID = " + myUID );
                break;
            default:
        }
    }
}
