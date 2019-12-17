<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 16.12.2019
  Time: 16:33
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/editor.css"/>
<style>
  input[type="number"]{
    width: 8em;
  }
</style>
<script>
  var corrector = new Vue({
    el:'#correct',
    data:{
      api:{},
      storages:[]
    }
  });
  var storage;
  var product;
  let n;
  <c:forEach items="${stocks}" var="stock">
  storage = JSON.parse('${stock.key.toJson()}');
  storage.values = {};
  <c:forEach items="${stock.value}" var="product">
  product = JSON.parse('${product.key.toJson()}');
  product.value = 0;
  <c:forEach items="${product.value}" var="shipper">
  n = ${shipper.value};
  product.value += Math.round(n * 100) / 100;
  </c:forEach>
  product.correction = product.value;
  if (!storage.values[product.id]){
    storage.values[product.id] = product;
  }
  </c:forEach>
  corrector.storages.push(storage);
  </c:forEach>
</script>
<html>
  <table id="correct" class="editor">
    <tr>
      <td>
        <fmt:message key="date"/>
      </td>
    </tr>
    <tr v-for="storage in storages">
      <td>
        <div>
          {{storage.name}}
        </div>
        <div v-for="product in storage.values" style="width: 100%; text-align: right">
          {{product.name}}:
          <input type="number" v-model="product.value">
        </div>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button onclick="closeModal()">
          <fmt:message key="button.cancel"/>
        </button>
        <button>
          <fmt:message key="button.save"/>
        </button>
      </td>
    </tr>
  </table>
</html>
