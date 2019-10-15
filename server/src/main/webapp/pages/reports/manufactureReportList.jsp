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
    <fmt:message key="button.add"/>
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
<div id="static-content">
  <script>
    var report= new Vue({
      el:'#report',
      data:{
        api:{},
        period:false,
        report:{
          date:new Date().toISOString().substring(0, 10),
          turn:-1,
          product:-1,
          storage:-1
        },
        turns:[],
        products:[],
        storages:[]
      },
      methods:{
        build:function(){
          if (this.api.builder){
            PostApi(this.api.builder, this.report, function(a){
              console.log(a);
            })
          }
        },
        pickDate:function(){
          const self = this;
          datepicker.show(function(d){
            self.report.date = d;
          }, this.report.date)
        }
      }
    });
    report.api.builder = '${builder}';
    <c:forEach items="${turns}" var="turn">
    report.turns.push({
      id:${turn.id},
      name:'<fmt:message key="turn"/> #${turn.number}',
      number:${turn.number}
    });
    </c:forEach>
    <c:forEach items="${products}" var="product">
    report.products.push({
      id:${product.id},
      name:'${product.name}'
    });
    </c:forEach>
    <c:forEach items="${storages}" var="storage">
    report.storages.push({
      id:${storage.id},
      name:'${storage.name}'
    });
    </c:forEach>
  </script>
  <table id="report">
    <tr>
      <td v-on:click="period = !period">
        <label for="date">
          <fmt:message key="date"/>
        </label>
      </td>
      <td>
        <span>
          <input id="date" v-model="new Date(report.date).toLocaleString().substring(0, 10)"
                 style="width: 7em" readonly autocomplete="off" v-on:click="pickDate()">
        </span>
        <%--<span v-if="!period" v-on:click="period = true">--%>
          <%--+--%>
        <%--</span>--%>
      </td>
    </tr>
    <tr v-if="period">
      <td>
        <label for="to">
          <fmt:message key="date"/>
        </label>
      </td>
      <td>
        <input id="to"style="width: 7em">
        <span v-on:click="period = false">
          -
        </span>
      </td>
    </tr>
    <tr>
      <td>
        <label for="turn">
          <fmt:message key="turn"/>
        </label>
      </td>
      <td>
        <select id="turn" style="width: 100%" v-model="report.turn">
          <option value="-1"><fmt:message key="none"/></option>
          <option v-for="turn in turns" :value="turn.number">
            {{turn.name}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="product">
          <fmt:message key="deal.product"/>
        </label>
      </td>
      <td>
        <select id="product" style="width: 100%" v-model="report.product">
          <option value="-1"><fmt:message key="none"/></option>
          <option v-for="product in products" :value="product.id">
            {{product.name}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="storage">
          <fmt:message key="storage"/>
        </label>
      </td>
      <td>
        <select id="storage" style="width: 100%" v-model="report.storage">
          <option value="-1"><fmt:message key="none"/></option>
          <option v-for="storage in storages" :value="storage.id">
            {{storage.name}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button v-on:click="build()">
          <fmt:message key="report.build"/>
        </button>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button>
          <fmt:message key="button.clear"/>
        </button>
      </td>
    </tr>
  </table>
</div>
<div id="container">

  <transition-group  name="flip-list" tag="div" class="container">
    <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
         class="container-item" :class="'container-item-' + new Date(value.item.turn.date).getDay()"
         v-on:click="show(value.item.id)" v-on:click.right="contextMenu(value.item)">
      <div class="upper-row">
        <fmt:message key="turn"/> <span>#</span>{{value.item.turn.number}}
        {{new Date(value.item.turn.date).toLocaleDateString()}}
        {{new Date(value.item.turn.date).toLocaleTimeString().substring(0, 5)}}
      </div>
    </div>
  </transition-group>
  <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
    <div ref="contextMenu" :style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
      <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.edit"/></div>
      <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.copy"/></div>
    </div>
  </div>
</div>
</html>
