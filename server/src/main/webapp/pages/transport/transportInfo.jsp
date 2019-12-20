<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 19.12.2019
  Time: 15:17
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table>
  <c:choose>
    <c:when test="${info ne null}">
      <c:if test="${not empty info.model}">
        <tr>
          <td>
            <fmt:message key="transportation.automobile.model"/>
          </td>
          <td>
              ${info.brand} ${info.model}
          </td>
        </tr>
      </c:if>
      <c:if test="${not empty info.category}">
        <tr>
          <td>
            <fmt:message key="truck.category"/>
          </td>
          <td>
              ${info.category}
          </td>
        </tr>
      </c:if>
      <c:if test="${not empty info.kind}">
        <tr>
          <td>
            <fmt:message key="truck.kind"/>
          </td>
          <td>
              ${info.kind}
          </td>
        </tr>
      </c:if>
      <c:if test="${not empty info.year}">
        <tr>
          <td>
            <fmt:message key="truck.year"/>
          </td>
          <td>
              ${info.year}
          </td>
        </tr>
      </c:if>
      <c:if test="${not empty info.color}">
        <tr>
          <td>
            <fmt:message key="truck.color"/>
          </td>
          <td>
              ${info.color}
          </td>
        </tr>
      </c:if>
      <c:if test="${info.engineVolume > 0}">
        <tr>
          <td>
            <fmt:message key="truck.engine.volume"/>
          </td>
          <td>
              ${info.engineVolume}
          </td>
        </tr>
      </c:if>
      <c:if test="${not empty info.document}">
        <tr>
          <td>
            <fmt:message key="truck.document"/>
          </td>
          <td>
              ${info.document}
          </td>
        </tr>
      </c:if>
      <c:if test="${not empty info.vin}">
        <tr>
          <td>
            <fmt:message key="truck.vin"/>
          </td>
          <td>
              ${info.vin}
          </td>
        </tr>
      </c:if>
    </c:when>
    <c:otherwise>
      <tr>
        <td>
          <div style="font-size: 14pt">
            <fmt:message key="vehicle.info.no"/>
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
