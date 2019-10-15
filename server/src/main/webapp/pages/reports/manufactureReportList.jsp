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
</div>
<script>
  var reportView = new Vue({
    el:'#container',
    data:{
      report:{}
    }
  })
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
              reportView.report = a;
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
  <table v-if="report.date">
    <tr>
      <td>
        <b>
          {{new Date(report.date).toLocaleDateString().substring(0, 10)}}
        </b>
      </td>
    </tr>
    <template v-if="report.loads">
      <tr>
        <td>
          <fmt:message key="deal.loads.done"/>
        </td>
      </tr>
      <template v-for="(load, key) in report.loads">
        <tr>
          <td>
            {{key}} {{load.length}} {{load.weight}}
          </td>
        </tr>
        <tr v-for="l in load.values">
          <td>
            {{l}}
          </td>
        </tr>
      </template>
    </template>
  </table>
</div>
</html>
