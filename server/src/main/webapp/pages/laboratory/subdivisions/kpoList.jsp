<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<jsp:include page="kpoHeader.jsp"/>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.limit = 14;
    list.api.edit = '${edit}';
    list.sort = function(){
        list.items.sort(function(a, b){
            return new Date(b.item.date) - new Date(a.item.date);
        })
    };
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
         :class="'container-item-' + new Date(value.item.date).getDay()" style="padding: 4pt; display: inline-block"
         :id="value.item.id" v-on:click="edit(value.item.id)">
        <div class="upper-row">
            {{new Date(value.item.date).toLocaleDateString()}}
            {{new Date(value.item.date).toLocaleTimeString().substring(0, 5)}}
            <fmt:message key="vro.part"/>&nbsp;<span>#</span>{{value.item.number}}
        </div>
        <div class="middle-row">
            <fmt:message key="oil.organoleptic"/>
            <b v-if="value.item.organoleptic">
                Відповідає НД
            </b>
            <b v-else>
                Не відповідає НД
            </b>,
            <fmt:message key="oil.color.value"/>:
            <b>
                {{(value.item.color).toLocaleString()}}
            </b>
            ,
            <fmt:message key="sun.acid.value"/>:
            <b>
                {{value.item.acid}}
            </b>
            ,
            <fmt:message key="oil.peroxide"/>:
            <b>
                {{value.item.peroxide}}
            </b>
            ,
            <fmt:message key="oil.soap"/>:
            <b v-if="value.item.soap">
                <fmt:message key="notification.kpo.soap.yes"/>
            </b>
            <b v-else>
                <fmt:message key="notification.kpo.soap.no"/>
            </b>
        </div>


    </div>
</div>
</html>