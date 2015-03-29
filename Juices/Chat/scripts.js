var messageList = [];

//var lastToken = 'chat';

//var url = 'http://localhost:999/chat?token=TN11EN';

//ajax('GET', url, function (jsonResult){

//    document.body.innerHTML = jsonResult;

//});

//function ajax(method, url, toReturn) {

//    var xhr = new XMLHttpRequest();
//    xhr.open(method || 'GET', url, true);
//    xhr.onload = function () {
//       // if (xhr.readyState != 4)
//           // return;
//        toReturn(xhr.responseText);
//    };
//    xhr.ontimeout = function () { toReturn('Timed out !'); };
//    xhr.onerror = function (e) { toReturn('Error !'); };
//    xhr.send();
//}

//callServer(lastToken, function (response) {

//    var data = JSON.parse(response.content);

//    document.body.innerHTML = data.messages[0];

//    lastToken = data.token;

//});

function theMessage(message, nick, id) {
    return {
        description: message,
        nameId: nick,
        id: id
    };
};

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random).toString();
};

function sendClick() {
    var name = document.getElementById('entername');
    var msg = document.getElementById('entermessages');
    if (name.value && msg.value) {
        var parentElem = document.body.children[3];
        var div = document.createElement('div');
        div.setAttribute("onclick", 'select(this)');
        div.id = uniqueId();
        messageList.push(theMessage(msg.value, name.value, div.id));
        div.description = msg.value;
        div.nameId = name.value;
        div.innerHTML = div.nameId + ': ' + div.description;
        parentElem.appendChild(div);
        store(messageList);
    }
    msg.value = null;
}

function select(elem) {
    if (elem.style.color == 'black') {
        elem.style.background = 'LightSlateGray';
        elem.style.color = 'red';
    }
    else {
        elem.style.background = 'Cornsilk';
        elem.style.color = 'black';
    }
}

function deleteClick() {
    var name = document.getElementById('entername');
    var children = document.getElementById('block2').childNodes;
    var count = 0;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
            var deleteDiv = children[i];
            count += 1;
        }
    }
    if (count == 1)
        for (var i = 0; i < messageList.length; i++) {
            if (messageList[i].id == deleteDiv.id)
                if (deleteDiv.nameId == name.value) {
                    messageList.splice(i, 1);
                    store(messageList);
                    remove(deleteDiv);
                }
        }
}

function remove(elem) {
    var parentElement = elem.parentNode;
    parentElement.removeChild(elem);
}

function renameClick() {
    var name = document.getElementById('entername');
    var msg = document.getElementById('entermessages');
    var div = document.createElement('div');
    div.setAttribute("onclick", 'select(this)');
    div.nameId = name.value;
    var count = 0;
    div.description = msg.value;
    div.nameId = name.value;
    div.innerHTML = div.nameId + ': ' + div.description;
    var children = document.getElementById('block2').childNodes;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
            var renameDiv = children[i];
            count += 1;
        }
    }
    if (name.value && msg.value) {
        if (count == 1)
            for (var i = 0; i < messageList.length; i++) {
                if (messageList[i].id == renameDiv.id)
                    if (renameDiv.nameId == name.value) {
                        messageList[i].description = msg.value;
                        store(messageList);
                        rename(renameDiv, div);
                    }
            }
        msg.value = null;
    }
}

function rename(elem, newElem) {
    var parentElement = elem.parentNode;
    newElem.id = elem.id;
    parentElement.insertBefore(newElem, elem);
    parentElement.removeChild(elem);
}

function store(listToSave) {
    //output(listToSave);
    if (typeof (Storage) == "undefined") {
        return;
    }
    localStorage.setItem("Chatik", JSON.stringify(listToSave));
}

function createAllMSG(arr) {
    var parentElem = document.body.children[3];
    for (var i = 0; i < arr.length; i++) {
        var div = document.createElement('div');
        div.setAttribute("onclick", 'select(this)');
        div.id = arr[i].id;
        div.nameId = arr[i].nameId;
        div.description = arr[i].description;
        div.innerHTML = div.nameId + ': ' + div.description;
        parentElem.appendChild(div);
        messageList.push(arr[i]);
        document.getElementById('entername').value = arr[arr.length - 1].nameId;
    }
}

function restore() {
    if (typeof (Storage) == "undefined") {
        return;
    }

    var item = localStorage.getItem("Chatik");
    return item && JSON.parse(item);
}

function run() {
    //localStorage.clear();
    var array = restore() || [];
    createAllMSG(array);
   // output(messageList);
}

//function output(value) {
//    var output = document.getElementById('output');

//    output.innerText = "var taskList = " + JSON.stringify(value, null, 4) + ";";
//}