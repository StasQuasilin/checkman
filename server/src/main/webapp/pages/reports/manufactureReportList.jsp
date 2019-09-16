<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<div id="container-header" class="container-header">
  <link rel="stylesheet" href="${context}/css/drop-menu.css">
  <button onclick="loadModal('${edit}')">
    <fmt:message key="edit"/>
  </button>
  <%--<div class="drop-menu">--%>
    <%--<a class="drop-btn"><fmt:message key="document.print"/>&nbsp;&#9660;</a>--%>
    <%--<div class="drop-menu-content">--%>
      <%--<div class="drop-menu-item" onclick="loadModal('${print}')">--%>
        <%--<fmt:message key="print.daily.report"/>--%>
      <%--</div>--%>
      <%--<div class="drop-menu-item" onclick="loadModal('${print}')">--%>
        <%--<fmt:message key="print.monthly.report"/>--%>
      <%--</div>--%>
    <%--</div>--%>
  <%--</div>--%>
</div>
<script src="${context}/vue/dataList.vue"></script>
<script>
  list.api.edit = '${edit}';
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
  <transition-group  name="flip-list" tag="div" class="container">
    <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
         class="container-item" :class="'container-item-' + new Date(value.item.date).getDay()" v-on:click="edit(value.item.id)">
      {{value}}
    </div>
  </transition-group>
</div>
</html>
