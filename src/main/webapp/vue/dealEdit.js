var findOrganisationUrl;
var saveOrganisationUrl;
var saveDealUrl;
var dealId;
var dealType;
var date;
var dateTo;
var contragent;
var contragentId;
var realisationFrom;
var product;
var quantity;
var price;

$(document).ready(function(){
    var modal = document.getElementById('modal-content');

    dealId = GetChildElemById(modal, 'deal_id');
    dealType = GetChildElemById(modal, 'type');
    date = GetChildElemById(modal, 'date');
    dateTo = GetChildElemById(modal, 'date_to');
    contragentId = GetChildElemById(modal, 'contragent_id');
    contragent = GetChildElemById(modal, 'contragent');
    realisationFrom = GetChildElemById(modal, 'realisation');
    product = GetChildElemById(modal, 'product');
    quantity = GetChildElemById(modal, 'quantity');
    price = GetChildElemById(modal, 'price');

    contragent.onclick = function(){
        contragent.select();
    }
    contragent.lock=true;
    contragent.onchange= function(){
        if (!contragent.lock){
            contragentId.value = null;
        }
    }
    contragent.onkeyup = find(
        findOrganisationUrl, contragent,
        GetChildElemById(modal, 'contragent-list'), function(e){
            contragentId.value = e.id;
            contragent.value = e.value;
        }
    );
})
function save(){
    var parameters = [];
    parameters.id = dealId.value;
    parameters.type = dealType.value;
    parameters.date = date.value;
    parameters.date_to = dateTo.value;
    parameters.organisation_id = contragentId.value;
    parameters.organisation = contragent.value;
    parameters.visibility = realisationFrom.value;
    parameters.product_id = product.value;
    parameters.quantity = quantity.value;
    parameters.price = price.value;
    PostApi(saveDealUrl, parameters, function(e){
        console.log(e);
        close();
    })
}
var onCloseAction;
function onClose(action){
    onCloseAction = action;
}
function close(){
    onCloseAction();
}