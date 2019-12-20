<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 25.11.2019
  Time: 11:31
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  <div>
    <div class="head">
      <fmt:message key="transport.carriage.head"/>
    </div>
    <div class="head">
      <c:if test="${not empty from}">
        <fmt:message key="date.from"/>
        <fmt:formatDate value="${from}" pattern="dd.MM.yyyy"/>
      </c:if>
      <c:if test="${not empty to}">
        <fmt:message key="date.to"/>
        <fmt:formatDate value="${to}" pattern="dd.MM.yyyy"/>
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

      <c:if test="${transportations.size() > 1}">
        <div style="font-size: 14pt; padding-bottom: 8pt">
            ${t.key.name}
        </div>
      </c:if>
      <c:set value="0" var="correction"/>
      <c:set value="0" var="total"/>

      <table>
        <tr>
          <th rowspan="2">
            <fmt:message key="date"/>
          </th>
          <th>
            <fmt:message key="transportation.time.in"/>
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
          <th rowspan="2">
            <fmt:message key="weight"/>
          </th>
          <c:choose>
            <c:when test="${t.key.analysesType eq 'sun'}">
              <th colspan="2">
                <fmt:message key="sun.humidity"/>
              </th>
              <th rowspan="2">
                <fmt:message key="sun.soreness"/>
              </th>
              <th rowspan="2">
                <fmt:message key="sun.oiliness"/>
              </th>
            </c:when>
            <c:when test="${t.key.analysesType eq 'oil'}">
              <th rowspan="2">
                <fmt:message key="oil.color.value"/>
              </th>
              <th rowspan="2">
                <fmt:message key="sun.acid.value"/>
              </th>
              <th rowspan="2">
                <fmt:message key="oil.peroxide"/>
              </th>
              <th rowspan="2">
                <fmt:message key="oil.phosphorus"/>
              </th>
              <th rowspan="2">
                <fmt:message key="sun.humidity"/>
              </th>
            </c:when>
            <c:when test="${t.key.analysesType eq 'meal'}">
              <th rowspan="2">
                <fmt:message key="sun.humidity"/>
              </th>
              <th rowspan="2">
                <fmt:message key="cake.protein"/>
              </th>
              <th rowspan="2">
                <fmt:message key="cake.cellulose"/>
              </th>
              <th rowspan="2">
                <fmt:message key="sun.oiliness"/>
              </th>
            </c:when>
          </c:choose>
        </tr>
        <tr>
          <th>
            <fmt:message key="transportation.time.out"/>
          </th>
          <c:if test="${empty driver}">
            <th>
              <fmt:message key="transportation.automobile"/>
            </th>
          </c:if>
          <c:choose>
            <c:when test="${t.key.analysesType eq 'sun'}">
              <th>
                <fmt:message key="transportation.automobile"/>
              </th>
              <th>
                <fmt:message key="transportation.automobile.trailer"/>
              </th>
            </c:when>
          </c:choose>
        </tr>
        <c:forEach items="${t.value}" var="transport">
          <tr>
            <td rowspan="2" align="center">
              <fmt:formatDate value="${transport.date}" pattern="dd.MM.yy"/>
            </td>
            <td>
              <fmt:formatDate value="${transport.timeIn.time}" pattern="dd.MM HH:mm"/>
            </td>
            <c:if test="${empty organisation}">
              <td rowspan="2">
                  ${transport.counterparty.value}
              </td>
            </c:if>
            <c:if test="${empty driver}">
              <td>
                  ${transport.driver.person.surname}
                  ${transport.driver.person.forename}
                  ${transport.driver.person.patronymic}
              </td>
            </c:if>
            <td rowspan="2">
              <div>
                <fmt:message key="weight.brutto"/>:
                <fmt:formatNumber value="${transport.weight.brutto}"/>
              </div>
              <div>
                <fmt:message key="weight.tara"/>:
                <fmt:formatNumber value="${transport.weight.tara}"/>
              </div>
              <div>
                <fmt:message key="weight.netto"/>:
                <fmt:formatNumber value="${transport.weight.netto}"/>
                <c:set var="correction" value="${correction + transport.weight.correctedNetto}"/>
                <c:set var="total" value="${total + transport.weight.netto}"/>
              </div>
              <c:if test="${transport.weight.correction ne 0}">
                <div>
                  <fmt:message key="weight.creadit.netto"/>:
                  <fmt:formatNumber value="${transport.weight.correctedNetto}"/>
                </div>
              </c:if>
            </td>
            <c:choose>
              <c:when test="${transport.sunAnalyses ne null}">
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.sunAnalyses.humidity1}"/> %
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.sunAnalyses.humidity2}"/> %
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.sunAnalyses.soreness}"/> %
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.sunAnalyses.oiliness}"/> %
                </td>
              </c:when>
              <c:when test="${transport.oilAnalyses ne null}">
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.oilAnalyses.color}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.oilAnalyses.acidValue}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.oilAnalyses.peroxideValue}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.oilAnalyses.phosphorus}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.oilAnalyses.humidity}"/> %
                </td>
              </c:when>
              <c:when test="${transport.mealAnalyses ne null}">
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.mealAnalyses.humidity}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.mealAnalyses.protein}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.mealAnalyses.cellulose}"/>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.mealAnalyses.oiliness}"/>
                </td>
              </c:when>
            </c:choose>
          </tr>
          <tr>
            <td>
              <fmt:formatDate value="${transport.timeOut.time}" pattern="dd.MM HH:mm"/>
            </td>
            <c:if test="${empty driver}">
              <td>
                  ${transport.vehicle.value}
              </td>
            </c:if>
            <c:choose>
              <c:when test="${transport.sunAnalyses ne null}">
              </c:when>
            </c:choose>
          </tr>
        </c:forEach>
      </table>
      <div style="padding-top: 12pt">
        <fmt:message key="amount.total.carriage"/>: <fmt:formatNumber value="${total}"/>
      </div>
      <c:if test="${total ne correction}">
        <div>
          <fmt:message key="valid.weight"/>: <fmt:formatNumber value="${correction}"/>
        </div>
      </c:if>
      <div style="padding-bottom: 12pt">
        &nbsp;
      </div>
    </c:forEach>
  </div>
</html>
