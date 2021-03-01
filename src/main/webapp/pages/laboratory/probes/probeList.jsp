<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css?v=${now}">
<c:if test="${edit ne null}">
    <div id="container-header" style="display: inline">
        <c:forEach items="${types}" var="a">
            <button ondblclick="" onclick="loadModal('${edit}?type=${a}')">
                <fmt:message key="${a}"/>
            </button>
        </c:forEach>
    </div>
</c:if>
<script src="${context}/vue2/baseList.vue?v=${now}"></script>
<script src="${context}/vue2/probeView.vue?v=${now}"></script>
<script src="${context}/vue2/probeList.vue?v=${now}"></script>
<script>
    probeList.fields ={
        turn:'<fmt:message key="turn"/>',
        sun:{
            title:'<fmt:message key="sun"/>',
            humidity:'<fmt:message key="sun.humidity"/>',
            soreness:'<fmt:message key="sun.soreness"/>',
            oiliness:'<fmt:message key="sun.oiliness"/>',
            impurity:'<fmt:message key="sun.oil.impurity"/>',
            acid:'<fmt:message key="sun.acid.value"/>'
        },
        oil:{
            title:'<fmt:message key="oil"/>',
            organoleptic:'<fmt:message key="oil.organoleptic"/>',
            organolepticYes:'<fmt:message key="oil.organoleptic.match"/>',
            organolepticNo:'<fmt:message key="oil.organoleptic.doesn't.match"/>',
            color:'<fmt:message key="oil.color.value"/>',
            acid:'<fmt:message key="sun.acid.value"/>',
            peroxide:'<fmt:message key="oil.peroxide"/>',
            phosphorus:'<fmt:message key="oil.phosphorus"/>',
            wax:'<fmt:message key="oil.wax"/>',
            soap:'<fmt:message key="oil.soap"/>',
            soapYes:'',
            soapNo:''
        },
        organisation:'<fmt:message key="deal.organisation"/>',
        manager:'<fmt:message key="deal.manager"/>'

    };
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        probeList.handle(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(){
            unSubscribe('${s}');
        });
        </c:forEach>
    }
</script>
<div id="probeList" class="container">
    <probe-view v-for="item in filteredItems()" :item="item" :fields="fields" :key="item.id"></probe-view>
</div>
</html>