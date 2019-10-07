<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 07.10.2019
  Time: 22:58
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table width="100%">
  <tr>
    <td>
      <fmt:message key="date"/>
    </td>
    <td>
      <fmt:formatDate value="${plan.date}" pattern="dd.MM.yyyy"/>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="deal.organisation"/>
    </td>
    <td>
      ${plan.deal.organisation.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="weight.netto"/>
    </td>
    <td>
      <fmt:formatNumber value="${plan.transportation.weight.netto}"/>&nbsp;${plan.deal.unit.name}
    </td>
  </tr>
  <c:forEach items="${storages}" var="storage">
    <tr>
      <td>
        ${storage.storage.name}
      </td>
    </tr>
  </c:forEach>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
