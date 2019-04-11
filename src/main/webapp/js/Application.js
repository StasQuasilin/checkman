var coverlet;
var header;
var modalLayer;
var filter;
var content;
const last_url = 'last-url';
var logoutAPI = '';

$(document).ready(function(){
    coverlet = document.getElementById('coverlet');
    header = document.getElementById('header');
    modalLayer = document.getElementById('modal');
    filter = document.getElementById('filter');
    content = document.getElementById('content');

    var last = localStorage.getItem(last_url);
    if (last){
        loadContent(last);
    }
});

function loadContent(url){
    if (url) {
        console.log('[ Application ] Load page ' + url);
        localStorage.setItem(last_url, url);
        PostReq(url, null, function (e) {
            try {
                //stopContent();
            } catch (e) {
                console.log(e)
            } finally {
                $(content).html(e);
                $(header).html(GetChildElemById(content, 'header-content'));
                //document.title = header.innerText;
                $(header).append(GetChildElemById(content, 'container-header'));
                $(filter).html(GetChildElemById(content, 'filter-content'));
            }
        }, function (e) {
            console.error('[ Application ] Load content error ' + e)
        }, true)
    }
}

function editableModal(url){
    var id = event.toElement.getAttribute("id");
    var copy = event.toElement.getAttribute('copy');
    var parameters = {};

    if (id){
        parameters.id = id;
    }
    if (copy){
        parameters.copy = copy;
    }

    loadModal(url, parameters)
}
function loadModal(url, parameters, onSave){
    console.log('[ Application ] Load modal ' + url);
    PostReq(url, parameters, function(m){
        addModal(m, onSave);
    }, function (e) {
        console.error('Error ' + e);
        modalLayer.style.display='none';
    }, function(e){
        console.log(e);
    }, true)
}
var modals = [];
function addModal(modal, onSave){
    modalLayer.style.display='block';
    var div = document.createElement('div');
    div.style.visibility='hidden';
    $(div).html(modal);
    $(modalLayer).append(div);
    modals.push(div);
    addOnSaveEvent(onSave);
    addOnCloseEvent(function () {
        var d = modals[modals.length - 1];
        modals.splice(modals.length - 1, 1);
        console.log('Close modal \'' + d.getElementsByClassName('modal-header')[0].innerText + '\'');
        $(d).remove();
        if(modals.length == 0){
            modalLayer.style.display='none';
        }
    });
    div.style.visibility='visible';
}
function logout(){
    PostApi(logoutAPI, null, function (a) {
        if (a.status == 'success'){
            location.href=context + a['redirect'];
        }
    })
}
