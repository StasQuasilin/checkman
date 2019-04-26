<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/summary.js"></script>
<script>
  summary.api.update = '${update}';
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
              <fmt:message key="${plan.deal.type}"/>
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
              ${plan.deal.organisation.value}
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
              ${plan.deal.product.name}
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
              <fmt:formatNumber value="${plan.deal.quantity}"/>&nbsp;${plan.deal.unit.name}
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
              <fmt:formatNumber value="${plan.deal.price}"/>
            </td>
          </tr>
        </table>
      </div>
    </td>
    <td valign="top" rowspan="2">
      <div style="position: relative; top: -3px; overflow-y: scroll">
        <table border="0" style="border: 0">
          <tr>
            <td>
              <%--!--%>
              <%--!--%>
              <%--WEIGHT--%>
              <%--!--%>
              <%--!--%>
              <div class="page-container">
                <table width="100%">
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
            </td>
          </tr>
          <tr>
            <td>
              <%--!--%>
              <%--!--%>
              <%--ANALYSES--%>
              <%--!--%>
              <%--!--%>
              <div class="page-container" v-if="analyses.sun">
                <table>
                  <tr>
                    <th colspan="3">
                      <fmt:message key="analyses"/>
                    </th>
                  </tr>
                  <template v-for="sun in analyses.sun">
                    <tr>
                      <td>
                        <fmt:message key="sun.humidity"/>
                      </td>
                      <td>
                        :
                      </td>
                      <td>
                        {{(sun.humidity).toLocaleString()}}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <fmt:message key="sun.soreness"/>
                      </td>
                      <td>
                        :
                      </td>
                      <td>
                        {{(sun.soreness).toLocaleString()}}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <fmt:message key="sun.oiliness"/>
                      </td>
                      <td>
                        :
                      </td>
                      <td>
                        {{(sun.oiliness).toLocaleString()}}
                      </td>
                    </tr>
                  </template>
                </table>
              </div>
            </td>
          </tr>
          <tr>
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
        border: solid gray 1px;
        box-shadow: inset 0 0 4px 0 #8c8c8c; padding: 1pt; font-size: 10pt">
          <div v-for="log in logs">
            <span>
              {{new Date(log.date).toLocaleTimeString().substring(0, 5)}}&nbsp;
              {{new Date(log.date).toLocaleDateString().substring(0, 5)}}
            </span>
            <div style="margin-left: 2pt">
              {{log.message}}
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
              ${plan.transportation.vehicle.model}
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
              ${plan.transportation.vehicle.number}
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
              ${plan.transportation.vehicle.trailer}
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
              ${plan.transportation.driver.person.surname}
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
              ${plan.transportation.driver.person.forename}
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
              ${plan.transportation.driver.person.patronymic}
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
              <fmt:formatNumber value ="${plan.plan}"/>&nbsp;${plan.deal.unit.name}
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
              {{(weight.netto).toLocaleString()}}&nbsp;${plan.deal.unit.name}
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
