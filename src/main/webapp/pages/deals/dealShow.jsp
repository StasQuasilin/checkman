<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

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
      <link rel="stylesheet" href="${context}/css/date-picker.css">
      <script src="${context}/vue/loadPlan.js"></script>
      <script>
        <c:forEach items="${customers}" var="customer">
        plan.addCustomer('${customer}', '<fmt:message key="${customer}"/> ')
        </c:forEach>
        plan.deal = ${deal.id};
        plan.quantity = ${deal.quantity};

        plan.api.save_link = '${save_link}'
        plan.api.updateAPI = '${updateAPI}'
        plan.api.findVehicleAPI = '${findVehicleAPI}'
        plan.api.findDriverAPI = '${findDriverAPI}'
        plan.api.editVehicleAPI = '${editVehicle}'
        plan.api.editDriverAPI = '${editDriver}'
        addOnCloseEvent(function(){
          plan.stop();
        })
      </script>
      <div class="plan-wrapper" id="load_plan">
        <table border="0">
          <tr>
            <td colspan="4" align="center">
              <fmt:message key="load.plan"/>
              <button v-on:click="newPlan"><fmt:message key="button.add"/> </button>
            </td>
          </tr>
          <%--HEADER--%>
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
              <%--TABLE--%>
              <transition-group name="flip-list" tag="div" class="plan-container">
                <div v-for="(value, key) in plans" :key="value.key" class="plan-item">
                  <%--UPPER ROW--%>
                  <div class="upper">
                    <%--REMOVE BUTTON--%>
                    <div style="display: inline-block; width: 10pt">
                      <span title="${dropTitle}" class="mini-close" style="left: 0"
                            v-show="!value.item.transportation.archive" v-on:click="remove(key)">&times;</span>
                    </div>
                    <%--DATE INPUT--%>
                    <input readonly style="width: 7em"
                           v-model="new Date(value.item.date).toLocaleDateString()"
                           v-on:click="dateTimePicker(key)">
                    <%--PLAN INPUT--%>
                    <input v-model="value.item.plan" onclick="this.select()" type="number" title="${planTitle}" style="width: 6em" min="1">
                    <%--CUSTOMER INPUT--%>
                    <select v-model="value.item.customer" title="${customerTitle}">
                      <option v-for="customer in customers" v-bind:value="customer.id">{{customer.name}}</option>
                    </select>
                    <span title="${factTitle}">{{weighs(value.item.transportation.weights).toLocaleString()}}</span>
                  </div>
                    <%--LOWER ROW--%>
                  <div class="lower">
                    <%--VEHICLE STUFF--%>
                    <div style="display: inline-block" v-if="value.item.transportation.vehicle.id" v-on:click.right="">
                      {{value.item.transportation.vehicle.model}}
                      '{{value.item.transportation.vehicle.number}}'
                      <template v-if="value.item.transportation.vehicle.trailer">
                        '{{value.item.transportation.vehicle.trailer}}'
                      </template>
                    </div>
                    <template v-else-if="value.editVehicle">
                      <div style="display: inline-block">
                        <%--VEHICLE INPUT--%>
                        <input v-on:keyup="findVehicle(value.vehicleInput)" v-on:keyup.enter="editVehicle(value.vehicleInput, key)" v-model="value.vehicleInput">
                        <div class="custom-data-list">
                          <div v-for="vehicle in findVehicles" class="custom-data-list-item" v-on:click="setVehicle(vehicle, key)">
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
                      <%--DRIVER STUFF--%>
                    <span v-if="value.item.transportation.driver.id">
                      {{value.item.transportation.driver.person.value}}
                    </span>
                      <template v-else-if="value.editDriver">
                        <div style="display: inline-block">
                          <%--DRIVER INPUT--%>
                          <input v-on:keyup="findDriver(value.driverInput)" v-on:keyup.enter="editDriver(value.driverInput, key)" v-model="value.driverInput">
                          <div class="custom-data-list">
                            <div v-for="driver in findDrivers" class="custom-data-list-item" v-on:click="setDriver(driver, key)">
                              {{driver.person.value}}
                            </div>
                          </div>
                        </div>
                        <span v-on:click="closeDriverInput(key)" class="mini-close">&times;</span>
                      </template>
                    <button v-else v-on:click="openDriverInput(value.item.id)">
                      <fmt:message key="transportation.driver"/>...
                    </button>
                  </div>
                </div>
              </transition-group>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <fmt:message key="totle.plan"/>:
              {{totalPlan().toLocaleString()}}/{{(quantity).toLocaleString()}} ( {{(totalPlan() / quantity * 100).toLocaleString()}} % )
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <fmt:message key="totle.fact"/>:
              {{totalFact().toLocaleString()}}/{{(quantity).toLocaleString()}} ( {{(totalFact() / quantity * 100).toLocaleString()}} % )
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
      <button onclick="closeModal()"><fmt:message key="button.cancel"/> </button>

      <button onclick="plan.save();"><fmt:message key="button.save"/> </button>
    </td>
  </tr>
</table>

</html>
