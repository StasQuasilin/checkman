<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 01.11.2019
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  print = new Vue({
    el:'#print',
    data:{
      api:{},
      date:new Date().toISOString().substring(0, 10)
    },
    methods:{
      print:function(){
        PostReq(this.api.print, {date:this.date}, function(a){
          let print = window.open();
          print.document.write(a);
        }, function(e){
          console.log(e)
        }, true)
      },
      pickDate:function(){
        const self = this;
        datepicker.show(function(a){
          self.date = a;
        }, this.date)
      }
    }
  });
  print.api.print = '${print}';
</script>
<table id="print">
  <tr>
    <td>
      <label for="date">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="date" v-model="new Date(date).toLocaleDateString()"
             v-on:click="pickDate()" readonly style="width: 7em">
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="print()">
        <fmt:message key="document.print"/>
      </button>
    </td>
  </tr>
</table>
</html>
