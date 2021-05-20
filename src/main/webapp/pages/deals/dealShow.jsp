<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<link rel="stylesheet" href="${context}/css/LoadPlan.css?v=${now}">
<script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
<script src="${context}/vue/loadPlan.vue?v=${now}"></script>
<script>
  plan.api.save = '${save}';
  plan.api.remove = '${remove}';
  <c:if test="${not empty back}">
  plan.api.back = '${back}';
  <c:if test="${not empty attributes}">
  plan.api.attributes = JSON.parse('${attributes}');
  </c:if>
  </c:if>
  plan.api.transportations = '${transportations}';

  plan.api.findVehicle = '${findVehicle}';
  plan.api.parseVehicle = '${parseVehicle}';
  plan.api.editVehicle = '${editVehicle}';

  plan.api.findDriver = '${findDriver}';
  plan.api.parseDriver = '${parseDriver}';
  plan.api.editDriver = '${editDriver}';
  <c:forEach items="${customers}" var="customer">
  plan.customers.push({
    id:'${customer}',
    value:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
  plan.tabNames['vehicles'] = '<fmt:message key="deal.tab.vehicles"/>';
  plan.tabNames['rails'] = '<fmt:message key="deal.tab.rails"/>';

  plan.deal = ${deal.toJson()};
  if (plan.deal.products.length > 0){
    plan.selectProduct(0);
  }
  plan.dealId = '${deal.id}';
  plan.dateFrom = new Date('${deal.date}');
  plan.dateTo = new Date('${deal.dateTo}');
  plan.quantity = ${deal.quantity};
  plan.unit = "${deal.unit.name}";


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
  <div id="container-header" class="container-header">
    <button id="header-content" v-on:click="goBack()"><fmt:message key="button.back"/> </button>
  </div>
  <div class="page-content">
    <div class="page-column">
      <div>
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
          <c:forEach items="${deal.costs}" var="cost">
            <tr>
              <td colspan="2">
                <fmt:message key="delivery.cost"/>
                <fmt:message key="delivery.${cost.customer}"/>:
                <fmt:formatNumber value="${cost.cost}"/>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div style="height: 100%; overflow-y: scroll" v-if="deal">
        <div v-for="(product, productIdx) in deal.products" class="product-cell"
             :class="{'selected-cell' : productIdx === selectedProduct}" v-on:click="selectProduct(productIdx)">
          <table>
            <tr>
              <td>
                <fmt:message key="deal.shipper"/>
              </td>
              <td>
                :{{product.shipperName}}
              </td>
            </tr>
            <tr>
              <td>
                <fmt:message key="deal.product"/>
              </td>
              <td>
                :{{product.productName}}
              </td>
            </tr>
            <tr>
              <td>
                <fmt:message key="deal.quantity"/>
              </td>
              <td>
                :{{product.quantity.toLocaleString()}} {{product.unitName}}
              </td>
            </tr>
            <tr v-if="product.price > 0">
              <td>
                <fmt:message key="deal.price"/>
              </td>
              <td>
                :{{product.price.toLocaleString()}}
              </td>
            </tr>
          </table>
        </div>
      </div>
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
          <div style="width: 100%">
            <table style="width: 100%">
              <tr>
                <td colspan="3" align="center">
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
              </tr>
              <tr>
                <td colspan="3">
                  <%--TABLE--%>
                  <transition-group name="flip-list" tag="div" class="plan-container">
                    <div v-for="(value, key) in getPlans()" :key="value.id" class="plan-item"
                         :class="'container-item-' + new Date(value.date).getDay()">
                      <div v-if="!value.removed">
                        <%--UPPER ROW--%>
                        <div class="upper">
                          <%--REMOVE BUTTON--%>
                          <div style="display: inline-block; width: 10pt">
                          <span title="${dropTitle}" class="mini-close" style="left: 0"
                                v-show="!value.archive" v-on:click="remove(key)">&times;</span>
                            <span v-show="value.archive" style="color: green">
                            &#10003;
                          </span>
                          </div>
                          <%--DATE INPUT--%>
                            <span class="mini-close" v-on:click="dateTimePicker(value)">
                              {{new Date(value.date).toLocaleDateString()}}
                            </span>
                          <%--PLAN INPUT--%>
                          <span style="position: relative">
                            <input v-model="value.amount" onfocus="this.select()" v-on:change="initSaveTimer(value)"
                                   title="${planTitle}" style="width: 4em; text-align: right; padding: 0 2px" min="1"> {{unit}}
                          </span>
                          <%--CUSTOMER INPUT--%>
                          <select v-model="value.customer" title="${customerTitle}" v-on:change="initSaveTimer(value)">
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
                            <object-input :props="driverProps" :object="value.driver" :item="value"></object-input>
                            <span v-if="value.driver && value.driver.license">
                            /{{value.driver.license}}/
                          </span>
                          </div>
                          <div>
                            <fmt:message key="transportation.automobile"/>
                            <object-input :props="vehicleProps" :object="value.vehicle" :item="value"></object-input>
                            <object-input :props="trailerProps" :object="value.trailer" :item="value"></object-input>
                            <object-input title="${transporter}" :props="transporterProps"
                                          :object="value.transporter" :item="value"></object-input>
                          </div>
                        </div>
                        <div class="lower">
                        <span v-if="value.editNote">
                          <input id="input" v-on:keyup.enter="saveNote(value)" v-on:blur="saveNote(value)"
                                 v-on:keyup.escape="closeNote(value.key)" v-model="value.noteInput" autocomplete="off"
                                 style="width: 100%; background: white; border: none">
                        </span>
                          <div style="flex-wrap: wrap">
                            <div v-for="(note, nId) in value.notes" :title="note.creator"
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

</div>
</html>
