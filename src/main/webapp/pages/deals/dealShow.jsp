<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<script>
  var closeAction;
  function onClose(action){
    closeAction = action;
  }
  function closeShow(){
    closeAction();
  }

</script>

<table border="0">
  <tr>
    <td valign="top">
      <table border="0">
        <tr>
          <td>
            <fmt:message key="deal.type"/>:
          </td>
          <td>
            <fmt:message key="${deal.type}"/>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="period"/>:
          </td>
          <td>
            <fmt:formatDate value="${deal.date}" pattern="dd.MM.yyyy"/>
            <c:if test="${deal.date ne deal.dateTo}">
              -<fmt:formatDate value="${deal.dateTo}" pattern="dd.MM.yyyy"/>
            </c:if>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.organisation"/>:
          </td>
          <td>
            ${deal.organisation.value}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.realisation"/>:
          </td>
          <td>
            ${deal.documentOrganisation.value}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.product"/>
          </td>
          <td>
            ${deal.product.name}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.quantity"/>
          </td>
          <td>
            <fmt:formatNumber value="${deal.quantity}"/>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.price"/>
          </td>
          <td>
            <fmt:formatNumber value="${deal.price}"/>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <button><fmt:message key="button.edit"/> </button>
          </td>
        </tr>
      </table>
    </td>
    <td>
      <link rel="stylesheet" href="${context}/css/LoadPlan.css">
      <script src="${context}/vue/loadPlan.js"></script>
      <script>
        <c:forEach items="${customers}" var="customer">
        plan.addCustomer('${customer}', '<fmt:message key="${customer}"/> ')
        </c:forEach>
        plan.deal = ${deal.id}
        plan.api.save_link = '${save_link}'
        plan.api.updateAPI = '${updateAPI}'
        plan.api.findVehicleAPI = '${findVehicleAPI}'
        plan.api.findDriverAPI = '${findDriverAPI}'
        plan.api.editVehicleAPI = '${editVehicle}'
        plan.api.editDriverAPI = '${editDriver}'
      </script>
      <div class="plan-wrapper" id="load_plan">
        <table border="0">
          <tr>
            <td colspan="4" align="center">
              <fmt:message key="load.plan"/>
              <button v-on:click="newPlan"><fmt:message key="button.add"/> </button>
            </td>
          </tr>
          <tr>
            <td>
              <c:set var="dropTitle"><fmt:message key="load.plan.drop.title"/> </c:set>
              <c:set var="dateTitle"><fmt:message key="load.date.title"/> </c:set>
            <span title="${dateTitle}" class="table-header" style="width: 8em">
              <fmt:message key="date"/>
            </span>
            </td>
            <td>
              <c:set var="planTitle"><fmt:message key="load.plan.title"/> </c:set>
            <span title="${planTitle}" class="table-header" style="width: 6em">
              <fmt:message key="deal.plan"/>
            </span>
            </td>
            <td>
              <c:set var="customerTitle"><fmt:message key="load.customer.title"/> </c:set>
            <span title="${customerTitle}" class="table-header" style="width: 8em">
              <fmt:message key="transport.customer"/>
            </span>
            </td>
            <td>
              <c:set var="factTitle"><fmt:message key="load.fact.title"/> </c:set>
            <span title="${factTitle}" class="table-header" style="width: 6em">
              <fmt:message key="fact"/>
            </span>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <div class="plan-container">
                <div v-for="(value, key) in plans" class="plan-item">
                  <div class="upper">
                    <span title="${dropTitle}" class="mini-close" style="left: 0">&times;</span>
                    <input v-model="new Date(value.item.date).toLocaleDateString()" id="date" name="date" title="${dateTitle}" readonly style="width: 7em">
                    <input v-model="value.item.plan" type="number" title="${planTitle}" style="width: 6em" min="1">
                    <select v-model="value.item.customer" title="${customerTitle}">
                      <option v-for="customer in customers" v-bind:value="customer.id">{{customer.name}}</option>
                    </select>
                    <span title="${factTitle}">{{value.fact}}</span>
                  </div>
                  <div class="lower">
                    <template v-if="value.item.transportation.vehicle.id">
                      {{value.item.transportation.vehicle.model}}
                      '{{value.item.transportation.vehicle.number}}'
                      <template v-if="value.item.transportation.vehicle.trailer">
                        '{{value.item.transportation.vehicle.trailer}}'
                      </template>
                    </template>
                    <template v-else-if="value.editVehicle">
                      <div style="display: inline-block">
                        <input v-on:keyup="findVehicle(value.vehicleInput)" v-model="value.vehicleInput">
                        <div class="custom-data-list">
                          <div v-for="vehicle in findVehicles" class="custom-data-list-item">
                            {{vehicle.model}}
                            '{{vehicle.number}}'
                            <template v-if="vehicle.trailer">
                              '{{vehicle.trailer}}'
                            </template>
                          </div>
                        </div>
                      </div>
                      <span v-on:click="closeVehicleInput(key)" class="mini-close">&times;</span>
                    </template>
                    <button v-else v-on:click="openVehicleInput(value.item.id)">
                      <fmt:message key="transportation.automobile"/>...
                    </button>
                    <span v-if="value.item.transportation.driver.id">
                      {{value.item.transportation.driver.person.value}}
                    </span>
                    <button v-else>
                      <fmt:message key="transportation.driver"/>...
                    </button>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <fmt:message key="totle.plan"/>:
              {{totalPlan().toLocaleString()}}/${deal.quantity}
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <fmt:message key="totle.fact"/>:
              <span id="total_fact">{{totalFact().toLocaleString()}}</span>
              /
              <span>${deal.quantity}</span>
            </td>
          </tr>
        </table>

      </div>
      <script>
        plan.loadPlan()
      </script>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeShow()"><fmt:message key="button.cancel"/> </button>

      <button onclick="plan.save();"><fmt:message key="button.save"/> </button>
    </td>
  </tr>
</table>

</html>
