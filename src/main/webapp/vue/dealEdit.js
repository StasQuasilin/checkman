var findOrganisationUrl;
var saveOrganisationUrl;

$(document).ready(function(){
    var modal = document.getElementById('modal-content');

    GetChildElemById(modal, 'contragent').onkeyup = find(
        findOrganisationUrl,
        GetChildElemById(modal, 'contragent-list')
    );
})