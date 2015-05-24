'use strict';

var flag = 0;

function mes(message, user, id) {
    return {
        message: message,
        user: user,
        id: id
    };
};

var id = -1;
var user;

var messagesList = [];

var appState = {
    mainUrl: 'chat',
    taskList: [],
    token: 'TE11EN'
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
        div.message = msg.value;
        div.user = name.value;
        div.innerHTML = div.user + ': ' + div.message;
        parentElem.appendChild(div);
        addMessage(div.user, div.message, div.id);
    }
    msg.value = null;
}

function addMessage(name, msg, id) {
    var mess = mes(msg, name, id);
    messagesList.push(mess);
    post(appState.mainUrl, JSON.stringify(mess));
}

function select(elem) {
    if (elem.style.color == 'black') {
        elem.style.background = 'LightSlateGray';
        elem.style.color = 'red';
        id = elem.id;
        user = elem.user;
    }
    else {
        elem.style.background = 'Cornsilk';
        elem.style.color = 'black';
    }
}

function deleteClick() {
    var parentElem = document.body.children[3];
    var name = document.getElementById('entername');
    var children = document.getElementById('block2').childNodes;
    var count = 0;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
            count += 1;
        }
    }
    if (count == 1) {
        for (var i = 0; i < messagesList.length; i++) {
            if ( messagesList[i].id == id) {
                if (name.value == user.toString()) {
                    del(appState.mainUrl, JSON.stringify(messagesList[i]));
                    appState.messagesList.splice(i, 1);
                    remove(document.getElementById(id));
                }
            }
        }
   }
}

function remove(elem) {
    var parentElement = elem.parentNode;
    parentElement.removeChild(elem);
}

function renameClick() {
    var parentElem = document.body.children[3];
    var name = document.getElementById('entername');
    var msg = document.getElementById('entermessages');
    var div = document.createElement('div');
    div.setAttribute("onclick", 'select(this)');
    div.user = name.value;
    var count = 0;
    div.message = msg.value;
    div.user = name.value;
    div.id = id;
    div.innerHTML = div.user + ': ' + div.message;
    var children = document.getElementById('block2').childNodes;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
            var renameDiv = children[i];
            count += 1;
        }
    }
    if (name.value && msg.value) {
        if (count == 1)
            for (var i = 0; i < messagesList.length; i++)
                if (messagesList[i].id == id) {
                    if (user.toString() == name.value) {
                        messagesList[i].message = msg.value;
                        put(appState.mainUrl, JSON.stringify(messagesList[i]));
                        rename(renameDiv, div);
                    }
            }
        msg.value = null;
    }
}

function rename(elem, newElem) {
    var parentElem = document.body.children[3];
    parentElem.insertBefore(newElem, elem);
    parentElem.removeChild(elem);
}

function restore(continueWith) {
    var url = appState.mainUrl + '?token=' + appState.token;
    get(url, function (responseText) {
        console.assert(responseText != null);
        var response = JSON.parse(responseText);
        appState.token = response.token;
        if (response.messages)
            createAllMSG(response.messages);
        continueWith && continueWith();
    });
}

function get(url, continueWith, continueWithError) {
    ajax('GET', url, null, continueWith, continueWithError);
}

function post(url, data, continueWith, continueWithError) {
    ajax('POST', url, data, continueWith, continueWithError);
}

function put(url, data, continueWith, continueWithError) {
    ajax('PUT', url, data, continueWith, continueWithError);
}

function del(url, data, continueWith, continueWithError) {
    ajax('DELETE', url, data, continueWith, continueWithError);
}

function isError(text) {
    if (text == "")
        return false;

    try {
        var obj = JSON.parse(text);
    } catch (ex) {
        return true;
    }

    return !!obj.error;
}

function defaultErrorHandler(mess) {
    console.error(mess);
}

function ajax(method, url, data, continueWith, continueWithError) {
    var xhr = new XMLHttpRequest();

    continueWithError = continueWithError || defaultErrorHandler;
    xhr.open(method || 'GET', url, true);

    xhr.onload = function () {
        if (xhr.readyState !== 4)
            return;
        if (xhr.status != 200) {
            continueWithError('Error on the server side, response ' + xhr.status);
            return;
        }

        if (isError(xhr.responseText)) {
            continueWithError('Error on the server side, response ' + xhr.responseText);
            return;
        }
        if (xhr.responseText) {
            continueWith(xhr.responseText);
        }
    };

    xhr.onimeout = function () {
        continueWithError('Server timed out !');
    }

    xhr.onerror = function (e) {
        var errMsg = 'Server connection error !\n' +
        '\n' +
        'Check if \n' +
        '- server is active\n' +
        '- server sends header "Access-Control-Allow-Origin:*"';

        continueWithError(errMsg);
    };

    xhr.send(data);
}

function run() {
    restore();
}

function createAllMSG(arr) {
    var parentElem = document.body.children[3];
    for (var i = 0; i < arr.length; i++) {
        var div = document.createElement('div');
        div.setAttribute("onclick", 'select(this)');
        div.id = arr[i].id;
        div.user = arr[i].user;
        div.message = arr[i].message;
        div.innerHTML = div.user + ': ' + div.message;
        parentElem.appendChild(div);
        messagesList.push(arr[i]);
    }
}
