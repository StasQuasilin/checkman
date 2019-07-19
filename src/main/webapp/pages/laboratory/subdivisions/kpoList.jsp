<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" style="display: inline">
    <link rel="stylesheet" href="${context}/css/drop-menu.css">
    <div class="drop-menu">
        <a class="drop-btn">
            <fmt:message key="analyses"/> &#9660;
        </a>
        <div class="drop-menu-content">
            <div class="drop-menu-item">
                <span onclick="loadModal('${edit}')">
                    <fmt:message key="vro.part"/>
                </span>
            </div>
        </div>
    </div>
</div>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.limit = 14;
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        list.handler(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(a){
            unSubscribe('${s}');
        });
        </c:forEach>
    }
</script>
<div id="container">
    <div v-for="(value, key) in items" class="container-item"
         :class="'container-item-' + new Date(value.item.date).getDay()" style="padding: 4pt"
         :id="value.item.id" onclick="editableModal('${edit}')">
        {{new Date(value.item.date).toLocaleDateString()}}
        {{new Date(value.item.date).toLocaleTimeString().substring(0, 5)}}
        <fmt:message key="vro.part"/>&nbsp;<span>#</span>{{value.item.number}}
        <fmt:message key="oil.organoleptic"/>:
        <span v-if="value.item.organoleptic">
            +
        </span>
        <span v-else>
            -
        </span>,
        <fmt:message key="oil.color.value"/>:
        {{value.item.color}},
        <fmt:message key="sun.acid.value"/>:
        {{value.item.acid}},
        <fmt:message key="oil.peroxide"/>:
        {{value.item.peroxide}},
        <fmt:message key="oil.soap"/>:
        <span v-if="value.item.soap">
           -
        </span>
        <span v-else>
            +
        </span>
    </div>
</div>
</html>