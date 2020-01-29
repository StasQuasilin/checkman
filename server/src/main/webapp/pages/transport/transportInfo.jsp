<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 19.12.2019
  Time: 15:17
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table width="100%">
  <c:choose>
    <c:when test="${fn:length(vehicles) > 0}">
      <c:forEach items="${vehicles}" var="vehicle">
        <tr>
          <td colspan="2" align="center" style="font-size: 14pt; font-weight: bold">
            <fmt:message key="transportation.automobile"/>
          </td>
        </tr>
        <c:if test="${not empty vehicle.model}">
          <tr>
            <td>
              <fmt:message key="transportation.automobile.model"/>
            </td>
            <td>
                ${vehicle.brand} ${vehicle.model}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty vehicle.category}">
          <tr>
            <td>
              <fmt:message key="truck.category"/>
            </td>
            <td>
                ${vehicle.category}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty vehicle.kind}">
          <tr>
            <td>
              <fmt:message key="truck.kind"/>
            </td>
            <td>
                ${vehicle.kind}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty vehicle.year}">
          <tr>
            <td>
              <fmt:message key="truck.year"/>
            </td>
            <td>
                ${vehicle.year}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty vehicle.color}">
          <tr>
            <td>
              <fmt:message key="truck.color"/>
            </td>
            <td>
                ${vehicle.color}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty vehicle.document}">
          <tr>
            <td>
              <fmt:message key="truck.document"/>
            </td>
            <td>
                ${vehicle.document}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty vehicle.vin}">
          <tr>
            <td>
              <fmt:message key="truck.vin"/>
            </td>
            <td>
                ${vehicle.vin}
            </td>
          </tr>
        </c:if>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <tr>
        <td colspan="2">
          <div style="font-size: 14pt">
            <fmt:message key="vehicle.info.no"/>
          </div>
        </td>
      </tr>
    </c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${fn:length(trailers) > 0}">
      <c:forEach items="${trailers}" var="trailer">
        <tr>
          <td colspan="2" align="center" style="font-size: 14pt; font-weight: bold">
            <fmt:message key="transportation.automobile.trailer"/>
          </td>
        </tr>
        <c:if test="${not empty trailer.model}">
          <tr>
            <td>
              <fmt:message key="transportation.automobile.model"/>
            </td>
            <td>
                ${trailer.brand} ${trailer.model}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty trailer.category}">
          <tr>
            <td>
              <fmt:message key="truck.category"/>
            </td>
            <td>
                ${trailer.category}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty trailer.kind}">
          <tr>
            <td>
              <fmt:message key="truck.kind"/>
            </td>
            <td>
                ${trailer.kind}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty trailer.year}">
          <tr>
            <td>
              <fmt:message key="truck.year"/>
            </td>
            <td>
                ${trailer.year}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty trailer.color}">
          <tr>
            <td>
              <fmt:message key="truck.color"/>
            </td>
            <td>
                ${trailer.color}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty trailer.document}">
          <tr>
            <td>
              <fmt:message key="truck.document"/>
            </td>
            <td>
                ${trailer.document}
            </td>
          </tr>
        </c:if>
        <c:if test="${not empty trailer.vin}">
          <tr>
            <td>
              <fmt:message key="truck.vin"/>
            </td>
            <td>
                ${trailer.vin}
            </td>
          </tr>
        </c:if>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <tr>
        <td colspan="2">
          <div style="font-size: 14pt">
            <fmt:message key="trailer.info.no"/>
          </div>
        </td>
      </tr>
    </c:otherwise>
  </c:choose>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
