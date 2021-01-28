<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 28.10.2019
  Time: 15:15
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  contractNumber = ' <fmt:message key="deal.title"/> ';
  by = ' <fmt:message key="by"/> ';
  transporter = '<fmt:message key="waybill.transporter"/>: ';
</script>
<script src="${context}/vue/summaryPrinter.vue?v=${now}"></script>
<script>
  // printer.items = list.getItems();
</script>
<table id="print">
  <tr>
    <td>
      <label for="date1">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      <input id="date1" v-model="new Date(date1).toLocaleDateString().substring(0, 10)"
             readonly style="width: 7em" v-on:click="pickDate1">
      <input id="date2" v-model="new Date(date2).toLocaleDateString().substring(0, 10)"
             readonly style="width: 7em" v-on:click="pickDate2">
    </td>
  </tr>
  <tr>
    <td>
      <label for="counterparty">
        <fmt:message key="deal.organisation"/>
      </label>
    </td>
    <td>
      <select id="counterparty" v-model="counterparty">
        <option value="-1">
          <fmt:message key="not.select"/>
        </option>
        <option v-for="c in counterpartyList" :value="c.id">
          {{c.value}}
        </option>
      </select>
      <span class="mini-close" v-on:click="counterparty =-1">
        &times;
      </span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="products"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div v-for="product in productList()">
        <input :id="'p' + product.id" type="checkbox" :id="product.id" v-model="product.check">
        <label :for="'p' + product.id">
          {{product.name}}
        </label>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="print">
        <fmt:message key="document.print"/>
      </button>
    </td>
  </tr>
</table>
</html>
