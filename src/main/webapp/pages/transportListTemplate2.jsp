<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<link rel="stylesheet" href="${context}/css/DataContainer.css?v=${now}">
<link rel="stylesheet" href="${context}/css/TransportList.css?v=${now}">
<script src="${context}/vue2/contextMenuView.vue"></script>
<script src="${context}/vue2/baseList.vue"></script>
<script src="${context}/vue/transportView.vue"></script>
<script src="${context}/vue2/transportList.vue"></script>
<script>
    transportList.api.show = '${show}';
    transportList.api.edit = '${edit}';
    transportList.api.archive = '${archive}';
    transportList.labels.counterparty = '<fmt:message key="deal.organisation"/>';
    transportList.labels.address = '<fmt:message key="load.address"/>';
    transportList.labels.region = '<fmt:message key="address.region.short"/>';
    transportList.labels.district = '<fmt:message key="address.district.short"/>';
    transportList.labels.buy = '<fmt:message key="_buy"/>';
    transportList.labels.sell = '<fmt:message key="_sell"/>';
    transportList.labels.price = '<fmt:message key="deal.price"/>';
    transportList.labels.icon = {
        buy:'${context}/images/upload.svg',
        sell:'${context}/images/download.svg'
    };
    transportList.labels.from = '<fmt:message key="deal.from"/>';
    transportList.labels.timeIn = '<fmt:message key="transportation.time.in"/>';
    transportList.labels.timeOut = '<fmt:message key="transportation.time.out"/>';
    transportList.labels.driver = '<fmt:message key="transportation.driver"/>';
    transportList.labels.truck = '<fmt:message key="transportation.automobile"/>';
    transportList.labels.phoneIcon = '${context}/images/phone.svg';
    transportList.labels.noSpecified = '<fmt:message key="not.specified"/>';
    transportList.labels.customer = '<fmt:message key="transport.customer"/>';
    transportList.labels.transporter = '<fmt:message key="transportation.transporter"/>';
    transportList.labels.szpt='<fmt:message key="szpt"/>';
    transportList.labels.cont='<fmt:message key="deal.organisation"/>';
    transportList.labels.weight = '<fmt:message key="weight"/>';
    transportList.labels.analyses = '<fmt:message key="analyses"/>';
    transportList.labels.sun = {
        humidity1:'<fmt:message key="sun.humidity.1.short"/>',
        humidity2:'<fmt:message key="sun.humidity.2.short"/>',
        soreness:'<fmt:message key="sun.soreness"/>',
        impurity:'<fmt:message key="sun.oil.impurity"/>',
        oiliness:'<fmt:message key="sun.oiliness"/>',
        contamination:'<fmt:message key="sun.contaminate"/>'
    };
    transportList.labels.oil = {
        acid:'<fmt:message key="sun.acid.value"/>',
        peroxide:'<fmt:message key="oil.peroxide"/>',
        phosphorus:'<fmt:message key="oil.phosphorus.mass.fraction"/>'
    };
    transportList.labels.meal = {
        humidity:'<fmt:message key="sun.humidity"/>',
        protein:'<fmt:message key="cake.protein"/>',
        cellulose:'<fmt:message key="cake.cellulose"/>',
        oiliness:'<fmt:message key="sun.oiliness"/>'
    };
    transportList.labels.missing = '<fmt:message key="missing"/>';
    transportList.menuItems.push({
        title:'<fmt:message key="edit"/>',
        onClick:function (itemId) {
            loadModal('${edit}', {id: itemId});
        }
    });
    transportList.menuItems.push({
        title:'<fmt:message key="menu.copy"/>',
        onClick:function (itemId) {
            loadModal('${edit}', {copy: itemId});
        }
    });
    transportList.menuItems.push({
        title:'<fmt:message key="menu.cancel"/>',
        onClick:function (itemId) {
            loadModal('${cancel}', {id:itemId})
        }
    });

    <c:forEach items="${subscribe}" var="s">
    // s++;
    subscribe('${s}', function(a){
        transportList.handle(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        unSubscribe('${s}');
        </c:forEach>
    };
    // if (s > 2) {
    //     list.loading = true;
    // }
</script>
<style>
    .label{
        font-size: 8pt;
    }
</style>
    <div id="transportList">
        <transition-group name="flip-list" tag="div" class="container" >
                <transport-view v-for="(value, key) in getItems()" :key="value.id" :id="value.id" :f="f"
                    v-on:click.native="show(value.id)" v-on:click.right.native="showMenu(value)"
                    :item="value" :titles="labels" ></transport-view>
        </transition-group>
        <context-menu ref="contextMenu" :items="menuItems" :menu="menu"></context-menu>
    </div>
</html>