<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/LoadPlan.css">
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/loadPlan.vue"></script>
<script>
  <c:forEach items="${transportations}" var="transportation">
  plan.add(${transportation.toJson()});
  </c:forEach>
  <c:forEach items="${customers}" var="customer">
  plan.customers.push({
    id:'${customer}',
    value:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
  Vue.set(plan.tabNames, 'vehicles', '<fmt:message key="deal.tab.vehicles"/>');
  Vue.set(plan.tabNames, 'rails', '<fmt:message key="deal.tab.rails"/>');
  plan.deal = '${deal.id}';
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

  plan.driverProps = {
    find:'${findDriver}',
    edit:'${editDriver}',
    add:'${parseDriver}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="driver.add"/>',
    put:function(driver, item){

      plan.setDriver(driver, item);
    },
    show:['person/value']
  };
  plan.vehicleProps = {
    find:'${findVehicle}',
    edit:'${editVehicle}',
    add:'${parseVehicle}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="button.add.vehicle"/>',
    put:function(vehicle, item){
      plan.setVehicle(vehicle, item);
    },
    show:['model', 'number']
  };
  plan.trailerProps = {
    find:'${findTrailer}',
    add:'${parseTrailer}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="button.add.trailer"/>',
    put:function(trailer, item){
      plan.setTrailer(trailer, item);
    },
    show:['number']
  };

  plan.transporterProps = {
    find:'${findOrganisation}',
    edit:'${editOrganisation}',
    add:'${parseOrganisation}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="button.add.transporter"/>',
    put:function(transporter, item){
      plan.setTransporter(transporter, item);
    },
    show:['value']
  };

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
<c:set var="transporter"><fmt:message key="transportation.transporter"/></c:set>
<div id="load_plan" class="full-page">
  <div class="page-content">
    <div class="page-column">
      <table style="width: 440px">
        <tr>
          <td>
            <fmt:message key="deal.type"/>
          </td>
          <td>
            :<fmt:message key="${deal.type}"/>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="period"/>
          </td>
          <td>
            :<fmt:formatDate value="${deal.date}" pattern="dd.MM.yyyy"/>
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
            :${deal.organisation.value}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.realisation"/>
          </td>
          <td>
            :${deal.shipper.value}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.product"/>
          </td>
          <td>
            :${deal.product.name}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.quantity"/>
          </td>
          <td>
            :<fmt:formatNumber value="${deal.quantity}"/>&nbsp;${deal.unit.name}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.price"/>
          </td>
          <td>
            :<fmt:formatNumber value="${deal.price}"/>
          </td>
        </tr>
      </table>
    </div>
    <div class="page-column">
      <div>
        <template v-for="t in tabs">
          <b v-if="t == tab" class="tab">
            {{tabNames[t]}}
          </b>
          <span class="mini-close tab" v-else v-on:click="tab = t">
          {{tabNames[t]}}
        </span>
        </template>
      </div>
      <div>
        <div v-if="tab == 'vehicles'" class="tab-content" style="display: flex; flex-direction: row">
          <div>
            <div style="height: 100%; max-height: 500px; overflow-y: scroll">
              <div v-for="date in dates()" class="mini-close" :class="{bold : filterDate === date}"
                   style="font-size: 10pt" v-on:click="selectDate(date)">
                <span v-if="filterDate === date">
                  -
                </span>
                <span v-else>
                  &nbsp;
                </span>
                <span>
                  {{new Date(date).toLocaleDateString()}}:
                  {{itemsByDate(date).count}} /
                  {{itemsByDate(date).weight}}
                </span>
              </div>
            </div>
          </div>
          <div>
            <table>
              <tr>
                <td colspan="4" align="center">
                  <fmt:message key="load.plans"/>
                  <button v-on:click="newVehicle()"><fmt:message key="button.add.vehicle"/> </button>
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
                    <div v-for="(value, key) in getPlans()" :key="value.key" class="plan-item"
                         :class="'container-item-' + new Date(value.item.date).getDay()">
                      <div v-if="!value.removed">
                        <%--UPPER ROW--%>
                        <div class="upper">
                          <%--REMOVE BUTTON--%>
                          <div style="display: inline-block; width: 10pt">
                          <span title="${dropTitle}" class="mini-close" style="left: 0"
                                v-show="!value.item.archive" v-on:click="remove(key)">&times;</span>
                            <span v-show="value.item.archive" style="color: green">
                            &#10003;
                          </span>
                          </div>
                          <%--DATE INPUT--%>
                            <span class="mini-close" v-on:click="dateTimePicker(value)">
                              {{new Date(value.item.date).toLocaleDateString()}}
                            </span>
                          <%--PLAN INPUT--%>
                          <span style="position: relative">
                            <input v-model="value.item.plan" onfocus="this.select()" v-on:change="initSaveTimer(value)"
                                   title="${planTitle}" style="width: 4em; text-align: right; padding: 0 2px" min="1"> {{unit}}
                          </span>
                          <%--CUSTOMER INPUT--%>
                          <select v-model="value.item.customer" title="${customerTitle}" v-on:change="initSaveTimer(value)">
                            <option v-for="customer in customers" :value="customer.id">{{customer.value}}</option>
                          </select>
                          <span style="float: right">
                          <span class="mini-close" v-on:click="copy(key)">
                            Copy
                          </span>
                        </span>
                        </div>
                        <%--LOWER ROW--%>
                        <div class="lower">
                          <div>
                            <fmt:message key="transportation.driver"/>:
                            <object-input :props="driverProps" :object="value.item.driver" :item="value"></object-input>
                            <span v-if="value.item.driver && value.item.driver.license">
                            /{{value.item.driver.license}}/
                          </span>
                          </div>
                          <div>
                            <fmt:message key="transportation.automobile"/>
                            <object-input :props="vehicleProps" :object="value.item.vehicle" :item="value"></object-input>
                            <object-input :props="trailerProps" :object="value.item.trailer" :item="value"></object-input>
                            <object-input title="${transporter}" :props="transporterProps"
                                          :object="value.item.transporter" :item="value"></object-input>
                          </div>
                        </div>
                        <div class="lower">
                        <span v-if="value.editNote">
                          <input id="input" v-on:keyup.enter="saveNote(value)" v-on:blur="saveNote(value)"
                                 v-on:keyup.escape="closeNote(value.key)" v-model="value.noteInput" autocomplete="off"
                                 style="width: 100%; background: white; border: none">
                        </span>
                          <div style="flex-wrap: wrap">
                            <div v-for="(note, nId) in value.item.notes" :title="note.creator"
                                 style="display: inline-flex; padding-left: 1pt">
                              <span v-on:click="removeNote(value, nId)" class="mini-close">&times;</span>
                              <a v-on:click="editNote(value, nId)">{{note.note}}</a>
                            </div>
                            <div style="display: inline-flex; padding-left: 1pt">
                              <a class="mini-close" v-on:click="addNote(value.key)">
                                +<fmt:message key="note.add"/>
                              </a>
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
        </div>
        <div v-else-if="tab == 'rails'" class="tab-content">
          RAILS CONTENT
        </div>
        <div v-else>
          UNKNOWN CONTENT
        </div>
      </div>
    </div>
  </div>

  <div class="modal-footer">
    <button onclick="closeModal()"><fmt:message key="button.close"/> </button>
  </div>
</div>
  <table border="0" style="height: 100%; display: none">
    <tr>
      <td>
        <div class="plan-wrapper">

        </div>
      </td>
    </tr>

    <tr>
      <td colspan="3" align="center">

      </td>
    </tr>
  </table>
</html>
