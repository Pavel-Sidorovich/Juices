TheTextBox3;
// Флаги для определения браузеров
var uagent = navigator.userAgent.toLowerCase();
var is_safari = ((uagent.indexOf('safari') != -1) || (navigator.vendor == "Apple Computer, Inc."));
var is_ie = ((uagent.indexOf('msie') != -1) && (!is_opera) && (!is_safari) && (!is_webtv));
var is_ie4 = ((is_ie) && (uagent.indexOf("msie 4.") != -1));
var is_moz = (navigator.product == 'Gecko');
var is_ns = ((uagent.indexOf('compatible') == -1) && (uagent.indexOf('mozilla') != -1) && (!is_opera) && (!is_webtv) && (!is_safari));
var is_ns4 = ((is_ns) && (parseInt(navigator.appVersion) == 4));
var is_opera = (uagent.indexOf('opera') != -1);
var is_kon = (uagent.indexOf('konqueror') != -1);
var is_webtv = (uagent.indexOf('webtv') != -1);
var is_win = ((uagent.indexOf("win") != -1) || (uagent.indexOf("16bit") != -1));
var is_mac = ((uagent.indexOf("mac") != -1) || (navigator.vendor == "Apple Computer, Inc."));
var ua_vers = parseInt(navigator.appVersion);

function SendClick() {
        var TheTextBox = document.getElementById('entermessages');
        if (TheTextBox3.value && TheTextBox.value) {
            var TheTextBox2 = document.getElementById('messages');
            if (!TheTextBox2.value)
                TheTextBox2.value = TheTextBox3.value + ': ' + TheTextBox.value;
            else 
                TheTextBox2.value = TheTextBox2.value + '\n' + TheTextBox3.value + ': ' + TheTextBox.value;
        }
        TheTextBox.value = null;
        TheTextBox3 = null;
}
function SaveClick() {
    TheTextBox3 = document.getElementById('entername');
}
function Selected() {
    getSelection(document.getElementById('messages'));
}
function getSelection(textarea)
{
    var selection = null;
    if ((ua_vers >= 4) && is_ie && is_win) {
        if (textarea.isTextEdit) {
            textarea.focus();
            var sel = document.selection;
            var rng = sel.createRange();
            rng.collapse;
            if((sel.type == "Text" || sel.type == "None") && rng != null)
                selection = rng.text;
        }
        } else if (typeof(textarea.selectionEnd) != "undefined" ) { 
            selection = (textarea.value).substring(textarea.selectionStart, textarea.selectionEnd);
        }
    return selection;
}
function Delete() {
    var sel = getSelection(document.getElementById('messages'));
    if (sel) {
        var TheTextBox4 = document.getElementById('messages');
        TheTextBox4.value = str_replace(sel + '\n', '', TheTextBox4.value);
    }
}
function str_replace(search, replace, subject) {
    return subject.split(search).join(replace);
}