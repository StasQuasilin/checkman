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
  <table width="100%">
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
        <fmt:message key="sun.humidity"/>
      </th>
      <th>
        <fmt:message key="sun.soreness"/>
      </th>
    </tr>
    <c:forEach items="${plans}" var="plan" varStatus="status">
      <tr>
        <td>
          ${status.index + 1}
        </td>
        <td>
          ${plan.deal.organisation.value}
        </td>
        <td>
          ${plan.driver.person.value}
        </td>
        <td>
          ${plan.vehicle.model}<br>
          ${plan.vehicle.number}
          ${plan.vehicle.trailer}

        </td>
        <td>
          <fmt:formatNumber value="${plan.weight.netto}"/>
        </td>
        <td>
          <fmt:formatNumber value="${plan.sunAnalyses.soreness}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>

</html>
