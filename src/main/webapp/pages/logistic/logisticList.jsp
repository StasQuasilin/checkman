<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<link rel="stylesheet" href="${context}/css/LogisticList.css">
  <script src="${context}/vue/logisticList.vue"></script>
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
    logistic.api.saveNote = '${saveNote}';
    logistic.api.removeNote = '${removeNote}';
    logistic.api.vehicleDriverInput = '${vehicleDriverInput}';
    logistic.api.changeDate = '${changeDate}'
    logistic.api.update = '${update}';
    logistic.api.save = '${save}';
    logistic.loadItems();
    logistic.worker={
      id:${worker.id},
      value:'${worker.person.value}'
    }
    function stopContent(){
      logistic.stop()
    }
  </script>
<c:set var="dateLeft"><fmt:message key="date.left"/></c:set>
<c:set var="dateRight"><fmt:message key="date.right"/></c:set>
  <div id="logistic">
    <transition-group name="flip-list" tag="div" class="container">
      <div v-for="(value, key) in filteredItems()" class="container-item" :class="rowName(value.item.date)"
           :key="value.item.id" :id="value.item.id">
        <div class="upper-row">
        <span class="date-container">
          <span title="${dateLeft}" class="arrow" v-on:click="changeDate(key, -1)">&#9664;</span>
          <span>
            {{new Date(value.item.date).toLocaleDateString()}}
          </span>
          <span title="${dateRight}" class="arrow" v-on:click="changeDate(key, 1)">&#9654;</span>
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
            {{value.item.product.name}},
          </b>
        </span>
        <span>
          <fmt:message key="deal.quantity"/>:
          <b>
            {{(value.item.quantity).toLocaleString()}}
            {{value.item.unit}},
          </b>
        </span>
        <span>
          <fmt:message key="deal.from"/>:
          <b>
            {{value.item.realisation}}
          </b>
        </span>
        </div>
        <div class="middle-row" style="font-size: 10pt">
          <div style="display: inline-block; ">
            <div>
              <fmt:message key="transportation.time.in"/>:
              <b v-if="value.item.transportation.timeIn.id">
                {{new Date(value.item.transportation.timeIn.time).toLocaleTimeString().substring(0, 5)}}
              </b>
            <span v-else>
              --:--
            </span>
            </div>
            <div>
              <fmt:message key="transportation.time.out"/>:
              <b v-if="value.item.transportation.timeOut.id">
                {{new Date(value.item.transportation.timeOut.time).toLocaleTimeString().substring(0, 5)}}
              </b>
            <span v-else>
              --:--
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
                    <span v-on:click="deleteVehicle(value.item.transportation.id)">
                      <fmt:message key="button.cancel"/>
                    </span>
                  </div>
                </span>
              </span>
                <template v-else-if="value.vehicleEdit">
                  <div style="display: inline-block;">
                    <input v-model="value.vehicleInput" style="width: 100%; border: none" v-on:keyup="findVehicle(value)"
                           v-on:keyup.enter="parseVehicle(value)" onclick="this.select()" >
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
                    <span v-on:click="parseDriver(value)">
                      <fmt:message key="edit"/>
                    </span>
                    <span v-on:click="deleteDriver(value.item.transportation.id)">
                      <fmt:message key="button.cancel"/>
                    </span>
                  </div>
                </span>
              </span>
            <template v-else-if="value.driverEdit">
              <div style="display: inline-block; width: 85%">
                <input v-model="value.driverInput" style="width: 100%; border: none" v-on:keyup="findDriver(value)"
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
          <div v-if="value.item.transportation.notes.length == 0"
               style="display: inline-block">
            <a class="mini-close" v-on:click="openNote(value.item.id, true)">
              +<fmt:message key="note.add"/>
            </a>
          </div>
        </div>
        <div v-if="value.item.transportation.notes.length > 0 || value.editNote"
             class="lower-row">
          <div v-if="value.editNote" style="display: inline-block">
            <span v-on:click="openNote(value.item.id, false)" class="mini-close">&times;</span>
            <input v-model="value.noteInput"
                   v-on:keyup.escape="openNote(value.item.id, false)"
                v-on:keyup.enter="saveNote(value.item.id)">
          </div>
          <div v-else style="display: inline-block">
            <a class="mini-close" v-on:click="openNote(value.item.id, true)">
              +<fmt:message key="note.add"/>
            </a>
          </div>
          <div v-for="note in value.item.transportation.notes"
               style="display: inline-block; padding: 0 6pt">
            <span v-if="worker.id===note.creator.id">
              <span class="mini-close" v-on:click="removeNote(note.id)" style="padding: 0">
                &times;
              </span>
              <span style="padding: 0">
                <fmt:message key="you"/>
              </span>

            </span>
            <span v-else>
            {{note.creator.person.value}}
            </span>
            :{{note.note}}
          </div>
        </div>
      </div>
    </transition-group>

  </div>
</html>
