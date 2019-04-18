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
<link rel="stylesheet" href="${context}/css/LogisticList.css">
  <script src="${context}/vue/logisticList.js"></script>
  <script>
    <c:forEach items="${dealTypes}" var="type">
    logistic.addType('${type}', '<fmt:message key="_${type}"/>');
    </c:forEach>
    logistic.api.saveTransportationVehicleApi = '${saveTransportationVehicleAPI}';
    logistic.api.saveTransportationDriverApi = '${saveTransportationDriverAPI}';
    logistic.api.findVehicleAPI = '${findVehicleApi}';
    logistic.api.findDriverAPI = '${findDriverApi}';
    logistic.api.vehicleInput = '${vehicleInput}';
    logistic.api.driverInput = '${driverInput}';
    logistic.api.vehicleDriverInput = '${vehicleDriverInput}';
    logistic.api.update = '${update}';
    logistic.api.save = '${save}';
    logistic.loadItems();

    function stopContent(){
      logistic.stop()
    }
  </script>
  <div id="logistic">
    <div v-for="(value, key) in filteredItems()" class="container-item" v-bind:class="rowName(value.item.date)">
      <div class="upper-row">
        <span>
          {{new Date(value.item.date).toLocaleDateString()}}
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
            {{(types[value.item.type]).toLowerCase()}}
            {{value.item.product.name}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.quantity"/>:
          <b>
            {{(value.item.quantity).toLocaleString()}}
            {{value.item.unit}}
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
            <b v-if="value.item.transportation.timeIn.id">
              {{new Date(value.item.transportation.timeIn.time).toLocaleTimeString()}}
            </b>
            <span v-else>
              --:--:--
            </span>
          </div>
          <div>
            <fmt:message key="transportation.time.out"/>:
            <b v-if="value.item.transportation.timeOut.id">
              {{new Date(value.item.transportation.timeOut.time).toLocaleTimeString()}}
            </b>
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
            <div class="content-item">
              <span v-if="value.item.transportation.vehicle.id">
                <b>{{value.item.transportation.vehicle.model}}</b>
                <span class="vehicle-number">
                {{value.item.transportation.vehicle.number}}
                </span>
                <span v-if="value.item.transportation.vehicle.trailer" class="vehicle-number">
                {{value.item.transportation.vehicle.trailer}}
                </span>
                <span class="edit-menu-header">
                  &#9660;
                  <div class="edit-menu">
                    <span v-on:click="parseVehicle(value)">
                      <fmt:message key="edit"/>
                    </span>
                    <span v-on:click="deleteVehicle(value)">
                      <fmt:message key="button.cancel"/>
                    </span>
                  </div>
                </span>
              </span>

              <template v-else-if="value.vehicleEdit">
                <div style="display: inline-block;">
                  <input v-model="value.vehicleInput" style="width: 100%" v-on:keyup="findVehicle(value)"
                         v-on:keyup.enter="parseVehicle(value)" onclick="this.select()">
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
                <span class="mini-close" v-on:click="closeVehicleInput(key)" style="left: -22px">&times;</span>
              </template>
              <a v-else v-on:click="openVehicleInput(value.item.id)">
                <fmt:message key="transport.insert.infortation"/>
              </a>
            </div>
          </div>
          <div>
            <span class="transport-label">
              <fmt:message key="transportation.driver"/>:
            </span>
            <div style="display: inline-block;">
          <span v-if="value.item.transportation.driver.id">
            <b>{{value.item.transportation.driver.person.value}}</b>
            <span class="edit-menu-header">
              &#9660;
              <div class="edit-menu">
                <span v-on:click="parseVehicle(value)">
                  <fmt:message key="edit"/>
                </span>
                <span v-on:click="deleteVehicle(value)">
                  <fmt:message key="button.cancel"/>
                </span>
              </div>
            </span>
          </span>
              <template v-else-if="value.driverEdit">
                <div style="display: inline-block; width: 85%">
                  <input v-model="value.driverInput" style="width: 100%" v-on:keyup="findDriver(value)"
                         v-on:keyup.enter="parseDriver(value)">
                  <div class="custom-data-list" v-show="driverFind">
                    <div class="custom-data-list-item" v-for="driver in driverFind"
                         v-on:click="setDriver(value.item.transportation.id, driver.id, key)">
                      {{driver.person.value}}
                    </div>
                  </div>
                </div>

                <span class="mini-close" v-on:click="closeDriverInput(key)" style="left: -22px;">&times;</span>
              </template>
              <a v-else v-on:click="openDriverInput(value.item.id)">
                <fmt:message key="transportation.driver.insert.info"/>
              </a>
            </div>

          </div>
        </div>
      </div>
    </div>
    <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
      <div v-bind:style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
        <div class="custom-data-list-item" style="font-weight: bold">{{menu.title}}</div>
        <div class="custom-data-list-item"><fmt:message key="transportation.new"/></div>
        <div class="custom-data-list-item"><fmt:message key="edit"/></div>
        <div class="custom-data-list-item"><fmt:message key="button.delete"/></div>
      </div>
    </div>
  </div>
</html>
