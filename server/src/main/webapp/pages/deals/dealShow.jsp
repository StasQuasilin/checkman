<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/LoadPlan.css">
<script src="${context}/vue/loadPlan.vue"></script>
<script>
  <c:forEach items="${plans}" var="plan">

  plan.add({
    id:${plan.id},
    date: new Date('${plan.date}').toISOString().substring(0, 10),
    plan:${plan.plan},
    customer:'${plan.customer}',
    transportation:{
      <c:choose>
      <c:when test="${not empty plan.transportation.vehicle.id}">
      vehicle:{
        id:${plan.transportation.vehicle.id},
        model:'${plan.transportation.vehicle.model}',
        number:'${plan.transportation.vehicle.number}',
        trailer:'${plan.transportation.vehicle.trailer}'
      },
      </c:when>
      <c:otherwise>
      vehicle:{
        id:-1
      },
      </c:otherwise>
      </c:choose>
      <c:choose>
      <c:when test="${not empty plan.transportation.driver.id}">
      driver:{
        id:${plan.transportation.driver.id},
        person:{
          value:'${plan.transportation.driver.person.value}'
        }
      },
      </c:when>
      <c:otherwise>
      driver:{
        id:-1
      },
      </c:otherwise>
      </c:choose>
      notes:[
        <c:forEach items="${plan.transportation.notes}" var="note">
        {
          id:${note.id},
          creator:{
            id:${note.creator.id},
            person:{
              value:'${note.creator.person.value}'
            }
          },
          note:'${note.note}'
        },
        </c:forEach>
      ],
      weight:{}
    }
  });
  </c:forEach>
  <c:forEach items="${customers}" var="customer">
  plan.customers.push({
    id:'${customer}',
    value:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
  plan.deal = ${deal.id};
  plan.dateFrom = new Date('${deal.date}');
  plan.dateTo = new Date('${deal.dateTo}');
  plan.quantity = ${deal.quantity};
  plan.unit = "${deal.unit.name}";
  plan.api.save = '${save}';
  plan.api.remove = '${remove}';

  plan.api.findVehicle = '${findVehicle}';
  plan.api.parseVehicle = '${parseVehicle}';
  plan.api.editVehicle = '${editVehicle}';

  plan.api.findDriver = '${findDriver}';
  plan.api.parseDriver = '${parseDriver}';
  plan.api.editDriver = '${editDriver}';

  plan.worker = {
    id:${worker.id},
    value:'${worker.value}'
  }

</script>
<c:set var="vehicleHolder"><fmt:message key="transportation.automobile"/>... </c:set>
<c:set var="driverHolder"><fmt:message key="transportation.driver"/>... </c:set>
<c:set var="editVehicle"><fmt:message key="vehicle.edit"/></c:set>
<c:set var="editDriver"><fmt:message key="driver.edit"/></c:set>
<c:set var="cancel"><fmt:message key="button.cancel"/></c:set>
  <table border="0" style="height: 100%" id="load_plan">
    <tr>
      <td valign="top">
        <table border="0" style="height: 100%; width: 340px">
          <tr>
            <td>
              <fmt:message key="deal.type"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:message key="${deal.type}"/>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="period"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:formatDate value="${deal.date}" pattern="dd.MM.yyyy"/>
              <c:if test="${deal.date ne deal.dateTo}">
                -
                <fmt:formatDate value="${deal.dateTo}" pattern="dd.MM.yyyy"/>
              </c:if>
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
              ${deal.organisation.value}
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.realisation"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${deal.shipper.value}
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
              ${deal.product.name}
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
              <fmt:formatNumber value="${deal.quantity}"/>&nbsp;${deal.unit.name}
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
              <fmt:formatNumber value="${deal.price}"/>
            </td>
          </tr>
          <tr>
            <td colspan="3" height="100%" valign="top">
              <div style="height: 100%; width: 100%; overflow-y: scroll; padding: 2pt">
                <p v-for="msg in messages()">
                  {{msg}}
                </p>
              </div>
            </td>
          </tr>
        </table>
      </td>
      <td>
        <div class="plan-wrapper">
          <table border="0">
            <tr>
              <td colspan="4" align="center">
                <fmt:message key="load.plans"/>
                <button v-on:click="newVehicle()"><fmt:message key="button.add.vehicle"/> </button>
                <%--<button v-on:click="newCarriage()"><fmt:message key="button.add.carriage"/> </button>--%>
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
                    <div v-if="!value.removed">
                      <%--UPPER ROW--%>
                      <div class="upper">
                        <%--REMOVE BUTTON--%>
                        <div style="display: inline-block; width: 10pt">
                          <span title="${dropTitle}" class="mini-close" style="left: 0"
                                v-show="!value.item.transportation.archive" v-on:click="remove(key)">&times;</span>
                          <span v-show="value.item.transportation.archive" style="color: green">
                            &#10003;
                          </span>
                        </div>
                        <%--DATE INPUT--%>
                        <input readonly style="width: 7em"
                               v-model="new Date(value.item.date).toLocaleDateString()"
                               v-on:click="dateTimePicker(key)">
                        <%--PLAN INPUT--%>
                          <span style="position: relative">
                            <input v-model="value.item.plan" onfocus="this.select()" type="number" v-on:change="initSaveTimer(value.key)"
                                   title="${planTitle}" style="width: 6em; text-align: right;" min="1">
                            <span style="position: absolute; top: 0; right: 12px">
                              {{unit}}
                            </span>
                          </span>
                        <%--CUSTOMER INPUT--%>
                        <select v-model="value.item.customer" title="${customerTitle}" v-on:change="initSaveTimer(value.key)">
                          <option v-for="customer in customers" :value="customer.id">{{customer.value}}</option>
                        </select>
                        <span class="fact-info">
                          <span title="${factTitle}" style="padding: 2pt">
                            <span v-if="value.item.transportation.weight.id">
                              <b>
                                {{value.item.transportation.weight.netto.toLocaleString()}}&nbsp;{{unit}}
                              </b>
                              <span>
                                {{different(value.item.transportation.weight.netto, value.item.plan)}}
                              </span>
                            </span>
                          </span>
                          <div class="fact-details" v-if="value.item.transportation.weight.id">
                            <table>
                              <tr>
                                <td>
                                  <fmt:message key="weight.brutto"/>
                                </td>
                                <td>
                                  :
                                </td>
                                <td>
                                  {{(value.item.transportation.weight.brutto).toLocaleString()}}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <fmt:message key="weight.tara"/>
                                </td>
                                <td>
                                  :
                                </td>
                                <td>
                                  {{(value.item.transportation.weight.tara).toLocaleString()}}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <fmt:message key="weight.netto"/>
                                </td>
                                <td>
                                  :
                                </td>

                                <td>
                                  {{(value.item.transportation.weight.netto).toLocaleString()}}
                                </td>
                              </tr>
                            </table>
                          </div>
                        </span>
                      </div>
                        <%--LOWER ROW--%>
                      <div class="lower">
                        <table style="font-size: 10pt; width: 100%" border="0">
                          <tr>
                            <td>
                              <span><fmt:message key="transportation.automobile"/></span>
                            </td>
                            <td>
                              :
                            </td>
                            <td width="100%">
                              <%--VEHICLE STUFF--%>
                              <div>
                                <div style="display: inline-block"
                                     v-if="value.item.transportation.vehicle.id > -1 && !(value.editVehicle)"
                                     v-on:click.right="">
                                  {{value.item.transportation.vehicle.model}}
                                  '{{value.item.transportation.vehicle.number}}'
                                  <template v-if="value.item.transportation.vehicle.trailer">
                                    '{{value.item.transportation.vehicle.trailer}}'
                                  </template>
                                  <span title="${editVehicle}" v-on:click="editVehicle(key)" class="mini-close flipY" style="padding: 0">
                                    &#9998;
                                  </span>
                                  <span title="${cancel}" v-on:click="cancelVehicle(key)" class="mini-close" style="padding: 0">
                                    &times;
                                  </span>
                                </div>
                                <div v-else-if="value.editVehicle">
                                  <div style="display: inline-block" v-on:blur="parseVehicle(value.vehicleInput, key)">
                                    <%--VEHICLE INPUT--%>
                                    <input v-on:keyup="findVehicle(value.vehicleInput)" id="input"
                                           v-on:keyup.enter="parseVehicle(value.vehicleInput, key)"
                                           v-on:keyup.escape="closeVehicleInput(key)"
                                           v-model="value.vehicleInput" autofocus
                                           placeholder="${vehicleHolder}" style="border: none">
                                    <div class="custom-data-list">
                                      <div v-for="vehicle in foundVehicles" class="custom-data-list-item"
                                           v-on:click="setVehicle(vehicle, key)">
                                        {{vehicle.model}}
                                        '{{vehicle.number}}'
                                        <template v-if="vehicle.trailer">
                                          '{{vehicle.trailer}}'
                                        </template>
                                      </div>
                                    </div>
                                  </div>
                                  <span v-on:click="closeVehicleInput(key)" class="mini-close" style="left: -22pt">&times;</span>
                                </div>
                                <a v-else v-on:click="openVehicleInput(value.key)">
                                  <fmt:message key="transportation.automobile.insert.info"/>
                                </a>
                              </div>
                            </td>
                            <td rowspan="2">
                              <a class="mini-close" v-on:click="addNote(value.key)">
                                +<fmt:message key="note.add"/>
                              </a>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <span><fmt:message key="transportation.driver"/></span>
                            </td>
                            <td>
                              :
                            </td>
                            <td>
                              <%--DRIVER STUFF--%>
                              <span v-if="value.item.transportation.driver.id > -1 && !value.editDriver">
                                {{value.item.transportation.driver.person.value}}
                                <span title="${editDriver}" v-on:click="editDriver(key)" class="mini-close flipY" style="padding: 0">
                                    &#9998;
                                  </span>
                                  <span title="${cancel}" v-on:click="cancelDriver(key)" class="mini-close" style="padding: 0">
                                    &times;
                                  </span>
                              </span>

                              <template v-else-if="value.editDriver">
                                <div style="display: inline-block" v-on:blur="parseDriver(value.driverInput, key)">
                                  <%--DRIVER INPUT--%>
                                  <input v-on:keyup="findDriver(value.driverInput)" id="input"
                                         v-on:keyup.escape="closeDriverInput(key)"
                                         v-on:keyup.enter="parseDriver(value.driverInput, key)"
                                         v-model="value.driverInput" autofocus
                                         placeholder="${driverHolder}" style="border:none;">
                                  <div class="custom-data-list">
                                    <div v-for="driver in foundDrivers" class="custom-data-list-item" v-on:click="setDriver(driver, key)">
                                      {{driver.person.value}}
                                    </div>
                                  </div>
                                </div>
                                <span v-on:click="closeDriverInput(key)" class="mini-close" style="left: -22pt">&times;</span>
                              </template>
                              <a v-else v-on:click="openDriverInput(value.key)">
                                <fmt:message key="transportation.driver.insert.info"/>
                              </a>

                            </td>

                          </tr>
                        </table>
                      </div>
                      <div class="lower">
                        <span v-if="value.editNote">
<%--suppress XmlDuplicatedId --%>
                          <input id="input"
                                 v-on:keyup.enter="saveNote(key)"
                                 v-on:blur="saveNote(key)"
                                 v-on:keyup.escape="closeNote(value.key)"
                                 v-model="value.noteInput"
                                 style="width: 100%; background: white; border: none">
                        </span>
                        <div style="flex-wrap: wrap">
                          <div v-for="note in value.item.transportation.notes" style="display: inline-flex;">
                            <span v-if="note.creator.id !== worker.id">{{note.creator.value}}</span>
                            <span v-else>
                              <span v-on:click="removeNote(key, note.id)" class="mini-close">&times;</span>
                              <fmt:message key="you"/>
                            </span>:{{note.note}}
                          </div>
                        </div>
                      </div>
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
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button onclick="closeModal()"><fmt:message key="button.close"/> </button>
      </td>
    </tr>
  </table>
</html>
