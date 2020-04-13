<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">

<script>
  var reportView = new Vue({
    el:'#container',
    data:{
      roundReport:{}
    }
  })
</script>
<div id="static-content">
  <script>
    var roundReport= new Vue({
      el:'#roundReport',
      data:{
        api:{},
        period:false,
        roundReport:{
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
            PostApi(this.api.builder, this.roundReport, function(a){
              reportView.roundReport = a;
            })
          }
        },
        pickDate:function(){
          const self = this;
          datepicker.show(function(d){
            self.roundReport.date = d;
          }, this.roundReport.date)
        }
      }
    });
    roundReport.api.builder = '${builder}';
    <c:forEach items="${turns}" var="turn">
    roundReport.turns.push({
      id:${turn.id},
      name:'<fmt:message key="turn"/> #${turn.number}',
      number:${turn.number}
    });
    </c:forEach>
    <c:forEach items="${products}" var="product">
    roundReport.products.push({
      id:${product.id},
      name:'${product.name}'
    });
    </c:forEach>
    <c:forEach items="${storages}" var="storage">
    roundReport.storages.push({
      id:${storage.id},
      name:'${storage.name}'
    });
    </c:forEach>
  </script>
  <table id="roundReport">
    <tr>
      <td v-on:click="period = !period">
        <label for="date">
          <fmt:message key="date"/>
        </label>
      </td>
      <td>
        <span>
          <input id="date" v-model="new Date(roundReport.date).toLocaleString().substring(0, 10)"
                 style="width: 7em" readonly autocomplete="off" v-on:click="pickDate()">
        </span>
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
        <select id="turn" style="width: 100%" v-model="roundReport.turn">
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
        <select id="product" style="width: 100%" v-model="roundReport.product">
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
        <select id="storage" style="width: 100%" v-model="roundReport.storage">
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
          <fmt:message key="roundReport.build"/>
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
  <table v-if="roundReport.date">
    <tr>
      <td>
        <b>
          {{new Date(roundReport.date).toLocaleDateString().substring(0, 10)}}
        </b>
        <b v-if="roundReport.turn">
          <fmt:message key="turn"/> {{roundReport.turn}}
        </b>
      </td>
    </tr>
    <template v-if="roundReport.loads">
      <tr>
        <td style="border-bottom: solid black 1pt">
          <fmt:message key="deal.loads.done"/>
        </td>
      </tr>
      <template v-for="(load, key) in roundReport.loads">
        <tr>
          <td>
            <div style="padding-left: 18pt; font-weight: bold">
              {{key}}, {{load.values.length}}, {{(load.weight).toLocaleString()}}
            </div>
            <div style="padding-left: 24pt">
              <table>
                <tr v-for="(l, idx) in load.values">
                  <td>
                    {{idx + 1}}.{{l.organisation.value}} ({{l.shipper}})
                  </td>
                  <td>
                    {{l.driver.person.value}}
                  </td>
                  <td v-if="l.weight.netto">
                    {{(l.weight.netto).toLocaleString()}}
                  </td>
                  <td v-else>
                    0
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
      </template>
    </template>
  </table>
</div>
</html>
