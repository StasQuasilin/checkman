<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 30.11.2019
  Time: 0:22
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var productEdit = new Vue({
    el:'#productEdit'
  })
</script>
<table>
  <tr>
    <td>
      <label for="name">
        <fmt:message key="product.name"/>
      </label>
    </td>
    <td>
      <input id="name">
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
