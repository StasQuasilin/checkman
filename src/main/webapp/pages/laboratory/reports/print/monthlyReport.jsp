<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .content table{
    border-collapse: collapse;
  }
  .content table, .content th, .content td {
    border: 1px solid black;
  }
  .content td{
    padding: 0 4pt;
  }
</style>
<div class="content">
  <table>
    <tr>
      <th>
        #
      </th>
      <th>
        <fmt:message key="deal.organisation"/>
      </th>
      <th>
        <fmt:message key="waybill.driver"/>
      </th>
      <th>
        <fmt:message key="waybill.vehicle"/>
      </th>
      <th>
        <fmt:message key="weight"/>
      </th>
    </tr>
    <c:forEach items="${plans}" var="plan" varStatus="status">
      <tr>
        <td>
          ${status.index}
        </td>
        <td>
          ${plan.deal.organisation.value}
        </td>
        <td>
          ${plan.transportation.driver.person.value}
        </td>
        <td>
          ${plan.transportation.vehicle.value}
        </td>
        <c:set var="weight">0</c:set>
        <c:forEach items="${plan.transportation.weights}" var="w">
          <c:set var="weight">${weight+w.netto}</c:set>
        </c:forEach>
        <td>
          <fmt:formatNumber value="${weight}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>

</html>
