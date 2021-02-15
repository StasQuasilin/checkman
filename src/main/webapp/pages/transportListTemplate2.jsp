
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/DataContainer.css?v=${now}">
    <link rel="stylesheet" href="${context}/css/TransportList.css?v=${now}">
    <script src="${context}/vue2/contextMenuView.vue?v=${now}"></script>
    <script src="${context}/vue2/baseList.vue?v=${now}"></script>
    <script src="${context}/vue/transportView.vue?v=${now}"></script>
    <script src="${context}/vue2/transportListBase.vue?v=${now}"></script>
    <c:choose>
        <c:when test="${not empty script}">
            <script src="${context}${script}?v=${now}"></script>
        </c:when>
        <c:otherwise>
            <script src="${context}/vue2/transportList.vue?v=${now}"></script>
        </c:otherwise>
    </c:choose>
    <jsp:include page="transportListInitialise.jsp"/>
    <script>
        transportList.api.show = '${show}';
        transportList.api.edit = '${edit}';
        transportList.api.archive = '${archive}';

        initFields(transportList.labels);
        <c:if test="${not empty edit}">
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
        </c:if>
        <c:if test="${not empty cancel}">
        transportList.menuItems.push({
            title:'<fmt:message key="menu.cancel"/>',
            onClick:function (itemId) {
                loadModal('${cancel}', {id:itemId})
            }
        });
        </c:if>

        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(a){
            transportList.handle(a);
        });
        </c:forEach>
        stopContent = function(){
            <c:forEach items="${subscribe}" var="s">
            unSubscribe('${s}');
            </c:forEach>
        };
    </script>
    <div id="transportList">
        <transition-group name="flip-list" tag="div" class="container" >
            <transport-view v-for="(value, key) in getItems()" :key="value.id" :id="value.id" :f="getF()"
                            v-on:click.native="show(value.id)" v-on:click.right.native="showMenu(value)"
                            :item="value" :titles="labels" ></transport-view>
        </transition-group>
        <context-menu ref="contextMenu" :items="menuItems" :menu="menu"></context-menu>
    </div>
</html>