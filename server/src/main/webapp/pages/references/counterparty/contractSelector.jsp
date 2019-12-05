<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 04.12.2019
  Time: 15:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script>
  var contractSelector = new Vue({
    el:'#contractSelector',
    data:{
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
  <%--contractSelector.contracts.push('${contract.toJson()}');--%>
  </c:forEach>
</script>
<table id="contractSelector">
  <tr>
    <td>
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
        --
      </button>
    </td>
  </tr>
</table>
</html>
