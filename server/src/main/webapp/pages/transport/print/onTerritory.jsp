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
    <fmt:formatDate value="${now}" pattern="hh:mm dd.MM.yyyy"/>
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
      <fmt:message key="transportation.driver"/>
    </th>
    <th>
      <fmt:message key="transportation.automobile"/>
    </th>
    <th>
      <fmt:message key="deal.organisation"/>
    </th>
  </tr>
  <c:forEach items="${transport}" var="transportations">
    <tr>
      <td colspan="4" style="background-color: lightgray">
        ${transportations.key.name}:
        ${fn:length(transportations.value)}
      </td>
      <c:set var="total" value="${total + fn:length(transportations.value)}"/>
    </tr>
    <c:forEach items="${transportations.value}" var="t">
      <tr>
        <td style="padding-left: 8pt">
          <fmt:formatDate value="${t.timeIn.time}" pattern="dd.MM.yy hh:mm"/>
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
