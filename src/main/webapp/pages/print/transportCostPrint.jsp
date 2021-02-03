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
  <div id="data">
    <div class="head">
      <fmt:message key="transport.carriage.head"/>
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
      <c:set value="0" var="totalCost"/>
      <c:set value="0" var="productCost"/>

      <table style="border-bottom: solid black 1.5pt">
        <tr>
          <th rowspan="2">
            <fmt:message key="date"/>
          </th>
          <th style="width: 5em">
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
          <th colspan="5">
            <fmt:message key="weight"/>
          </th>
          <th rowspan="2">
            <fmt:message key="deal.price"/>
          </th>
          <th rowspan="2">
            <fmt:message key="product.amount"/>
          </th>
          <th rowspan="2">
            <fmt:message key="delivery.cost"/>
          </th>
          <th rowspan="2">
            <fmt:message key="unit.delivery.cost"/>
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
          <th>
            <fmt:message key="weight.gross"/>
          </th>
          <th>
            <fmt:message key="weight.tare"/>
          </th>
          <th>
            <fmt:message key="weight.net"/>
          </th>
          <th colspan="2">
            <fmt:message key="weight.creadit.netto"/>:
          </th>
        </tr>
        <c:forEach items="${t.value}" var="transport">
          <tr style="border-top: solid black 1.5pt">
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
              <td style="border-bottom-color: white">
                  ${transport.driver.person.surname}
                  ${transport.driver.person.forename}
                  ${transport.driver.person.patronymic}
              </td>
            </c:if>
            <td rowspan="2">
              <fmt:formatNumber value="${transport.weight.brutto *100}" pattern="##,##" maxFractionDigits="3"/>
            </td>
            <td rowspan="2">
              <fmt:formatNumber value="${transport.weight.tara * 100}" pattern="##,##" maxFractionDigits="3"/>
            </td>
            <td rowspan="2">
              <fmt:formatNumber value="${transport.weight.netto * 100}" pattern="##,##" maxFractionDigits="3"/>
              <c:set var="correction" value="${correction + transport.weight.correctedNetto}"/>
              <c:set var="total" value="${total + transport.weight.netto}"/>
            </td>
            <td rowspan="2" style="border-right-color: white">
              <c:if test="${transport.weight.correction ne 0}">
                <fmt:formatNumber value="${transport.weight.correctedNetto * 1000}" pattern="##,###" maxFractionDigits="0"/>
              </c:if>
            </td>
            <td rowspan="2">
              <c:if test="${transport.weight.correction ne 0}">
                <fmt:formatNumber value="${(transport.weight.correctedNetto-transport.weight.netto)}" maxFractionDigits="3"/>
              </c:if>
            </td>
            <td rowspan="2" style="text-align: center">
              <fmt:formatNumber value="${transport.deal.price}" var="price"/>
                ${fn:replace(price, ",", "&nbsp;")}
            </td>
            <td rowspan="2" style="text-align: center">
              <c:if test="${transport.weight.netto > 0}">
                <fmt:formatNumber value="${transport.deal.price * transport.weight.netto}" var="pc"/>
                <c:set var="productCost" value="${productCost + transport.deal.price * transport.weight.netto}"/>
                ${fn:replace(pc, ",", "&nbsp;")}
              </c:if>
            </td>
            <td rowspan="2" style="text-align: center">
              <c:forEach items="${transport.deal.costs}" var="cost">
                <c:choose>
                  <c:when test="${not empty transport.transporter.organisationWe}">
                    <c:if test="${cost.customer eq 'szpt'}">
                      ${cost.cost}
                      <c:set var="totalCost" value="${totalCost + cost.cost}"/>
                    </c:if>
                  </c:when>
                  <c:otherwise>
                    <c:if test="${cost.customer ne 'szpt'}">
                      ${cost.cost}
                      <c:set var="totalCost" value="${totalCost + cost.cost}"/>
                    </c:if>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </td>
            <td rowspan="2" style="text-align: center">
              <c:if test="${transport.weight.netto > 0}">
                <c:forEach items="${transport.deal.costs}" var="cost">
                  <c:choose>
                    <c:when test="${not empty transport.transporter.organisationWe}">
                      <c:if test="${cost.customer eq 'szpt'}">
                        <fmt:formatNumber value="${cost.cost / transport.weight.netto}" pattern="##.###"/>
                      </c:if>
                    </c:when>
                    <c:otherwise>
                      <c:if test="${cost.customer ne 'szpt'}">
                        <fmt:formatNumber value="${cost.cost / transport.weight.netto}" pattern="##.###"/>
                      </c:if>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </c:if>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:formatDate value="${transport.timeOut.time}" pattern="dd.MM HH:mm"/>
            </td>
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
      <div style="padding-top: 12pt">
        <fmt:message key="amount.total.carriage"/>:
        <fmt:formatNumber value="${total}" maxFractionDigits="3" var="c"/>
          ${fn:replace(c, ",", "&nbsp;")}
      </div>
      <c:if test="${total ne correction}">
        <div>
          <fmt:message key="valid.weight"/>:
          <fmt:formatNumber value="${correction}" maxFractionDigits="3" var="vw"/>
            ${fn:replace(vw, ",", "&nbsp;")}
        </div>
      </c:if>
      <c:if test="${productCost > 0}">
        <div>
          <fmt:message key="total.product.cost"/>:
          <fmt:formatNumber value="${productCost}" var="pc"/>
            ${fn:replace(pc, ",", "&nbsp;")}
        </div>
      </c:if>
      <c:if test="${totalCost > 0}">
        <div>
          <fmt:message key="total.delivery.cost"/>:
          <fmt:formatNumber value="${totalCost}" var="tc"/>
          ${fn:replace(tc, ",", "&nbsp;")}
        </div>
      </c:if>
      <div style="padding-bottom: 12pt">
        &nbsp;
      </div>
    </c:forEach>
  </div>
</html>
