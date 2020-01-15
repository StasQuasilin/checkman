<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 15.01.2020
  Time: 16:35
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<jsp:include page="../retail/retailHeader.jsp"/>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/manyProductView.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/productView.css">
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script>
if (typeof list.fields === 'undefined'){
list.fields = {};
}
list.fields.counterparty = '<fmt:message key="deal.organisation"/>';
list.fields.address = '<fmt:message key="address"/>';
list.fields.product = '<fmt:message key="deal.product"/>';
list.fields.price = '<fmt:message key="deal.price"/>';
list.fields.from = '<fmt:message key="deal.from"/>';
list.fields.vehicle = '<fmt:message key="transportation.automobile"/>';
list.fields.driver = '<fmt:message key="transportation.driver"/>';
list.fields.customer = '<fmt:message key="transport.customer"/>';
list.fields.transporter = '<fmt:message key="transportation.transporter"/>';
list.fields.noData='<fmt:message key="no.data"/>';

subscribe('${subscribe}', function(a){
list.handler(a);
});
stopContent = function(){
unSubscribe('${subscribe}');
}
</script>

<div id="container">
<div v-for="(value, idx) in getItems()" class="container-item"
:class="'container-item-' + new Date(value.item.from).getDay()">
<div  style="display: inline-flex">
{{new Date(value.item.from).toLocaleString().substring(0, 10)}}
{{value.item.counterparty.value}}
<product-view :fields="fields" :show="false" :products="value.item.products"></product-view>
</div>
</div>
</div>
</html>
