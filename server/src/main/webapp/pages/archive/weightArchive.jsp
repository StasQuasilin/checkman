<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<c:set var="unit">${weight.deal.unit.name}</c:set>
<table>
  <tr>
    <td>
      <fmt:message key="deal.organisation"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.counterparty.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="deal.product"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.product.name}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.driver"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.driver.person.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.automobile"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.vehicle.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="load.plan"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.plan} ${unit}
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <fmt:message key="load.fact"/>
    </td>
  </tr>
  <tr>
    <td align="right">
      <fmt:message key="weight.brutto"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.weight.brutto} ${unit}
        <fmt:formatDate value="${weight.transportation.weight.bruttoTime.time}" pattern="dd.MM.yy HH:mm"/>
    </td>
  </tr>
  <tr>
    <td align="right">
      <fmt:message key="weight.tara"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.weight.tara} ${unit}
      <fmt:formatDate value="${weight.transportation.weight.taraTime.time}" pattern="dd.MM.yy HH:mm"/>
    </td>
  </tr>
  <tr>
    <td align="right">
      <fmt:message key="weight.netto"/>
    </td>
    <td>
      :
    </td>
    <td>
      ${weight.transportation.weight.netto} ${unit}

    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.time.in"/>
    </td>
    <td>
      :
    </td>
    <td>
      <fmt:formatDate value="${weight.transportation.timeIn.time}"  pattern="dd.MM.yy HH:mm"/>
      ${weight.transportation.timeIn.creator.person.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.time.out"/>
    </td>
    <td>
      :
    </td>
    <td>
      <fmt:formatDate value="${weight.transportation.timeOut.time}"  pattern="dd.MM.yy HH:mm"/>
      ${weight.transportation.timeOut.creator.person.value}
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
