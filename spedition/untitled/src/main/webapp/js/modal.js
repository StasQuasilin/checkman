let modalLayer;
let modalTitle;
let modalContent;

$(document).ready(function(){
    modalLayer = document.getElementById('modalLayer');
    modalTitle = document.getElementById('modalTitle');
    modalContent = document.getElementById('modalContent');
    closeModal();
});
function closeModal(){
    modalLayer.style.display = 'none';
}
function showModal(content) {
    modalContent.innerHTML = content;
    modalLayer.style.display = 'block';
}
function loadModal(url, params) {
    PostReq(url, params, function (ans) {
        showModal(ans)
    })
}