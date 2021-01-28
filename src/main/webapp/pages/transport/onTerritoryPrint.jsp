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
  printer = new Vue({
    el:'#print',
    data:{
      api:{}
    },
    methods:{
      print:function(){
        PostReq(this.api.print, {}, function(a){
          let print = window.open();
          print.document.write(a);
        })
      }
    }
  });
  printer.api.print = '${print}';
</script>
  <table id="print" width="100%">
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
