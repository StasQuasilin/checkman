<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/summary.vue"></script>
<script>
  summary.api.update = '${update}';
  summary.id = ${plan.id};
  summary.update();
  addOnCloseEvent(function(){
    summary.stop();
  })
</script>
<table id="summary" border="0">
  <tr>
    <td valign="top">
      <div class="page-container">
        <table border="0">
          <tr>
            <th colspan="3">
              <a><fmt:message key="deal"/></a>
            </th>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.type"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:message key="${plan.type}"/>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.organisation"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.counterparty.value}
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
              ${plan.product.name}
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.quantity"/>
            </td>
            <td>
              :
            </td>
            <td>
              <%--<fmt:formatNumber value="${plan.deal.quantity}"/>&nbsp;${plan.deal.unit.name}--%>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.price"/>
            </td>
            <td>
              :
            </td>
            <td>
              <%--<fmt:formatNumber value="${plan.deal.price}"/>--%>
            </td>
          </tr>
        </table>
      </div>
    </td>
    <td valign="top" rowspan="2" style="width: 150pt">
      <div style="position: relative; top: -3px; overflow-y: scroll">
        <table border="0" style="border: 0" width="100%">
          <tr>
            <td>
              <%--!--%>
              <%--!--%>
              <%--WEIGHT--%>
              <%--!--%>
              <%--!--%>
              <div class="page-container">
                <table width="100%" v-if="weight.id">
                  <tr>
                    <th colspan="3">
                      <fmt:message key="weight"/>
                    </th>
                  </tr>
                  <tr>
                    <td>
                      <fmt:message key="weight.brutto"/>
                    </td>
                    <td>
                      :
                    </td>
                    <td style="width: 3em;" align="right">
                      {{(weight.brutto).toLocaleString()}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <fmt:message key="weight.tara"/>
                    </td>
                    <td>
                      :
                    </td>
                    <td align="right">
                      {{(weight.tara).toLocaleString()}}
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <fmt:message key="weight.netto"/>
                    </td>
                    <td>
                      :
                    </td>
                    <td align="right">
                      {{(weight.netto).toLocaleString()}}
                    </td>
                  </tr>
                </table>
              </div>
              <div class="page-container">
                <c:choose>
                  <c:when test="${plan.sunAnalyses ne null}">
                    <table width="100&"  style="font-size: 10pt">
                      <tr>
                        <td>
                          <fmt:message key="sun.humidity.1.short"/>
                        </td>
                        <td>
                          <fmt:formatNumber value="${plan.sunAnalyses.humidity1}"/>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <fmt:message key="sun.humidity.2.short"/>
                        </td>
                        <td>
                          <fmt:formatNumber value="${plan.sunAnalyses.humidity2}"/>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <fmt:message key="sun.soreness"/>
                        </td>
                        <td>
                          <fmt:formatNumber value="${plan.sunAnalyses.soreness}"/>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <fmt:message key="sun.oil.impurity"/>
                        </td>
                        <td>
                          <fmt:formatNumber value="${plan.sunAnalyses.oilImpurity}"/>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <fmt:message key="sun.oiliness"/>
                        </td>
                        <td>
                          <fmt:formatNumber value="${plan.sunAnalyses.oiliness}"/>
                        </td>
                      </tr>
                      <c:if test="plan.transportation.sunAnalyses.contamination">
                        <tr>
                          <td colspan="2" style="color: orangered">
                            <fmt:message key="sun.contamination"/>
                          </td>
                        </tr>
                      </c:if>
                      <tr>
                        <td>
                          <fmt:message key="create.date"/>
                        </td>
                        <td>
                          <fmt:formatDate value="${plan.sunAnalyses.createTime.time}" pattern="dd.MM.yy HH:mm"/>
                        </td>
                      </tr>
                    </table>
                  </c:when>
                  <c:when test="${plan.oilAnalyses ne null}">
                    OIL
                  </c:when>
                  <c:when test="${plan.mealAnalyses ne null}">
                    MEAL
                  </c:when>
                </c:choose>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <%--!--%>
              <%--!--%>
              <%--ANALYSES--%>
              <%--!--%>
              <%--!--%>
              <%--<div class="page-container" v-if="analyses.sun">--%>
                <%--<table>--%>
                  <%--<tr>--%>
                    <%--<th colspan="3">--%>
                      <%--<fmt:message key="analyses"/>--%>
                    <%--</th>--%>
                  <%--</tr>--%>
                  <%--<template v-for="sun in analyses.sun">--%>
                    <%--<tr>--%>
                      <%--<td>--%>
                        <%--<fmt:message key="sun.humidity"/>--%>
                      <%--</td>--%>
                      <%--<td>--%>
                        <%--:--%>
                      <%--</td>--%>
                      <%--<td>--%>
                        <%--{{(sun.humidity).toLocaleString()}}--%>
                      <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                      <%--<td>--%>
                        <%--<fmt:message key="sun.soreness"/>--%>
                      <%--</td>--%>
                      <%--<td>--%>
                        <%--:--%>
                      <%--</td>--%>
                      <%--<td>--%>
                        <%--{{(sun.soreness).toLocaleString()}}--%>
                      <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                      <%--<td>--%>
                        <%--<fmt:message key="sun.oiliness"/>--%>
                      <%--</td>--%>
                      <%--<td>--%>
                        <%--:--%>
                      <%--</td>--%>
                      <%--<td>--%>
                        <%--{{(sun.oiliness).toLocaleString()}}--%>
                      <%--</td>--%>
                    <%--</tr>--%>
                  <%--</template>--%>
                <%--</table>--%>
              <%--</div>--%>
            </td>
          </tr>
          <tr>
            <%--!--%>
            <%--!--%>
            <%--SEALS--%>
            <%--!--%>
            <%--!--%>
            <td>
              <div v-if="seals.length > 0" class="page-container">
                <table>
                  <tr>
                    <th>
                      <fmt:message key="seals"/>
                    </th>
                  </tr>
                  <tr v-for="seal in seals">
                    <td>
                      {{seal}}
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </td>
    <td valign="top" rowspan="2">
      <div class="page-container" style="height: 100%">
        <div style="text-align: center">
          <fmt:message key="transportation.log"/>
        </div>
        <div style="width: 250pt; height: 398px;
        border: solid gray 1px; overflow-y: scroll;
        box-shadow: inset 0 0 4px 0 #8c8c8c; padding: 1pt; font-size: 10pt">
          <div v-for="log in logs">
            <span style="text-decoration: underline; color: cadetblue">
              {{new Date(log.date).toLocaleTimeString().substring(0, 5)}}&nbsp;
              {{new Date(log.date).toLocaleDateString().substring(0, 5)}}
            </span>
            <div style="margin-left: 2pt; font-weight: bold">
              {{log.message}}
            </div>
            <div style="padding-left: 24pt">
              <div v-for="change in log.changes">
                {{change.field}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <div class="page-container">
        <table border="0">
          <tr>
            <th colspan="3">
              <fmt:message key="transportation"/>
            </th>
          </tr>
          <tr>
            <th colspan="3" align="left">
              <fmt:message key="transportation.automobile"/>
            </th>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="transportation.automobile.model"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.vehicle.model}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="transportation.automobile.number"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.vehicle.number}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="transportation.automobile.trailer"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.vehicle.trailer.number}
            </td>
          </tr>
          <tr>
            <th colspan="3" align="left">
              <fmt:message key="transportation.driver"/>
            </th>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="person.surname"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.driver.person.surname}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="person.forename"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.driver.person.forename}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="person.patronymic"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.driver.person.patronymic}
            </td>
          </tr>

        </table>
        <table>
          <tr>
            <td>
              <fmt:message key="load.plan"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:formatNumber value ="${plan.amount}"/>
              <%--&nbsp;${plan.deal.unit.name}--%>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="load.fact"/>
            </td>
            <td>
              :
            </td>
            <td>
              <span v-if="weight.id">
                {{(weight.netto).toLocaleString()}}
                <%--&nbsp;${plan.deal.unit.name}--%>
              </span>
              <span v-else>
                0
              </span>
            </td>
          </tr>
        </table>
      </div>
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
