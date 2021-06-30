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
    <c:if test="${not empty organisations}">
      <c:forEach items="${organiosations}" var="organisation">
        ${organisation.value}
      </c:forEach>

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
        <th>
          <fmt:message key="transportation.time.in"/>
        </th>
        <c:if test="${empty organisations}">
          <th rowspan="2">
            <fmt:message key="deal.organisation"/>
          </th>
        </c:if>
        <c:if test="${empty driver}">
          <th>
            <fmt:message key="transportation.driver"/>
          </th>
        </c:if>
        <c:if test="${type eq 2}">
          <th colspan="5">
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
              <th rowspan="2">
                <fmt:message key="recount.percentage"/>
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
          </c:choose>
        </c:if>
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
        <c:if test="${type eq 2}">
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
        </c:if>
      </tr>
      <c:forEach items="${t.value}" var="transport">
        <tr style="border-top: solid black 1.5pt">
          <td rowspan="2" align="center">
            <c:set var="transportationDate"><fmt:formatDate value="${transport.transportation.date}" pattern="dd.MM"/></c:set>
            ${transportationDate}
          </td>
          <td style="text-align: right">
            <c:set var="timeInDate"><fmt:formatDate value="${transport.transportation.timeIn.time}" pattern="dd.MM"/></c:set>
            &#8203;
            <c:if test="${transportationDate ne timeInDate}">
              <span style="font-size: 8pt">${timeInDate}</span>
            </c:if>
            <fmt:formatDate value="${transport.transportation.timeIn.time}" pattern="HH:mm"/>
          </td>
          <c:if test="${empty organisations}">
            <td rowspan="2">
                ${transport.dealProduct.deal.organisation.value}
            </td>
          </c:if>
          <c:if test="${empty drivers}">
            <td style="border-bottom-color: white">
                ${transport.transportation.driver.person.surname}
                ${transport.transportation.driver.person.forename}
                ${transport.transportation.driver.person.patronymic}
            </td>
          </c:if>
          <c:if test="${type eq 2}">
            <td rowspan="2" style="text-align: center">
                ${fn:replace(transport.weight.brutto, '.', ',')}
            </td>
            <td rowspan="2" style="text-align: center">
                ${fn:replace(transport.weight.tara, '.', ',')}
            </td>
            <td rowspan="2" style="text-align: center">
                ${fn:replace(transport.weight.netto, '.', ',')}
              <c:set var="correction" value="${correction + transport.weight.correctedNetto}"/>
              <c:set var="total" value="${total + transport.weight.netto}"/>
            </td>
            <td rowspan="2" style="border-right-color: white">
              <c:if test="${transport.weight.correction ne 0}">
                ${fn:replace(transport.weight.correctedNetto, '.', ',')}
<%--                <fmt:formatNumber value="${transport.weight.correctedNetto * 1000}" pattern="##,###" maxFractionDigits="0"/>--%>
              </c:if>
            </td>
            <td rowspan="2">
              <c:if test="${transport.weight.correction ne 0}">
                ( <fmt:formatNumber value="${(transport.weight.correctedNetto-transport.weight.netto)}" maxFractionDigits="3"/> )
              </c:if>
            </td>
            <c:choose>
              <c:when test="${transport.sunAnalyses ne null}">
                <td rowspan="2" align="center">
                  <c:choose>
                    <c:when test="${transport.sunAnalyses.humidity1 > 7}">
                      <b>
                        <fmt:formatNumber value="${transport.sunAnalyses.humidity1}"/> %
                      </b>
                    </c:when>
                    <c:otherwise>
                      <fmt:formatNumber value="${transport.sunAnalyses.humidity1}"/> %
                    </c:otherwise>
                  </c:choose>
                </td>
                <td rowspan="2" align="center">
                  <c:if test="${transport.sunAnalyses.humidity2 > 0}">
                    <c:choose>
                      <c:when test="${transport.sunAnalyses.humidity2 > 7}">
                        <b>
                          <fmt:formatNumber value="${transport.sunAnalyses.humidity2}"/> %
                        </b>
                      </c:when>
                      <c:otherwise>
                        <fmt:formatNumber value="${transport.sunAnalyses.humidity2}"/> %
                      </c:otherwise>
                    </c:choose>
                  </c:if>
                </td>
                <td rowspan="2" align="center">
                  <c:choose>
                    <c:when test="${transport.sunAnalyses.soreness > 3}">
                      <b>
                        <fmt:formatNumber value="${transport.sunAnalyses.soreness}"/> %
                      </b>
                    </c:when>
                    <c:otherwise>
                      <fmt:formatNumber value="${transport.sunAnalyses.soreness}"/> %
                    </c:otherwise>
                  </c:choose>
                </td>
                <td rowspan="2" align="center">
                  <fmt:formatNumber value="${transport.sunAnalyses.oiliness}"/> %
                </td>
                <td rowspan="2" align="center">
                  <c:if test="${transport.weight.correction ne 0}">
                    <fmt:formatNumber value="${transport.weight.correction}"/> %
                  </c:if>
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
            </c:choose>
          </c:if>
        </tr>
        <tr>
          <td style="text-align: right">
            <c:set var="timeOutDate"><fmt:formatDate value="${transport.transportation.timeOut.time}" pattern="dd.MM"/></c:set>
            &#8203;
            <c:if test="${transportationDate ne timeOutDate}">
              <span style="font-size: 8pt">${timeOutDate}</span>
            </c:if>
            <fmt:formatDate value="${transport.transportation.timeOut.time}" pattern="HH:mm"/>
          </td>
          <c:if test="${empty drivers}">
            <td>
              <c:if test="${not empty transport.transportation.vehicle}">
                ${transport.transportation.vehicle.model}
                /${transport.transportation.vehicle.number}/
                  <c:if test="${transport.transportation.trailer ne null}">
                    /${transport.transportation.trailer.number}/
                  </c:if>
              </c:if>
            </td>
          </c:if>
        </tr>
      </c:forEach>
    </table>
    <c:if test="${type == 2}">
      <div style="padding-top: 12pt">
        <fmt:message key="amount.total.carriage"/>: <fmt:formatNumber value="${total}" maxFractionDigits="3"/>
      </div>
      <c:if test="${total ne correction}">
        <div>
          <fmt:message key="valid.weight"/>: <fmt:formatNumber value="${correction}" maxFractionDigits="3"/>
        </div>
      </c:if>
      <div>
        &#8203;
      </div>
    </c:if>

  </c:forEach>
</div>
</html>
