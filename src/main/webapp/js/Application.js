let coverlet;
let header;
let modalLayer;
let chatLayer;
let navMenu;
let filter;
let content;
let staticField;
const lastPage = 'last-page';
let welcome = '';
let logoutAPI = '';
let currentPage = '';
let sessionLocker;

$(document).ready(function(){

    console.log('Platform ' + navigator.platform);
    if(navigator.platform.includes('Linux')){
        let style = document.createElement('style');
        document.head.appendChild(style);
        style.sheet.insertRule('* {font-size: 10pt;'); // font-family: Helvetica, sans-serif}
    }
    document.body.style.maxWidth = Settings.switchWidth + 'px';
    coverlet = document.getElementById('coverlet');
    header = document.getElementById('header');
    modalLayer = document.getElementById('modal');
    navMenu = document.getElementById('nav-menu');
    filter = document.getElementById('filter');
    content = document.getElementById('content');
    staticField = document.getElementById('static');
    sessionLocker = document.getElementById('sessionLocker');
    openLast();
    window.addEventListener('beforeunload', function (e) {
        closeAllSubscribes();
    });
});
function getLastUrl(){
    return localStorage.getItem(lastPage + ';' + Settings.worker);
}
function getLastAttributes() {
    return localStorage.getItem('attributes;' + Settings.worker);
}
function openLast(){
    let last = getLastUrl();

    if (last){
        document.addEventListener('loading', function(event){
            let a = getLastAttributes();
            let attr;
            if (a){
                attr = JSON.parse(a);
            }
            loadContent(last, attr);
        });
        document.dispatchEvent(new Event('loading'));

    } else {
        loadContent(welcome)
    }
}
function lockSession(cause){
    console.log('clock ' + cause);
    GetChildElemById(sessionLocker, 'reason').innerText = cause;
    sessionLocker.style.display='block';

}

function loadContent(url, args){
    if (url && currentPage !== url) {
        currentPage = url;
        coverlet.style.display='block';
        content.style.display='none';
        //filter.style.display='none';
        if (DEBUG) {
            console.log('[ Application ] Load page ' + url);
        }
        localStorage.setItem(lastPage + ';' + Settings.worker, url);
        let key = 'attributes;' + Settings.worker;
        if (args){
            localStorage.setItem(key, JSON.stringify(args));
        } else {
            localStorage.removeItem(key);
        }
        PostReq(url, args, function (e) {
            unSubscribe();
            $(content).empty();
            $(staticField).empty();
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
                staticField.style.display = 'block';
                $(staticField).html(staticCont);
            } else {
                staticField.style.display = 'none';
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
    console.log(event);
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
            let p = document.location.origin + context + a['redirect'];
            location.replace(p);
        }
    }, function(e){
        console.log(e)
    }, true);
}
let menuHidden = false;
function hideMenu() {
    if (!menuHidden){
        navMenu.style.display = 'none';
        menuHidden = true;
    } else {
        navMenu.style.display = 'block';
        menuHidden = false;
    }
}
let filterHidden = false;
function hideFilter() {
    if (!filterHidden){
        filter.style.display = 'none';
        filterHidden = true;
    } else {
        filter.style.display = 'block';
        filterHidden = false;
    }
}
