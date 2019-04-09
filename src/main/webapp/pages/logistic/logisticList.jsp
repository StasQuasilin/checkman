<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<style>
  button, input{
    border: none;
    font-size: 8pt;
    margin: 1pt;
  }
</style>
  <script src="${context}/vue/logisticList.js"></script>
  <script>
    logistic.saveTransportationVehicleApi = '${saveTransportationVehicleAPI}'
    logistic.saveTransportationDriverApi = '${saveTransportationDriverAPI}'
    logistic.findVehicleAPI = '${findVehicleApi}'
    logistic.findDriverAPI = '${findDriverApi}'
    logistic.vehicleInput = '${vehicleInput}'
    logistic.driverInput = '${driverInput}'
    logistic.vehicleDriverInput = '${vehicleDriverInput}'
    logistic.setUrls('${updateLink}', '${saveLink}')

    <c:forEach items="${dealTypes}" var="type">
    logistic.addType('${type}', '<fmt:message key="${type}"/>')
    </c:forEach>
    function stopContent(){
      logistic.stop()
    }
  </script>
  <div id="logistic">
    <div v-for="(value, key) in filter.filteredItems()" class="container-item" v-bind:class="value.className">
      <div class="upper-row">
        <span>
          {{new Date(value.item.date).toLocaleDateString()}}
        </span>
        <span>
          <b>
            {{types[value.item.type]}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.organisation"/>:
          <b>
            {{value.item.organisation.value}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.product"/>:
          <b>
            {{value.item.product.name}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.quantity"/>:
          <b>
            {{(value.item.quantity).toLocaleString()}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.from"/>:
          <b>
            {{value.item.realisation}}
          </b>
        </span>
      </div>
      <div class="lower-row" style="font-size: 10pt">
        <div style="display: inline-block; ">
          <div>
            <fmt:message key="transportation.time.in"/>:
            <span v-if="value.item.transportation.timeIn.id">
              {{new Date(value.item.transportation.timeIn.time).toLocaleTimeString()}}
            </span>
            <span v-else>
              --:--:--
            </span>
          </div>
          <div>
            <fmt:message key="transportation.time.out"/>:
            <span v-if="value.item.transportation.timeOut.id">
              {{new Date(value.item.transportation.timeOut.time).toLocaleTimeString()}}
            </span>
            <span v-else>
              --:--:--
            </span>
          </div>
        </div>

        <div style="display: inline-block">
          <div>
            <span class="transport-label">
              <fmt:message key="transportation.automobile"/>:
            </span>
            <div style="display: inline-block;">
              <template v-if="value.item.transportation.vehicle.id">
                {{value.item.transportation.vehicle.model}}
            <span class="vehicle-number">
              {{value.item.transportation.vehicle.number}}
            </span>
            <span v-if="value.item.transportation.vehicle.trailer" class="vehicle-number">
              {{value.item.transportation.vehicle.trailer}}
            </span>
              </template>
              <template v-else-if="value.vehicleEdit">
                <div style="display: inline-block;">
                  <input v-model="value.vehicleInput" style="width: 100%" v-on:keyup="findVehicle(value)"
                         v-on:keyup.enter="parseVehicle(value)">
                  <div class="custom-data-list">
                    <div class="custom-data-list-item" v-for="vehicle in vehicleFind"
                         v-on:click="setVehicle(value.item.transportation.id, vehicle.id, key)">
                      {{vehicle.model}}
                  <span>
                    '{{vehicle.number}}'
                  </span>
                  <span v-if="vehicle.trailer">
                    '{{vehicle.trailer}}'
                  </span>
                    </div>
                  </div>
                </div>
                <span class="mini-close" v-on:click="closeVehicleInput(key)">&times;</span>
              </template>
              <button v-else v-on:click="openVehicleInput(value.item.id)">
                <fmt:message key="transport.insert.infortation"/>
              </button>
            </div>
          </div>
          <div>
            <span class="transport-label">
              <fmt:message key="transportation.driver"/>:
            </span>
            <div style="display: inline-block;">
          <span v-if="value.item.transportation.driver.id">
            {{value.item.transportation.driver.person.value}}
          </span>
              <template v-else-if="value.driverEdit">
                <div style="display: inline-block; width: 90%">
                  <input v-model="value.driverInput" style="width: 100%" v-on:keyup="findDriver(value)"
                         v-on:keyup.enter="parseDriver(value)">
                  <div class="custom-data-list" v-show="driverFind">
                    <div class="custom-data-list-item" v-for="driver in driverFind"
                         v-on:click="setDriver(value.item.transportation.id, driver.id, key)">
                      {{driver.person.value}}
                    </div>
                  </div>
                </div>

                <span class="mini-close" v-on:click="closeDriverInput(key)">&times;</span>
              </template>
              <button v-else v-on:click="openDriverInput(value.item.id)">
                <fmt:message key="transportation.driver.insert.info"/>
              </button>
            </div>

          </div>
        </div>




      </div>
    </div>
  </div>
</html>
