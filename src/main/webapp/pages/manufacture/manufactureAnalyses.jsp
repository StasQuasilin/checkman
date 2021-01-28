<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 05.12.2019
  Time: 15:35
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>

  table{
    border-collapse: collapse;
  }
  .analyse-header{
    font-style: italic;
    font-size: 18pt;
    font-weight: bold;
    text-align: center;
  }
  .analyse-body *{
    user-select: text;
  }
</style>
<script>
  var analyse = new Vue({
    el:'#analyse',
    data:{
      api:{},
      from:'',
      to:'',
      tables:[]
    },
    methods:{
      prettyNumber:function(num){
        if (num){
          return (Math.round(num * 100) / 100).toLocaleString();
        }
      },
      middle:function(arr, field){
        let result = 0;
        let amount = 0;
        arr.forEach(function(item){
          let t = item[field];
          if (t > 0){
            result+=t;
            amount++;
          }
        });
        if (result > 0){
          return this.prettyNumber(result / amount);
        }

      },
      update:function(month, onUpdate){
        let date = new Date(month).toISOString().substring(0, 10);
        const self = this;

        PostApi(this.api.update, {month:date}, function(a){
          if (typeof onUpdate === 'function'){
            onUpdate();
          }
          self.from = a.from;
          self.to = a.to;

          let table1 = [];
          let table2 = [];
          let table3 = [];
          let table4 = [];
          self.tables = [
              table1,
              table2,
              table3,
              table4
          ];

          a.values.forEach(function(item){
            let date = new Date(item.date);
            let turn = item.turn;
            let minus = Math.floor((date.getDate()) / 4) * 4;
            let idx = date.getDate() - minus;
            idx += (2 - turn);
            if (idx == 4){
              idx = 0;
            }

            let table = self.tables[idx];
            table.push(item);
          });
        });
      }
    }
  });
  analyse.api.update = '${update}';
  analyse.update(filter_view.month);
</script>
  <div id="analyse" style="width: 100%;">
    <div class="analyse-header">
      <div>
        <fmt:message key="manufacture.analyses.header"/>
      </div>
      <div>
        <fmt:message key="date.from"/> {{new Date(from).toLocaleDateString()}}
        <fmt:message key="date.to"/> {{new Date(to).toLocaleDateString()}}
      </div>
    </div>
    <div class="analyse-body">
      <div v-for="table in tables" style="padding-top: 8pt; padding-bottom: 12pt">
        <table border="1" style="font-size: 10pt; user-select: text;" width="100%">
          <tr>
            <th rowspan="2">
              <fmt:message key="date"/>
            </th>
            <th rowspan="2">
              <fmt:message key="turn"/>
            </th>
            <th colspan="2">
              <fmt:message key="vro.sun.before"/>
            </th>
            <th colspan="2">
              <fmt:message key="vro.sun.after"/>
            </th>
            <th colspan="2">
              <fmt:message key="kernel"/>
            </th>
            <th colspan="2">
              <fmt:message key="husk"/>
            </th>
            <th colspan="3">
              <fmt:message key="cake"/>
            </th>
            <th colspan="2">
              <fmt:message key="meal"/>
            </th>
          </tr>
          <tr>
            <td>
              <fmt:message key="sun.soreness"/>
            </td>
            <td>
              <fmt:message key="sun.humidity"/>
            </td>
            <td>
              <fmt:message key="sun.soreness"/>
            </td>
            <td>
              <fmt:message key="sun.humidity"/>
            </td>
            <td>
              <fmt:message key="vro.huskiness"/>
            </td>
            <td>
              <fmt:message key="vro.kernel.offset"/>
            </td>
            <td>
              <fmt:message key="sun.oiliness"/>
            </td>
            <td>
              <fmt:message key="sun.humidity"/>
            </td>
            <td>
              <fmt:message key="sun.oiliness"/>
            </td>
            <td>
              <fmt:message key="sun.humidity"/>
            </td>
            <td>
              <fmt:message key="vro.pulp.humidity"/>
            </td>
            <td>
              <fmt:message key="sun.humidity"/>
            </td>
            <td>
              <fmt:message key="sun.oiliness"/>
            </td>
          </tr>
          <tr v-for="row in table">
            <td>
              {{new Date(row.date).toLocaleDateString()}}
            </td>
            <td align="center">
              {{row.turn}}
            </td>
            <td align="center">
              {{prettyNumber(row.sorenessBefore)}}
            </td>
            <td align="center">
              {{prettyNumber(row.humidityBefore)}}
            </td>
            <td align="center">
              {{prettyNumber(row.sorenessAfter)}}
            </td>
            <td align="center">
              {{prettyNumber(row.humidityAfter)}}
            </td>
            <td align="center">
              {{prettyNumber(row.kernelHuskiness)}}
            </td>
            <td align="center">
              {{prettyNumber(row.kernelOffset)}}
            </td>
            <td align="center">
              {{prettyNumber(row.huskOiliness)}}
            </td>
            <td align="center">
              {{prettyNumber(row.huskHumidity)}}
            </td>
            <td align="center">
              {{prettyNumber(row.cakeOiliness)}}
            </td>
            <td align="center">
              {{prettyNumber(row.cakeHumidity)}}
            </td>
            <td align="center">
              {{prettyNumber(row.pulpHumidity)}}
            </td>
            <td align="center">
              {{prettyNumber(row.mealHumidity)}}
            </td>
            <td align="center">
              {{prettyNumber(row.mealOiliness)}}
            </td>
          </tr>
          <tr>
            <th colspan="2">
              {{table.length}}
            </th>
            <th>
              {{middle(table, 'sorenessBefore')}}
            </th>
            <th>
              {{middle(table, 'humidityBefore')}}
            </th>
            <th>
              {{middle(table, 'sorenessAfter')}}
            </th>
            <th>
              {{middle(table, 'humidityAfter')}}
            </th>
            <th>
              {{middle(table, 'kernelHuskiness')}}
            </th>
            <th>
              {{middle(table, 'kernelOffset')}}
            </th>
            <th>
              {{middle(table, 'huskOiliness')}}
            </th>
            <th>
              {{middle(table, 'huskHumidity')}}
            </th>
            <th>
              {{middle(table, 'cakeOiliness')}}
            </th>
            <th>
              {{middle(table, 'cakeHumidity')}}
            </th>
            <th>
              {{middle(table, 'pulpHumidity')}}
            </th>
            <th>
              {{middle(table, 'mealHumidity')}}
            </th>
            <th>
              {{middle(table, 'mealOiliness')}}
            </th>
          </tr>
        </table>
      </div>
      <div style="padding-bottom: 24pt"></div>
    </div>
  </div>
</html>
