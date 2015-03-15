function sendClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var TheTextBoxOne = document.getElementById('entermessages');
    if (TheTextBoxTwo.value && TheTextBoxOne.value) {
        var parentElem = document.body.children[3];
        var d = document.createElement('div');
        //d.id = 
        d.nameId = TheTextBoxTwo.value;
        d.innerHTML = TheTextBoxTwo.value + ': ' + TheTextBoxOne.value;
        parentElem.appendChild(d);
    }
    TheTextBoxOne.value = null;
}

function remove(elem) {
    var parentElement = elem.parentNode;
    parentElement.removeChild(elem);
}

function deleteClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var children = document.getElementById('block2').childNodes;
    var count = 0;
    for(var i=0;i<children.length; i++) {
        if (window.getSelection().containsNode(children[i], true)) {
            var d2 = children[i];
            count += 1;
        }
    }
    if (count == 1)
        if (d2.nameId == TheTextBoxTwo.value)
            remove(d2);
}

function renameClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var TheTextBoxOne = document.getElementById('entermessages');
    var d = document.createElement('div');
    d.nameId = TheTextBoxTwo.value;
    var count = 0;
    d.innerHTML = TheTextBoxTwo.value + ': ' + TheTextBoxOne.value;
    var children = document.getElementById('block2').childNodes;
    for (var i = 0; i < children.length; i++) {
        if (window.getSelection().containsNode(children[i], true)) {
            var d2 = children[i];
            count += 1;

        }
    }
    if (TheTextBoxTwo.value && TheTextBoxOne.value) {
        if (count == 1)
            if (d2.nameId == d.nameId)
                rename(d2, d);
    }
    TheTextBoxOne.value = null;
}

function rename(elem, d) {
    var parentElement = elem.parentNode;
    parentElement.insertBefore(d, elem);
    parentElement.removeChild(elem);
}