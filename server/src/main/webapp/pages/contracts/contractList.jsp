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
    list.api.edit = '${edit}';
    list.api.show = '${show}';
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
  list.edit = function(id){
    loadModal(list.api.edit, {id:id});
  };
  list.show = function(id){
    loadModal(list.api.show, {id: id});
  };

  subscribe('${subscribe}', function(a){
  list.handler(a);
  });
  stopContent = function(){
  unSubscribe('${subscribe}');
  }
  </script>

  <div id="container">
    <div v-for="(value, idx) in getItems()" class="container-item"
    :class="'container-item-' + new Date(value.item.date).getDay()"
            v-on:click="show(value.item.id)" v-on:click.right="contextMenu(value.item)">
      <div style="display: inline-flex; border-right: solid black 1pt;">
        <div style="margin: auto; padding: 0 2pt;">
          <div v-if="value.item.number">
            №{{value.item.number}}
          </div>
          <div>
            {{new Date(value.item.date).toLocaleString().substring(0, 10)}}
          </div>
          <div v-if="value.item.date !== value.item.to">
            {{new Date(value.item.to).toLocaleString().substring(0, 10)}}
          </div>
        </div>
      </div>
      <div>
        <div class="upper-row">
          {{value.item.counterparty.value}}
        <span v-if="value.item.address">
          {{value.item.address.city}}
          {{value.item.address.street}}
          {{value.item.address.build}}
        </span>
        </div>
        <div class="middle-row">
          <div v-for="product in value.item.products">
            {{product.product.name}}
          </div>
        </div>
      </div>
    </div>
  </div>
</html>
