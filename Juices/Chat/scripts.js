function sendClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var TheTextBoxOne = document.getElementById('entermessages');
    if (TheTextBoxTwo.value && TheTextBoxOne.value) {
        var parentElem = document.body.children[3];
        var d = document.createElement('div');
        d.setAttribute("onclick", 'select(this)');
        d.nameId = TheTextBoxTwo.value;
        d.innerHTML = TheTextBoxTwo.value + ': ' + TheTextBoxOne.value;
        parentElem.appendChild(d);
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

function remove(elem) {
    var parentElement = elem.parentNode;
    parentElement.removeChild(elem);
}

function deleteClick() {
    var TheTextBoxTwo = document.getElementById('entername');
    var children = document.getElementById('block2').childNodes;
    var count = 0;
    for(var i=0;i<children.length; i++) {
        if (children[i].style.color == 'red') {
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
    d.setAttribute("onclick", 'select(this)');
    d.nameId = TheTextBoxTwo.value;
    var count = 0;
    d.innerHTML = TheTextBoxTwo.value + ': ' + TheTextBoxOne.value;
    var children = document.getElementById('block2').childNodes;
    for (var i = 0; i < children.length; i++) {
        if (children[i].style.color == 'red') {
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
//function store(listToSave) {

//    var listAsString = JSON.stringify(listToSave);

//    localStorage.setItem("TODOs taskList",listAsString);

////}

//function restore() {

//    var item = localStorage.getItem("halma.piece.1.row");
//    {
//        var parentElem = document.body.children[3];
//        var d = document.createElement('div');
//        d.nameId = item.nameId;
//        d.innerHTML = item.innerHTML;
//        parentElem.appendChild(d);
//        store(d);
//    }
//    var TheTextBoxTwo = document.getElementById('entername');
//    TheTextBoxTwo = item;
//    //return item && JSON.parse(item);

//}