<html>
<jsp:include page="../retail/retailHeader.jsp"/>

<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/manyProductView.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/productView.css">
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script>
    subscribe('${subscribe}', function(a){
        list.handler(a);
    });
</script>
<div id="container">
    <div v-for="(value, idx) in getItems()" class="container-item"
         :class="'container-item-' + new Date(value.item.from).getDay()">
        <div  style="display: inline-flex">
            {{new Date(value.item.from).toLocaleString().substring(0, 10)}}
            {{value.item.counterparty.value}}
            <product-view :fields="{}" :products="value.item.products"></product-view>
        </div>
    </div>
</div>
</html>                                                                                                                                                                                                                                                                    