var coverlet;
var header;
var modalLayer;
var chatLayer;
var filter;
var content;
var staticfield;
const lastPage = 'last-page';
var welcome = '';
var logoutAPI = '';
var currentPage = '';
var sessionLocker;

$(document).ready(function(){
    if(navigator.platform.includes('Linux')){
        console.log('Is Linux');
        let style = document.createElement('style');
        document.head.appendChild(style);
        style.sheet.insertRule('* {font-size: 10pt;'); // font-family: Helvetica, sans-serif}
    }
    document.body.style.maxWidth = Settings.switchWidth + 'px';
    coverlet = document.getElementById('coverlet');
    header = document.getElementById('header');
    modalLayer = document.getElementById('modal');
    filter = document.getElementById('filter');
    content = document.getElementById('content');
    staticfield = document.getElementById('static');
    sessionLocker = document.getElementById('sessionLocker');

    let last = localStorage.getItem(lastPage + ';' + Settings.worker);

    if (last){
        document.addEventListener('loading', function(event){
            loadContent(last);
        });
        document.dispatchEvent(new Event('loading'));

    } else {
        loadContent(welcome)
    }
});

function lockSession(cause){
    console.log('clock ' + cause);
    GetChildElemById(sessionLocker, 'reason').innerText = cause;
    sessionLocker.style.display='block';

}

function loadContent(url){
    if (url && currentPage !== url) {
        currentPage = url;
        coverlet.style.display='block';
        content.style.display='none';
        //filter.style.display='none';
        if (DEBUG) {
            console.log('[ Application ] Load page ' + url);
        }
        localStorage.setItem(lastPage + ';' + Settings.worker, url);
        PostReq(url, null, function (e) {
            if (typeof stopContent === 'function'){
                stopContent();
            }
            $(content).empty();
            $(staticfield).empty();
            $(filter).empty();
            $(content).html(e);
            $(header).html(GetChildElemById(content, 'header-content'));
            $(header).append(GetChildElemById(content, 'container-header'));
            let filterContent = GetChildElemById(content, 'filter-content');
            if (filterContent){
                $(filter).html(filterContent);
            }
            let staticCont = GetChildElemById(content, 'static-content');
            if (staticCont){
                staticfield.style.display = 'block';
                $(staticfield).html(staticCont);
            } else {
                staticfield.style.display = 'none';
            }


            content.style.display='block';
            coverlet.style.display='none';
            filter.style.display='block';
        }, function (e) {
            console.error('[ Application ] Load content error ' + e);
            coverlet.style.display='none';
            content.style.display='block';
            filter.style.display='block';
        }, true);
    }
}

function editableModal(url){
    let id = event.toElement.getAttribute("id");
    let copy = event.toElement.getAttribute('copy');

    let parameters = {};

    if (id){
        parameters.id = id;
    }
    if (copy){
        parameters.copy = copy;
    }

    loadModal(url, parameters)
}
function loadModal(url, parameters, onSave){
    if (DEBUG){
        console.log('[ Application ] Load modal ' + url);
        console.log(parameters);
    }
    coverlet.style.display='block';
    PostReq(url, parameters, function(m){
        addModal(m, onSave);
        coverlet.style.display='none';
    }, function (e) {
        console.error('Error ' + e);
        //modalLayer.style.display='none';
        coverlet.style.display='none';
    }, function(e){
        console.log(e);
        coverlet.style.display='none';
    }, true)
}
let modals = [];
function addModal(modal, onSave){
    modalLayer.style.display='block';
    let div = document.createElement('div');
    div.style.visibility='hidden';
    $(div).html(modal);
    $(modalLayer).append(div);
    modals.push(div);
    if (typeof addOnSaveEvent !== 'undefined') {
        addOnSaveEvent(onSave);
    }
    addOnCloseEvent(function () {
        let d = modals[modals.length - 1];
        modals.splice(modals.length - 1, 1);
        $(d).remove();
        if(modals.length === 0){
            modalLayer.style.display='none';
        }
    });
    div.style.visibility='visible';
}
function logout(){
    PostApi(logoutAPI, null, function (a) {
        if (a.status === 'success'){
            var p = document.location.origin + context + a['redirect'];
            location.replace(p);
        }
    }, function(e){
        console.log(e)
    }, true);
}
function showChat(){

}
function closeChat(){

}
