<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table border="1">
  <tr>
    <th colspan="3">
      <fmt:message key="deal"/>
    </th>
  </tr>
  <tr>
    <td>
      <fmt:message key="deal.type"/>
    </td>
    <td>
      :
    </td>
    <td>
      <fmt:message key="${plan.deal.type}"/>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
