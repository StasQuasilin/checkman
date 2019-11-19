<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<link rel="stylesheet" href="${context}/css/LogisticList.css">
  <script src="${context}/vue/logisticList.vue"></script>
  <script>
    <c:forEach items="${dealTypes}" var="type">
    list.types['${type}'] = '<fmt:message key="_${type}"/>';
    </c:forEach>
    list.api.saveVehicle = '${saveVehicle}';
    list.api.saveDriver = '${saveDriver}';
    list.api.parseVehicle = '${parseVehicle}';
    list.api.parseDriver = '${parseDriver}';
    list.api.findVehicle = '${findVehicle}';
    list.api.findDriver = '${findDriver}';
    list.api.editVehicle = '${editVehicle}';
    list.api.editDriver = '${editDriver}';
    list.api.vehicleInput = '${vehicleInput}';
    list.api.driverInput = '${driverInput}';
    list.api.saveNote = '${saveNote}';
    list.api.removeNote = '${removeNote}';
    list.api.vehicleDriverInput = '${vehicleDriverInput}';
    list.api.changeDate = '${changeDate}'
    list.api.update = '${update}';
    list.api.save = '${save}';
    list.customers=[];
    list.customers['szpt'] = 'СЗПТ';
    list.customers['contragent'] = 'Контрагент';
    list.worker={
      id:${worker.id},
      value:'${worker.person.value}'
    }
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
      list.handler(a);
    });
    </c:forEach>
    stopContent = function(){
      <c:forEach items="${subscribe}" var="s">
      unSubscribe('${s}');
      </c:forEach>
    }
  </script>
<style>
  .copypable *{
    user-select: text;
  }
</style>
<div id="container-header" class="container-header">
  <c:if test="${not empty add}">
    <button onclick="loadModal('${add}')"><fmt:message key="button.add"/> </button>
  </c:if>
</div>

<c:set var="dateLeft"><fmt:message key="date.left"/></c:set>
<c:set var="dateRight"><fmt:message key="date.right"/></c:set>
<c:set var="plan"><fmt:message key="load.plan"/></c:set>
  <div id="logistic">
    <transition-group name="flip-list" tag="div" class="container">
      <div v-for="(value, key) in getItems()" class="container-item"
           :class="['container-item-' + new Date(value.item.date).getDay(),
           { 'logistic-loading': value.item.timeIn.id && !value.item.timeOut.id }]"
           :key="value.item.id" :id="value.item.id" v-on:click.right="contextMenu(value.item)">
        <div class="upper-row" style="font-size: 11pt">
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
            {{value.item.product.name}},
          </b>
        </span>
        <span>
          ${fn:substring(plan, 0, 4)}:
          <b>
            {{(value.item.plan)}},
          </b>
        </span>
        <span>
          <fmt:message key="deal.from"/>:
          <b>
            {{value.item.shipper}}
          </b>
        </span>
        </div>
        <div class="middle-row" style="font-size: 10pt">
          <div style="display: inline-block; ">
            <div>
              <fmt:message key="transportation.time.in"/>:
              <b v-if="value.item.timeIn.id">
                {{new Date(value.item.timeIn.time).toLocaleTimeString().substring(0, 5)}}
              </b>
            <span v-else>
              --:--
            </span>
            </div>
            <div>
              <fmt:message key="transportation.time.out"/>:
              <b v-if="value.item.timeOut.id">
                {{new Date(value.item.timeOut.time).toLocaleTimeString().substring(0, 5)}}
              </b>
            <span v-else>
              --:--
            </span>
            </div>
          </div>
          <div style="display: inline-block; width: 280pt">
            <div>
            <span class="transport-label">
              <fmt:message key="transportation.automobile"/>:
            </span>
              <div class="content-item" style="width: 80%">
              <span v-if="value.item.vehicle.id">
                <b>{{value.item.vehicle.model}}</b>
                <span class="vehicle-number">
                {{value.item.vehicle.number}}
                </span>
                <span v-if="value.item.vehicle.trailer" class="vehicle-number">
                {{value.item.vehicle.trailer}}
                </span>
                <span class="edit-menu-header">
                  &#9660;
                  <div class="edit-menu">
                    <span v-on:click="editVehicle(value)">
                      <fmt:message key="edit"/>
                    </span>
                    <span v-on:click="deleteVehicle(value.item.id)">
                      <fmt:message key="button.cancel"/>
                    </span>
                  </div>
                </span>
              </span>
                <template v-else-if="value.editVehicle">
                  <div style="display: inline-block; width: 90%">
                    <input v-model="value.vehicleInput" style="width: 100%; border: solid paleturquoise 1pt"
                           v-on:keyup="findVehicle(value)"
                           v-on:keyup.enter="parseVehicle(value)" onfocus="this.select()" >
                    <div class="custom-data-list">
                      <div class="custom-data-list-item" v-for="vehicle in foundVehicles"
                           v-on:click="setVehicle(value.item.id, vehicle.id, value)">
                        {{vehicle.model}}
                      <span>
                        {{vehicle.number}}
                      </span>
                      <span v-if="vehicle.trailer">
                        {{vehicle.trailer}}
                      </span>
                      </div>
                    </div>
                  </div>
                  <span class="mini-close" v-on:click="closeVehicleInput(key)" style="left: -24px">&times;</span>
                </template>
                <a v-else v-on:click="openVehicleInput(value.item.id)">
                  <fmt:message key="transport.insert.infortation"/>
                </a>
              </div>
            </div>
            <div style="padding: 2pt 0; width: 100%" >
              <span class="transport-label">
                <fmt:message key="transportation.driver"/>:
              </span>
              <div style="display: inline-block; width: 80%">
                <span v-if="value.item.driver.id">
                  <b>
                    {{value.item.driver.person.surname}}
                    {{value.item.driver.person.forename}}
                    {{value.item.driver.person.patronymic}}
                  </b>
                  <span class="edit-menu-header">
                    &#9660;
                    <div class="edit-menu">
                      <span v-on:click="editDriver(value)">
                        <fmt:message key="edit"/>
                      </span>
                      <span v-on:click="deleteDriver(value.item.id)">
                        <fmt:message key="button.cancel"/>
                      </span>
                    </div>
                  </span>
                </span>
                <template v-else-if="value.editDriver">
                  <div style="display: inline-block; width: 90%">
                    <input v-model="value.driverInput" style="width: 100%; border: solid paleturquoise 1pt"
                           v-on:keyup="findDriver(value)"
                           v-on:keyup.enter="parseDriver(value)">
                    <div class="custom-data-list" v-show="foundDrivers">
                      <div class="custom-data-list-item" v-for="driver in foundDrivers"
                           v-on:click="setDriver(value.item.id, driver.id, value)">
                        {{driver.person.value}}
                      </div>
                    </div>
                  </div>
                  <span class="mini-close" v-on:click="closeDriverInput(key)" style="left: -24px;">&times;</span>
                </template>
                <a v-else v-on:click="openDriverInput(value.item.id)">
                  <fmt:message key="transportation.driver.insert.info"/>
                </a>
              </div>
            </div>
            <div style="display: inline-block; font-size: 10pt" v-if="value.item.driver.license">
              <fmt:message key="driver.license"/>:
              <b>
                {{value.item.driver.license}}
              </b>
            </div>
          </div>
          <div style="display: inline-block; font-size: 10pt">
            <div>
              <fmt:message key="transport.customer"/>:
            </div>
            <div style="font-size: 12pt; font-weight: bold; width: 100%; text-align: center">
              {{customers[value.item.customer]}}
            </div>
          </div>
          <div style="display: inline-block">
            <div v-if="value.item.weight.id" class="copypable">
              <div>
                <b>
                  Б: {{value.item.weight.brutto}},
                </b>
              </div>
              <div>
                <b>
                  Т: {{value.item.weight.tara}},
                </b>
              </div>
              <div>
                <b>
                  Н: {{value.item.weight.brutto > 0 &&
                  value.item.weight.tara > 0 ?
                  (value.item.weight.brutto - value.item.weight.tara).toLocaleString() : 0}}
                </b>
              </div>
            </div>
          </div>
          <div v-if="value.item.notes.length == 0"
               style="display: inline-block">
            <a class="mini-close" v-on:click="openNote(value.item.id, true)">
              +<fmt:message key="note.add"/>
            </a>
          </div>
        </div>
        <div v-if="value.item.notes.length > 0 || value.editNote"
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
          <div v-for="note in value.item.notes"
               style="display: inline-block; padding: 0 6pt">
            <span v-if="worker.id===note.creator.id">
              <span class="mini-close" v-on:click="removeNote(note.id)" style="padding: 0">
                &times;
              </span>
              <span style="padding: 0">
                <fmt:message key="you"/>:
              </span>
            </span>
            <span v-else-if="note.creator.person">
              {{note.creator.person.value}}:
            </span>
            <i>
              {{note.note}}
            </i>
          </div>
        </div>
      </div>
    </transition-group>
    <c:if test="${(haveMenu eq null) || (haveMenu)}">
      <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
        <div ref="contextMenu" :style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
          <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${add}')"><fmt:message key="menu.edit"/> </div>
          <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${add}')"><fmt:message key="menu.copy"/></div>

          <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${cancel}')">
            <span v-if="menu.any">
              <fmt:message key="menu.archive"/>
            </span>
            <span v-else>
              <fmt:message key="menu.delete"/>
            </span>
          </div>
        </div>
      </div>
    </c:if>
  </div>

</html>
<jsp:include page="../summary/staticCalendar.jsp"/>
