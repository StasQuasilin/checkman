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
<c:set var="driver"><fmt:message key="transportation.driver"/></c:set>
<c:set var="truck"><fmt:message key="transportation.automobile"/></c:set>
<c:set var="trailer"><fmt:message key="transportation.automobile.trailer"/></c:set>
<div id="dealShow" class="full-page">
  <div id="container-header" class="container-header">
    <button id="header-content" v-on:click="goBack()"><fmt:message key="button.back"/> </button>
  </div>
  <div class="page-content">
    <div class="page-column">
      <div>
        <table style="width: 440px">
          <tr>
            <td>
              <fmt:message key="period"/>
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
              ${deal.organisation.value}
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
                {{product.id}}:{{productIdx}}
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <fmt:message key="deal.product"/>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <b>
                  <fmt:message key="${deal.type}"/> {{product.productName}} <fmt:message key="deal.from"/> {{product.shipperName}}
                </b>
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
    <div class="page-column" style="width: 100%;">
<%--      HEADER--%>
      <div style="display: none">
        <template v-for="t in tabs">
          <b v-if="t == tab" class="tab">
            {{tabNames[t]}}
          </b>
          <span class="mini-close tab" v-else v-on:click="tab = t">
          {{tabNames[t]}}
        </span>
        </template>
      </div>
      <div style="height: 100%">
        <div v-if="tab == 'vehicles'" class="tab-content" style="display: flex; flex-direction: row">
          <div>
            <div style="height: 100%; max-height: 500px; overflow-y: scroll; display: none">
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
          <div style="width: 100%; display: flex; flex-direction: column">
            <div style="text-align: center; padding: 4pt 0">
              <fmt:message key="load.plans"/>
              <button v-on:click="newVehicle()"><fmt:message key="button.add.vehicle"/> </button>
              <button v-on:click="newVehicle()"><fmt:message key="button.add.vehicle"/> </button>
            </div>
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
                            <template v-for="product in value.products" v-if="product.dealProduct === deal.products[selectedProduct].id">
                              <input v-model="product.amount" onfocus="this.select()" v-on:change="initSaveTimer(value)"
                                     title="${planTitle}" style="width: 4em; text-align: right; padding: 0 8px" min="1"> {{unit}}
                            </template>
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
                      <div title="${driver}">
                        <fmt:message key="transportation.driver"/>
                        <object-input :props="driverProps" :object="value.driver" :item="value"></object-input>
                        <span v-if="value.driver && value.driver.license">
                                /{{value.driver.license}}/
                              </span>
                      </div>
                      <div>
                        <fmt:message key="transportation.automobile"/>
                        <object-input title=${truck} :props="vehicleProps" :object="value.vehicle" :item="value"></object-input>
                        <object-input title=${trailer} :props="trailerProps" :object="value.trailer" :item="value"></object-input>
                      </div>
                      <div title="${transporter}">
                        <fmt:message key="transportation.transporter"/>
                        <object-input :props="transporterProps" :object="value.transporter" :item="value"></object-input>
                      </div>
                    </div>
                    <div>
                      <div v-for="product in value.products" v-if="product.dealProduct === deal.products[selectedProduct].id && product.weight">
                        <div>
                          Б:&#9;{{product.weight.gross.toLocaleString()}}
                        </div>
                        <div>
                          T:&#9;{{product.weight.tare.toLocaleString()}}
                        </div>
                        <div>
                          H:&#9;
                          <template v-if="product.weight.gross > 0 && product.weight.tare > 0">
                            {{(product.weight.gross - product.weight.tare).toLocaleString()}}
                          </template>
                          <template v-else>
                            0
                          </template>
                        </div>

                      </div>
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
            <div v-if="this.deal">
              <fmt:message key="totle.plan"/>: {{totalAmountString()}}
              <fmt:message key="totle.fact"/>: {{totalFactString()}}
            </div>
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
