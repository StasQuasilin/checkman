var coverlet;
var header;
var modalLayer;
var filter;
var content;
const last_url = 'last-url';

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

    console.log('[ Application ] Load page ' + url);
    localStorage.setItem(last_url, url);
    PostReq(url, null, function(e){
        try {
            stopContent();
        } catch (e){}

        $(content).html(e);
        $(header).html(GetChildElemById(content, 'header-content'));
        document.title = header.innerText;
        $(header).append(GetChildElemById(content, 'container-header'));
        $(filter).html(GetChildElemById(content, 'filter-content'));

    }, function(e){
        console.error('[ Application ] Load content error ' + e)
    })
}
function loadModal(url, parameters, onSave){
    console.log('[ Application ] Load modal ' + url);
    PostReq(url, parameters, function(m){
        addModal(m, onSave);

    }, function (e) {
        console.error('Error ' + e);
    })
}
var modals = 0;
function addModal(modal, onSave){
    var div = document.createElement('div');
    $(div).html(modal);
    $(modalLayer).append(div);
    modalLayer.style.display='block';
    modals++;

    addOnSaveEvent(onSave);
    addOnCloseEvent(function () {
        $(div).remove();
        modals--;
        if(modals == 0){
            modalLayer.style.display='none';
        }
    });
}
