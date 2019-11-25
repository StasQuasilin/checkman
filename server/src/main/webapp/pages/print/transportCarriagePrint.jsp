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
        <th rowspan="2">
          <fmt:message key="analyses"/>
        </th>
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
      </tr>
      <c:forEach items="${transportations}" var="transport">
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
          <td rowspan="2">
            <c:choose>
              <c:when test="${transport.sunAnalyses ne null}">
                <div>
                  <fmt:message key="sun.humidity.1"/>:
                  <fmt:formatNumber value="${transport.sunAnalyses.humidity1}"/>
                </div>
                <c:if test="${transport.sunAnalyses.humidity2 > 0}">
                  <div>
                    <fmt:message key="sun.humidity.2"/>:
                    <fmt:formatNumber value="${transport.sunAnalyses.humidity2}"/>
                  </div>
                </c:if>
                <div>
                  <fmt:message key="sun.soreness"/>:
                  <fmt:formatNumber value="${transport.sunAnalyses.soreness}"/>
                </div>
                <div>
                  <fmt:message key="sun.oil.impurity"/>:
                  <fmt:formatNumber value="${transport.sunAnalyses.oilImpurity}"/>
                </div>
                <div>
                  <fmt:message key="sun.oiliness"/>:
                  <fmt:formatNumber value="${transport.sunAnalyses.oiliness}"/>
                </div>
                <c:if test="${transport.sunAnalyses.contamination}">
                  <div style="color: orangered">
                    !! <fmt:message key="sun.contamination"/>
                  </div>
                </c:if>
                <c:if test="${transport.weight.correction ne 0}">
                  <fmt:message key="recalculation"/>:
                  <fmt:formatNumber value="${transport.weight.correction}"/>%
                </c:if>
              </c:when>
            </c:choose>
          </td>
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
        </tr>
      </c:forEach>
    </table>
    <div style="padding-top: 24pt">
      <fmt:message key="amount.total.carriage"/>: <fmt:formatNumber value="${total}"/>
    </div>
    <c:if test="${total ne correction}">
      <div>
        <fmt:message key="valid.weight"/>: <fmt:formatNumber value="${correction}"/>
      </div>
    </c:if>
  </div>
</html>
