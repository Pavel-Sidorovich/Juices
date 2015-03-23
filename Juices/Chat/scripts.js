var messageList = [];

function theMessage(message, nick, done, id) {
    return {
        description: message,
        nameId: nick,
        done: done,
        id: id
    };
};

function uniqueId() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random).toString();
};

function sendClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var TheTextBoxOne = document.getElementById('entermessages');
    if (TheTextBoxTwo.value && TheTextBoxOne.value) {
        var parentElem = document.body.children[3];
        var d = document.createElement('div');
        d.setAttribute("onclick", 'select(this)');
        d.id = uniqueId();
        messageList.push(theMessage(TheTextBoxOne.value, TheTextBoxTwo.value, true, d.id));
        d.done = true;
        d.description = TheTextBoxOne.value;
        d.nameId = TheTextBoxTwo.value;
        d.innerHTML = d.nameId + ': ' + d.description;
        parentElem.appendChild(d);
        store(messageList);
    }
    TheTextBoxOne.value = null;
}

function select(el) {
    if (el.style.color == 'black') {
        el.style.background = 'LightSlateGray';
        el.style.color = 'red';
    }
    else {
        el.style.background = 'Cornsilk';
        el.style.color = 'black';
    }
}

function deleteClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var children = document.getElementById('block2').childNodes;
    var count = 0;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
            var d2 = children[i];
            count += 1;
        }
    }
    if (count == 1)
        for (var i = 0; i < messageList.length; i++) {
            if (messageList[i].id == d2.id)
                if (d2.nameId == TheTextBoxTwo.value) {
                    messageList[i].done = false;
                    store(messageList);
                    remove(d2);
                }
        }
}

function remove(elem) {
    var parentElement = elem.parentNode;
    parentElement.removeChild(elem);
}

function renameClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var TheTextBoxOne = document.getElementById('entermessages');
    var d = document.createElement('div');
    d.setAttribute("onclick", 'select(this)');
    d.nameId = TheTextBoxTwo.value;
    var count = 0;
    d.done = true;
    d.description = TheTextBoxOne.value;
    d.nameId = TheTextBoxTwo.value;
    d.innerHTML = d.nameId + ': ' + d.description;
    var children = document.getElementById('block2').childNodes;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
            var d2 = children[i];
            count += 1;
        }
    }
    if (TheTextBoxTwo.value && TheTextBoxOne.value) {
        if (count == 1)
            for (var i = 0; i < messageList.length; i++) {
                if (messageList[i].id == d2.id)
                    if (d2.nameId == TheTextBoxTwo.value) {
                        messageList[i].description = TheTextBoxOne.value;
                        store(messageList);
                        rename(d2, d);
                    }
            }
        TheTextBoxOne.value = null;
    }
}

function rename(elem, d) {
    var parentElement = elem.parentNode;
    d.id = elem.id;
    parentElement.insertBefore(d, elem);
    parentElement.removeChild(elem);
}

function store(listToSave) {
   // output(listToSave);
    if (typeof (Storage) == "undefined") {
        return;
    }
    localStorage.setItem("Chatik", JSON.stringify(listToSave));
}

function createAllMSG(arr) {
    var parentElem = document.body.children[3];
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].done != false) {
            var d = document.createElement('div');
            d.setAttribute("onclick", 'select(this)');
            d.id = arr[i].id;
            d.nameId = arr[i].nameId;
            d.description = arr[i].description;
            d.innerHTML = d.nameId + ': ' + d.description;
            parentElem.appendChild(d);
            messageList.push(arr[i]);
        }
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
    //output(messageList);
}

//function output(value) {
//    var output = document.getElementById('output');

//    output.innerText = "var taskList = " + JSON.stringify(value, null, 4) + ";";
//}