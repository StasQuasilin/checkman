var coverlet;
var header;
var filter;
var content;

$(document).ready(function(){
    coverlet = document.getElementById('coverlet');
    header = document.getElementById('header');
    filter = document.getElementById('filter');
    content = document.getElementById('content');
});

function loadContent(url){
    PostReq(url, null, function(e){
        $(content).html(e);
        $(header).html(GetChildElemById(content, 'header-content'));
        $(filter).html(GetChildElemById(content, 'filter-content'));
        document.title = header.innerText;
    }, function(e){
        console.error('[ Application ] Load content error ' + e)
    })
}