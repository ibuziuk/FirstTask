var number = -1;
var del = false;
var change = false;

var uniqueId = function() {
    var date = Date.now();
    var random = Math.random() * Math.random();

    return Math.floor(date * random).toString();
};

function run() {
    var appContainer = document.getElementsByClassName('messaging')[0];

    appContainer.addEventListener('click', delegateEvent);
}

function processEnter(e) {
    if (e.keyCode == '13') {
       e.preventDefault();
       getUserName();
   }
}

function delegateEvent(evtObj) {
    if(evtObj.type == 'click' && evtObj.target.classList.contains('btn-send')) {
        buttonSendClicked(evtObj);
    }
    if(evtObj.type == 'click' && evtObj.target.classList.contains('btn-change')) {
        buttonChangeClicked(evtObj);
    }
    if(evtObj.type == 'click' && evtObj.target.classList.contains('btn-delete')) {
        buttonDeleteClicked(evtObj);
    }
    if(evtObj.type == 'click' && evtObj.target.classList.contains('message')) {
        messageClicked(evtObj.target);
    }
}

function getUserName() {
    var user = document.getElementById('user');
    var userName = document.getElementById('userName');
    var loginButton = document.getElementById('loginButton');

    user.innerHTML = userName.value;
    userName.value = ""

    if (loginButton.value == "Log out") {
        loginButton.value = "Log in";
    } else {
        loginButton.value = "Log out";
    }
}

function buttonSendClicked() {
    var message = document.getElementById('newMessage');
    var user = document.getElementById('user');

    user.value = document.getElementById('user').innerHTML;
    if (user.value.localeCompare("") == 0) {
        alert("Input your login!");
        return;
    }
    addMessage(message.value, user.value);
    message.value = '';
}

function addMessage(message, user) {
    if (!message) {
        return;
    }

    var item = createDiv(message, user);
    var items = document.getElementsByClassName('messages')[0];

    items.appendChild(item);
}

function buttonChangeClicked() {
    change = true;
}

function buttonDeleteClicked() {
    del = true;
}

function messageClicked(item) {
    if (del) {
        deleteMessage(item);
        del = false;
        return;
    }

    if (change) {
        var b = item.childNodes[2].textContent;
        document.forms['text'].elements['msg'].value = b;
        deletemessage(item);
        change = false;
        return;
    }
}

function deleteMessage(item) {
    var items = document.getElementsByClassName('messages')[0];

    for(var i = 0; i < items.childNodes.length; i++) {
        if(items.childNodes[i] == item) {
            break;
        }
    }
    items.removeChild(items.childNodes[i]);
}

function deletemessage(item) {
    var items = document.getElementsByClassName('messages')[0];
    var element;
    for(var i = 0; i < items.childNodes.length; i++)
        if(items.childNodes[i] === item) {
            element = i;
            number = i;
            break;
        }
    items.childNodes[element].removeChild(items.childNodes[element].childNodes[2]);
}

function createDiv(text, user)
{
    var message = document.createTextNode(text);
    var newDiv = document.createElement('div');
    newDiv.classList.add('message');
    newDiv.appendChild(document.createTextNode(user+': '));
    newDiv.appendChild(message);
    return newDiv;
}