<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/LoadPlan.css">
<script src="${context}/vue/loadPlan.vue"></script>
<script>

  <c:forEach items="${customers}" var="customer">
  plan.customers.push({
    id:'${customer}',
    value:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
  plan.api.save = '${save}';
  plan.api.remove = '${remove}';
  plan.api.findVehicle = '${findVehicle}';
  plan.api.parseVehicle = '${parseVehicle}';
  plan.api.editVehicle = '${editVehicle}';

  plan.api.findDriver = '${findDriver}';
  plan.api.parseDriver = '${parseDriver}';
  plan.api.editDriver = '${editDriver}';

  plan.deal = '${contract.id}';
  plan.from = new Date('${contract.from}');
  plan.to = new Date('${contract.to}');
  <c:forEach items="${contract.products}" var="product">
  plan.contractProducts.push({
    id:'${product.id}',
    product:'${product.product.name}',
    shipper:'${product.shipper.value}',
    done:${product.done},
    amount:${product.amount},
    price:${product.price},
    selected:false
  })
  </c:forEach>
  plan.worker = {
    id:${worker.id},
    value:'${worker.value}'
  }

</script>
<style>
  .selected{
    background-color: lightgray;
  }
</style>
<c:set var="vehicleHolder"><fmt:message key="transportation.automobile"/>... </c:set>
<c:set var="driverHolder"><fmt:message key="transportation.driver"/>... </c:set>
<c:set var="editVehicle"><fmt:message key="vehicle.edit"/></c:set>
<c:set var="editDriver"><fmt:message key="driver.edit"/></c:set>
<c:set var="cancel"><fmt:message key="button.cancel"/></c:set>
  <table border="1" style="height: 100%" id="load_plan">
    <tr>
      <td valign="top">
        <table border="0" style="height: 100%; width: 340px">
          <tr>
            <td>
              <c:choose>
                <c:when test="${contract.from ne contract.to}">
                  <fmt:message key="period"/>
                </c:when>
                <c:otherwise>
                  <fmt:message key="date"/>
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:formatDate value="${contract.from}" pattern="dd.MM.yyyy"/>
              <c:if test="${contract.from ne contract.to}">
                -
                <fmt:formatDate value="${contract.to}" pattern="dd.MM.yyyy"/>
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
              ${contract.counterparty.value}
            </td>
          </tr>
          <c:if test="${not empty contract.number}">
            <tr>
              <td>
                <fmt:message key="deal.number"/>
              </td>
              <td>
                :
              </td>
              <td>
                  ${contract.number}
              </td>
            </tr>
          </c:if>
          <tr>
            <td colspan="3" height="100%" valign="top" >
              <div style="border: solid gray 1pt; overflow-y: scroll; height: 100%">
                <div v-for="product in contractProducts" style="border-bottom: dotted gray 1pt; padding: 0 4pt;"
                     v-on:click="selectRow(product.id)" :class="{selected : product.selected}">
                  <div style="display: inline-block">
                    <div>
                      {{product.product}}
                    </div>
                    <div>
                      {{product.done.toLocaleString()}} / {{product.amount.toLocaleString()}}
                    </div>
                    <div>
                      <fmt:message key="deal.price"/> {{product.price.toLocaleString()}} {{product.shipper}}
                    </div>
                  </div>
                  <div style="display: inline-block; font-size: 10pt; float: right">
                    <div v-on:click="newVehicle(product.id)">
                      <a class="mini-close"><fmt:message key="button.add.vehicle"/></a>
                    </div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="3" align="center">
              <button onclick="closeModal()">
                <fmt:message key="button.close"/>
              </button>
            </td>
          </tr>
        </table>
      </td>
      <td>
        <table border="0">
          <%--HEADER--%>
          <tr style="font-size: 10pt">
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
            <td colspan="4">
              <%--TABLE--%>
              <transition-group name="flip-list" tag="div" class="plan-container">
                <%--<div v-for="(value, key) in plans" :key="value.key" class="plan-item">--%>
                <%--<div v-if="!value.removed">--%>
                <%--&lt;%&ndash;UPPER ROW&ndash;%&gt;--%>
                <%--<div class="upper">--%>
                <%--&lt;%&ndash;REMOVE BUTTON&ndash;%&gt;--%>
                <%--<div style="display: inline-block; width: 10pt">--%>
                <%--<span title="${dropTitle}" class="mini-close" style="left: 0"--%>
                <%--v-show="!value.item.transportation.archive" v-on:click="remove(key)">&times;</span>--%>
                <%--<span v-show="value.item.transportation.archive" style="color: green">--%>
                <%--&#10003;--%>
                <%--</span>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;DATE INPUT&ndash;%&gt;--%>
                <%--<input readonly style="width: 7em"--%>
                <%--v-model="new Date(value.item.date).toLocaleDateString()"--%>
                <%--v-on:click="dateTimePicker(key)">--%>
                <%--&lt;%&ndash;PLAN INPUT&ndash;%&gt;--%>
                <%--<span style="position: relative">--%>
                <%--<input v-model="value.item.plan" onfocus="this.select()" type="number" v-on:change="initSaveTimer(value.key)"--%>
                <%--title="${planTitle}" style="width: 6em; text-align: right;" min="1">--%>
                <%--<span style="position: absolute; top: 0; right: 12px">--%>
                <%--{{unit}}--%>
                <%--</span>--%>
                <%--</span>--%>
                <%--&lt;%&ndash;CUSTOMER INPUT&ndash;%&gt;--%>
                <%--<select v-model="value.item.customer" title="${customerTitle}" v-on:change="initSaveTimer(value.key)">--%>
                <%--<option v-for="customer in customers" :value="customer.id">{{customer.value}}</option>--%>
                <%--</select>--%>
                <%--<span class="fact-info">--%>
                <%--<span title="${factTitle}" style="padding: 2pt">--%>
                <%--<span v-if="value.item.transportation.weight.id">--%>
                <%--<b>--%>
                <%--{{value.item.transportation.weight.netto.toLocaleString()}}&nbsp;{{unit}}--%>
                <%--</b>--%>
                <%--<span>--%>
                <%--{{different(value.item.transportation.weight.netto, value.item.plan)}}--%>
                <%--</span>--%>
                <%--</span>--%>
                <%--</span>--%>
                <%--<div class="fact-details" v-if="value.item.transportation.weight.id">--%>
                <%--<table>--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<fmt:message key="weight.brutto"/>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--:--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--{{(value.item.transportation.weight.brutto).toLocaleString()}}--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<fmt:message key="weight.tara"/>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--:--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--{{(value.item.transportation.weight.tara).toLocaleString()}}--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<fmt:message key="weight.netto"/>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--:--%>
                <%--</td>--%>

                <%--<td>--%>
                <%--{{(value.item.transportation.weight.netto).toLocaleString()}}--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--</table>--%>
                <%--</div>--%>
                <%--</span>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;LOWER ROW&ndash;%&gt;--%>
                <%--<div class="lower">--%>
                <%--<table style="font-size: 10pt; width: 100%" border="0">--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<span><fmt:message key="transportation.automobile"/></span>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--:--%>
                <%--</td>--%>
                <%--<td width="100%">--%>
                <%--&lt;%&ndash;VEHICLE STUFF&ndash;%&gt;--%>
                <%--<div>--%>
                <%--<div style="display: inline-block"--%>
                <%--v-if="value.item.transportation.vehicle.id > -1 && !(value.editVehicle)"--%>
                <%--v-on:click.right="">--%>
                <%--{{value.item.transportation.vehicle.model}}--%>
                <%--'{{value.item.transportation.vehicle.number}}'--%>
                <%--<template v-if="value.item.transportation.vehicle.trailer">--%>
                <%--'{{value.item.transportation.vehicle.trailer}}'--%>
                <%--</template>--%>
                <%--<span title="${editVehicle}" v-on:click="editVehicle(key)" class="mini-close flipY" style="padding: 0">--%>
                <%--&#9998;--%>
                <%--</span>--%>
                <%--<span title="${cancel}" v-on:click="cancelVehicle(key)" class="mini-close" style="padding: 0">--%>
                <%--&times;--%>
                <%--</span>--%>
                <%--</div>--%>
                <%--<div v-else-if="value.editVehicle">--%>
                <%--<div style="display: inline-block" v-on:blur="parseVehicle(value.vehicleInput, key)">--%>
                <%--&lt;%&ndash;VEHICLE INPUT&ndash;%&gt;--%>
                <%--<input v-on:keyup="findVehicle(value.vehicleInput)" id="input"--%>
                <%--v-on:keyup.enter="parseVehicle(value.vehicleInput, key)"--%>
                <%--v-on:keyup.escape="closeVehicleInput(key)"--%>
                <%--v-model="value.vehicleInput" autofocus--%>
                <%--placeholder="${vehicleHolder}" style="border: none">--%>
                <%--<div class="custom-data-list">--%>
                <%--<div v-for="vehicle in foundVehicles" class="custom-data-list-item"--%>
                <%--v-on:click="setVehicle(vehicle, key)">--%>
                <%--{{vehicle.model}}--%>
                <%--'{{vehicle.number}}'--%>
                <%--<template v-if="vehicle.trailer">--%>
                <%--'{{vehicle.trailer}}'--%>
                <%--</template>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<span v-on:click="closeVehicleInput(key)" class="mini-close" style="left: -22pt">&times;</span>--%>
                <%--</div>--%>
                <%--<a v-else v-on:click="openVehicleInput(value.key)">--%>
                <%--<fmt:message key="transportation.automobile.insert.info"/>--%>
                <%--</a>--%>
                <%--</div>--%>
                <%--</td>--%>
                <%--<td rowspan="2">--%>
                <%--<a class="mini-close" v-on:click="addNote(value.key)">--%>
                <%--+<fmt:message key="note.add"/>--%>
                <%--</a>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td>--%>
                <%--<span><fmt:message key="transportation.driver"/></span>--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--:--%>
                <%--</td>--%>
                <%--<td>--%>
                <%--&lt;%&ndash;DRIVER STUFF&ndash;%&gt;--%>
                <%--<span v-if="value.item.transportation.driver.id > -1 && !value.editDriver">--%>
                <%--{{value.item.transportation.driver.person.value}}--%>
                <%--<span title="${editDriver}" v-on:click="editDriver(key)" class="mini-close flipY" style="padding: 0">--%>
                <%--&#9998;--%>
                <%--</span>--%>
                <%--<span title="${cancel}" v-on:click="cancelDriver(key)" class="mini-close" style="padding: 0">--%>
                <%--&times;--%>
                <%--</span>--%>
                <%--</span>--%>

                <%--<template v-else-if="value.editDriver">--%>
                <%--<div style="display: inline-block" v-on:blur="parseDriver(value.driverInput, key)">--%>
                <%--&lt;%&ndash;DRIVER INPUT&ndash;%&gt;--%>
                <%--<input v-on:keyup="findDriver(value.driverInput)" id="input"--%>
                <%--v-on:keyup.escape="closeDriverInput(key)"--%>
                <%--v-on:keyup.enter="parseDriver(value.driverInput, key)"--%>
                <%--v-model="value.driverInput" autofocus--%>
                <%--placeholder="${driverHolder}" style="border:none;">--%>
                <%--<div class="custom-data-list">--%>
                <%--<div v-for="driver in foundDrivers" class="custom-data-list-item" v-on:click="setDriver(driver, key)">--%>
                <%--{{driver.person.value}}--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<span v-on:click="closeDriverInput(key)" class="mini-close" style="left: -22pt">&times;</span>--%>
                <%--</template>--%>
                <%--<a v-else v-on:click="openDriverInput(value.key)">--%>
                <%--<fmt:message key="transportation.driver.insert.info"/>--%>
                <%--</a>--%>

                <%--</td>--%>

                <%--</tr>--%>
                <%--</table>--%>
                <%--</div>--%>
                <%--<div class="lower">--%>
                <%--<span v-if="value.editNote">--%>
                <%--&lt;%&ndash;suppress XmlDuplicatedId &ndash;%&gt;--%>
                <%--<input id="input"--%>
                <%--v-on:keyup.enter="saveNote(key)"--%>
                <%--v-on:blur="saveNote(key)"--%>
                <%--v-on:keyup.escape="closeNote(value.key)"--%>
                <%--v-model="value.noteInput"--%>
                <%--style="width: 100%; background: white; border: none">--%>
                <%--</span>--%>
                <%--<div style="flex-wrap: wrap">--%>
                <%--<div v-for="note in value.item.transportation.notes" style="display: inline-flex;">--%>
                <%--<span v-if="note.creator.id !== worker.id">{{note.creator.value}}</span>--%>
                <%--<span v-else>--%>
                <%--<span v-on:click="removeNote(key, note.id)" class="mini-close">&times;</span>--%>
                <%--<fmt:message key="you"/>--%>
                <%--</span>:{{note.note}}--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
              </transition-group>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</html>
