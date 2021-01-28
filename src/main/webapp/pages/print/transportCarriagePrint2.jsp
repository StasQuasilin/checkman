<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 25.11.2019
  Time: 11:31
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  *{
    user-select: text;
  }
  table{
    border-collapse: collapse;
    font-size: 10pt;
  }
  th, td{
    border: solid 1pt;
    padding: 0 4pt;
  }
  .head{
    width: 100%;
    text-align: center;
  }
</style>
<head>
  <title>
    <fmt:message key="transport.carriage.2.head"/>
  </title>
</head>
  <div id="data">
    <div class="head">
      <fmt:message key="transport.carriage.2.head"/>
    </div>
    <div class="head">
      <c:if test="${not empty from}">
        <fmt:message key="date.from"/>
        <fmt:formatDate value="${from}" pattern="dd.MM.yyyy HH:mm"/>
      </c:if>
      <c:if test="${not empty to}">
        <fmt:message key="date.to"/>
        <fmt:formatDate value="${to}" pattern="dd.MM.yyyy HH:mm"/>
      </c:if>
    </div>
    <div class="head">
      <c:if test="${not empty organisation}">
        <fmt:message key="deal.organisation"/> ${organisation.value}
      </c:if>
    </div>
    <div class="head" style="padding-bottom: 24pt">
      <c:if test="${not empty driver}">
        <fmt:message key="transportation.driver"/> ${driver.person.value}
      </c:if>
    </div>
    <c:forEach items="${transportations}" var="t">

      <div style="font-size: 14pt; padding-bottom: 8pt">
          ${t.key.name}
      </div>
      <c:set value="0" var="correction"/>
      <c:set value="0" var="total"/>

      <table style="border-bottom: solid black 1.5pt">
        <tr>
          <th rowspan="2">
            <fmt:message key="date"/>
          </th>
          </th>
          <c:if test="${empty organisation}">
            <th rowspan="2">
              <fmt:message key="deal.organisation"/>
            </th>
          </c:if>
          <c:if test="${empty driver}">
            <th>
              <fmt:message key="transportation.driver"/>
            </th>
          </c:if>
        </tr>
        <tr>
          <c:if test="${empty driver}">
            <th>
              <fmt:message key="transportation.automobile"/>
            </th>
          </c:if>
        </tr>
        <c:forEach items="${t.value}" var="transport">
          <tr style="border-top: solid black 1.5pt">
            <td rowspan="2" align="center">
              <fmt:formatDate value="${transport.date}" pattern="dd.MM.yy"/>
            </td>
            <c:if test="${empty organisation}">
              <td rowspan="2">
                  ${transport.counterparty.value}
              </td>
            </c:if>
            <c:if test="${empty driver}">
              <td style="border-bottom-color: white">
                  ${transport.driver.person.surname}
                  ${transport.driver.person.forename}
                  ${transport.driver.person.patronymic}
              </td>
            </c:if>
          </tr>
          <tr>
            <c:if test="${empty driver}">
              <td>
                ${transport.vehicle.model}
                /${transport.vehicle.number}/
                <c:if test="${transport.trailer ne null}">
                  /${transport.trailer.number}/
                </c:if>
              </td>
            </c:if>
          </tr>
        </c:forEach>
      </table>
    </c:forEach>
  </div>
</html>
