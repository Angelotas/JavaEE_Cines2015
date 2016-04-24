/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//CONEXIÓN CON WEBSOCKET
var wsUri = 'ws://' + document.location.host
    + document.location.pathname.substr(0,
    document.location.pathname.indexOf("/faces")) + '/websocket';

//VARIABLES
console.log(wsUri);
var websocket = new WebSocket(wsUri); //Inicializa el websocket
var textField = document.getElementById("texto"); //Llama al campo input texto
var users = document.getElementById("users"); //Llama al campo textarea users
var chatlog = document.getElementById("chatlog"); //text area de los mensajes
var output = document.getElementById("output"); //div final donde sale quien se conecta, quien ha escrito...
var username;
//MÉTODOS
function join() {
    username = textField.value;
    websocket.send(username + " joined");
}
function send_message() {
    websocket.send(username + ": " + textField.value);
}
function disconnect() {
    websocket.close();
}
//LISTENERS
websocket.onopen = function (evt) {
    onOpen(evt);
};
websocket.onmessage = function (evt) {
    onMessage(evt);
};
websocket.onerror = function (evt) {
    onError(evt);
};
websocket.onclose = function (evt) {
    onClose(evt);
};
//FUNCIONES A LAS QUE LLAMAN LOS LISTENERS
function onOpen() {
    writeToScreen("CONNECTED");
}
function onClose() {
    writeToScreen("DISCONNECTED");
}
function onMessage(evt) {
    writeToScreen("RECEIVED: " + evt.data);
    if (evt.data.indexOf("joined") !== -1) { //si el texto contiene "join" es que se ha unido
        users.innerHTML += evt.data.substring(0, evt.data.indexOf("joined")) + "\n";
    } else{  //si no, escribe el mensaje en el textarea
        chatlog.innerHTML += evt.data + "\n";
    }
}
function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}