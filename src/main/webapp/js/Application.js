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
        $(content).html(e);
        $(header).html(GetChildElemById(content, 'header-content'));
        $(header).append(GetChildElemById(content, 'container-header'));
        $(filter).html(GetChildElemById(content, 'filter-content'));
        document.title = header.innerText;
    }, function(e){
        console.error('[ Application ] Load content error ' + e)
    })
}
function loadModal(url){
    console.log('[ Application ] Load modal ' + url);
    PostReq(url, null, function(m){
        showModal(m);
    }, function (e) {
        console.error('Error ' + e);
    })
}
function showModal(modal){
    $(modalLayer).html(modal);
    modalLayer.style.display='block';
}