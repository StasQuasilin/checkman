<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 10.12.2019
  Time: 12:02
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<style>
  .error span{
    font-size: 12pt;
  }
</style>
<script>
  var report = new Vue({
    el:'#report',
    data:{
      api:{},
      subdivisions:[],
      report:{
        subdivision:-1,
        reason:'',
        delay:{
          days:0,
          hours:1,
          minutes:0
        }
      },
      errors:{
        subdivision:false,
        reason:false
      }
    },
    methods:{
      checkHours:function(){
        if(this.report.delay.hours > 23){
          let days = Math.floor(this.report.delay.hours / 24);
          this.report.delay.days += days;
          this.report.delay.hours -= days * 24;
        } else if (this.report.delay.hours < 0){
          this.report.delay.hours = 0;
        }
      },
      checkMinutes:function(){
        if (this.report.delay.minutes > 59){
          let hours = Math.floor(this.report.delay.minutes / 60);
          this.report.delay.hours += hours;
          this.report.delay.minutes -= hours * 60;
        } else  if (this.report.delay.minutes < 0){
          this.report.delay.minutes = 0;
        }
      },
      save:function(){
        let e = this.errors;
        e.subdivision = this.report.subdivision == -1;
        e.reason = this.report.reason.replace(/ /g, '') === '';
        if (!e.subdivision && !e.reason) {
          PostApi(this.api.save, this.report, function (a) {
            if (a.status === 'success') {
              closeModal();
            }
          })
        }
      }
    }
  });
  report.api.save = '${save}';
  <c:forEach items="${subdivisions}" var="subdivision">
  report.subdivisions.push({
    id:${subdivision.id},
    name:'${subdivision.name}'
  });
  </c:forEach>
</script>
<table width="100%" id="report">
  <tr>
    <td>
      <label for="subdivision">
        <fmt:message key="subdivision"/>
      </label>
    </td>
    <td>
      <select :class="{error : errors.subdivision}" v-on:click="errors.subdivision = false"
              id="subdivision" v-model="report.subdivision">
        <option value="-1">
          <fmt:message key="need.select"/>
        </option>
        <option v-for="sub in subdivisions" :value="sub.id">
          {{sub.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2">
        <label for="reason">
          <span :class="{error : errors.reason}">
          <fmt:message key="stop.reason"/>
            </span>
        </label>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <textarea id="reason" style="width: 100%; height: 100pt" onfocus="this.select()"
          v-model="report.reason" v-on:click="errors.reason = false"></textarea>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <span style="font-size: 8pt; color: darkgray">
        {{report.reason.length}}
      </span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="stop.delay"/>
    </td>
    </tr>
  <tr>
    <td colspan="2">
      <label for="days">
        <fmt:message key="days"/>
      </label>
      <input id="days" type="number" step="0" v-model="report.delay.days" style="width: 7em">
      <label for="hours">
        <fmt:message key="hourses"/>
      </label>
      <input id="hours" type="number" min="0" max="23" v-model="report.delay.hours" style="width: 7em"
          v-on:change="checkHours()">
      <label for="minutes">
        <fmt:message key="minutes"/>
      </label>
      <input id="minutes" type="number" min="0" max="59" v-model="report.delay.minutes" style="width: 7em"
          v-on:change="checkMinutes">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
