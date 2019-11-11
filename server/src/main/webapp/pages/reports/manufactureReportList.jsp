<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 06.11.2019
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<div id="container-header" class="container-header">
  <link rel="stylesheet" href="${context}/css/drop-menu.css">
  <button onclick="loadModal('${edit}')">
    <fmt:message key="button.add"/>
  </button>
</div>
<script>
  list.api.edit = '${show}';
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
  <div v-for="item in items" v-on:click="edit(item.item.id)">
    <a>
      {{new Date(item.item.date).toLocaleDateString()}}
      <fmt:message key="turn"/> <span>#</span>{{item.item.turn.number}}
    </a>
  </div>
</div>
</html>
