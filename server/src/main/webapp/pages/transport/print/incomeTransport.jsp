<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 01.11.2019
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div style="width: 100%; text-align: center; font-size: 14pt; padding: 16pt">
  <div>
    <fmt:message key="transport.on.territory"/>
  </div>
  <div>
    <fmt:message key="date.from"/>
    <fmt:formatDate value="${from}" pattern="hh:mm dd.MM.yyyy"/>
  </div>
  <div>
    <fmt:message key="date.to"/>
    <fmt:formatDate value="${to}" pattern="hh:mm dd.MM.yyyy"/>
  </div>
</div>
<c:set var="total" value="0"/>
<style>
  table{
    border-collapse: collapse;
  }
  tr{
    border: solid black 1pt;
    padding: 2pt 4pt;
  }
</style>
<table width="100%">
  <tr>
    <th>
      <fmt:message key="transportation.time.in"/>
    </th>
    <th>
      <fmt:message key="transportation.time.out"/>
    </th>
    <th>
      <fmt:message key="transportation.driver"/>
    </th>
    <th>
      <fmt:message key="transportation.automobile"/>
    </th>
    <th>
      <fmt:message key="deal.organisation"/>
    </th>
  </tr>
  <c:forEach items="${transportations}" var="product">
    <tr>
      <td colspan="5" style="background-color: lightgray">
        ${product.key.name}:
        ${fn:length(product.value)}
      </td>
      <c:set var="total" value="${total + fn:length(product.value)}"/>
    </tr>
    <c:forEach items="${product.value}" var="t">
      <tr>
        <td style="padding-left: 8pt">
          <fmt:formatDate value="${t.timeIn.time}" pattern="HH:mm (dd.MM.yy)"/>
        </td>
        <td style="padding-left: 8pt">
          <fmt:formatDate value="${t.timeOut.time}" pattern="HH:mm (dd.MM.yy)"/>
        </td>
        <td>
          ${t.driver.person.value}
        </td>
        <td>
          ${t.vehicle.value}
        </td>
        <td>
          ${t.counterparty.value}
        </td>
      </tr>
    </c:forEach>
  </c:forEach>
</table>
<div style="padding: 16pt; font-size: 14pt">
  <fmt:message key="amount.total"/>: ${total}
</div>
</html>
