<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 04.12.2019
  Time: 15:34
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var contractSelector = new Vue({
    el:'#contractSelector',
    data:{
      contract:{
        id:-1
      },
      contracts:[]
    },
    methods:{
      save:function(deal){
        closeModal();
        saveModal(deal);
      }
    }
  });
  <c:forEach items="${contracts}" var="contract">
  contractSelector.contracts.push(JSON.parse('${contract.toJson()}'));
  </c:forEach>
</script>
<table id="contractSelector" width="100%">
  <tr>
    <td>
      <div>
        <fmt:message key="deal.new"/>
      </div>
      <div v-for="contract in contracts" v-on:click="save(contract)">
        <div style="display: flex">
          <div>
            {{new Date(contract.from).toLocaleString().substring(0, 10)}}
          </div>
          <div v-if="contract.address">
            {{contract.address.city}}, {{contract.address.street}}, {{contract.address.build}}
          </div>
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
