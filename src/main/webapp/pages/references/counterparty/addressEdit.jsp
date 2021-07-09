<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.11.2019
  Time: 15:23
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/addressEditor.vue?v=${now}"></script>
  <script>
  addressEditor.api.save='${save}';
  <c:forEach items="${addressTypes}" var="type">
  addressEditor.addressTypes.push({
    id:'${type}',
    value:'<fmt:message key="${type}.address"/>'
  });
  </c:forEach>
  addressEditor.addressType='${type}';
  <c:if test="${not empty counterparty }">
  addressEditor.counterparty = {
    id:${counterparty.id},
    value:'${counterparty.value}'
  };
  </c:if>
  <c:if test="${not empty address}">
  addressEditor.address = ${address.toJson()};
  </c:if>
</script>
<table id="addressEditor" class="editor">
  <tr>
    <td>
      <fmt:message key="deal.organisation"/>
    </td>
    <td>
      <object-input :props="counterpartyProps" :object="counterparty"></object-input>
    </td>
  </tr>
  <tr>
    <td>
      <label for="type">
        <fmt:message key="address.type"/>
      </label>
    </td>
    <td>
      <select id="type" v-model="addressType">
        <option v-for="type in addressTypes" :value="type.id">
          {{type.value}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="index">
        <fmt:message key="address.index"/>
      </label>
    </td>
    <td>
      <input id="index" v-model="address.index" onfocus="this.select()" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="region">
        <fmt:message key="address.region"/>
      </label>
    </td>
    <td>
      <input id="region" v-model="address.region" onfocus="this.select()" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="district">
        <fmt:message key="address.district"/>
      </label>
    </td>
    <td>
      <input id="district" v-model="address.district" onfocus="this.select()" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="city">
        <fmt:message key="address.city"/>
      </label>
    </td>
    <td>
      <input id="city" v-model="address.city" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="street">
        <fmt:message key="address.street"/>
      </label>
    </td>
    <td>
      <input id="street" v-model="address.street" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="build">
        <fmt:message key="address.build"/>
      </label>
    </td>
    <td>
      <input id="build" v-model="address.build" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="block">
        <fmt:message key="address.block"/>
      </label>
    </td>
    <td>
      <input id="block" v-model="address.block" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="flat">
        <fmt:message key="address.flat"/>
      </label>
    </td>
    <td>
      <input id="flat" v-model="address.flat" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="save()">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
