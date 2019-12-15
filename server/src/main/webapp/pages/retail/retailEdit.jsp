<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.11.2019
  Time: 10:32
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${contex}/css/editor.css">
<style>
  .object-block {
    background-color: transparent;
    border: none;
  }
  .product-row{

  }
  .product-row:nth-child(even){
    background-color: #e8e8e8;
  }
  .product-row select{
    border: none;
    background: transparent;
  }
  .product-row select:focus{
    background-color: rgb(179, 212, 251);
    outline: none;
  }
</style>
<script src="${context}/vue/templates/dateInput.vue"></script>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/retailEdit.vue"></script>
<script>
  editor.type='${type}';
  editor.driverProps={
    find:'${findDriver}',
        edit:'${editDriver}',
        add:'${parseDriver}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="driver.add" />',
        show:["person/surname", "person/forename", "person/patronymic"],
        put:function(driver){
      editor.putDriver(driver);
    }
  };
  editor.vehicleProps={
    find:'${findVehicle}',
        edit:'${editVehicle}',
        add:'${parseVehicle}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.vehicle"/>',
        show:['model', 'number'],
        put:function(vehicle){
      editor.putVehicle(vehicle);
    }
  };
  editor.trailerProps={
    header:'<fmt:message key="button.add.trailer" />',
        show:['number']
  };
  editor.counterpartyProps={
    find:'${findOrganisation}',
        edit:'${editOrganisation}',
        add:'${parseOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="counterparty.select"/>',
        put:function(a, b){
      b.counterparty = a;
      editor.getContracts(a, b);
      editor.getAddress(a, b);
    },
    show:['value']
  };
  editor.transporterProps={
    find:'${findOrganisation}',
        edit:'${editOrganisation}',
        add:'${parseOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="transporter.select"/>',
        put:function(transporter){
      editor.putTransporter(transporter);
    },
    show:['value']
  };
  editor.productProps={
    find:'${findProduct}',
        edit:'${editProduct}',
        add:'${parseProduct}',
        addHeader:'<fmt:message key="product.new"/>',
        header:'<fmt:message key="button.select.product"/>',
        put:function(product, deal){
      deal.product = product;
      if (product.unit && product.unit.id) {
        deal.unit = product.unit;
      } else {
        deal.unit = {id:-1};
      }
      editor.getPrice(deal, product);
    },
    show:['name']
  };
  <c:forEach items="${shippers}" var="shipper">
  editor.shippers.push({
    id:${shipper.id},
    name:'${shipper.value}'
  });
  </c:forEach>
  <c:forEach items="${customers}" var="customer">
  editor.customers.push({
    id:'${customer}',
    name:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
  editor.customer = editor.customers[0].id;
  <c:forEach items="${units}" var="unit">
  editor.units.push({
    id:${unit.id},
    name:'${unit.name}'
  });
  </c:forEach>
  editor.api.save = '${save}';
  editor.api.editAddress = '${editAddress}';
  editor.api.findAddress = '${findLoadAddress}';
  editor.api.findContracts = '${findContracts}';
  editor.api.waybill = '${waybillPrint}';
  editor.api.print = '${print}';
  editor.api.price = '${findPrice}';
  <c:choose>
  <c:when test="${not empty transportation}">
//  TRANSPORTATION INIT HERE
  editor.id = '${transportation.id}';
  editor.customer = '${transportation.customer}';
  editor.date = new Date('${transportation.date}').toISOString().substring(0, 10);

  <c:if test="${not empty transportation.driver}">
  editor.driver = JSON.parse('${transportation.driver.toJson()}');
  </c:if>
  <c:if test="${not empty transportation.truck}">
  editor.vehicle = JSON.parse('${transportation.truck.toJson()}');
  </c:if>
  <c:if test="${not empty transportation.trailer}">
  editor.trailer = JSON.parse('${transportation.trailer.toJson()}');
  </c:if>
  <c:if test="${not empty transportation.transporter}">
  editor.transporter = JSON.parse('${transportation.transporter.toJson()}');
  </c:if>
  var deal;
  <c:forEach items="${transportation.documents}" var="document">
  deal = JSON.parse('${document.getContract().toJson()}');
  if (!deal.address){
    deal.address = {id:-1}
  }
  deal.addressList = [];
  editor.getAddress(deal.counterparty, deal);
  deal.products.forEach(function(p){
    p.pallet = p.amount / p.product.pallet;
  });
  editor.deals.push(deal);
  </c:forEach>
  editor.shipper = editor.computeShipper();
  </c:when>

  <c:otherwise>
  editor.shipper = editor.shippers[0].id;
  editor.addDeal();
  </c:otherwise>
  </c:choose>
</script>
<link rel="stylesheet" href="${context}/css/editor.css"/>
<table id="editor" width="960px" class="editor">
  <tr>
    <td>
      <fmt:message key="date"/>
    </td>
    <td width="80%">
      <a v-on:click="selectDate">{{new Date(date).toLocaleDateString()}}</a>
    </td>
  </tr>
  <tr>
    <td>
      <label for="customer">
        <fmt:message key="load.customer.title"/>
      </label>
    </td>
    <td>
      <select id="customer" v-model="customer">
        <option v-for="customer in customers" :value="customer.id">
          {{customer.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.driver"/>
    </td>
    <td>
      <object-input :props="driverProps" :object="driver"></object-input>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.automobile"/>/<fmt:message key="transportation.automobile.trailer"/>
    </td>
    <td>
      <object-input :props="vehicleProps" :object="vehicle"></object-input>
      <object-input :props="trailerProps" :object="trailer"></object-input>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.transporter"/>
    </td>
    <td>
      <object-input :props="transporterProps" :object="transporter"></object-input>
    </td>
  </tr>
  <tr>
    <td>
      <label for="masterRealisation">
        <fmt:message key="deal.realisation"/>
      </label>
    </td>
    <td>
      <select id="masterRealisation" v-model="shipper">
        <option v-for="shipper in shippers" :value="shipper.id">
          {{shipper.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="pallets"/>: {{palletsAmount()}}
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="height: 290pt; width: 100%; font-size: 10pt; overflow-y: scroll">
        <div v-for="(deal, idx) in deals" style="border: solid 1pt; margin: 1pt; padding: 2pt; position: relative">
          <div style="display: inline-block; width: 100%; position: relative">
            <div style="display: inline-block; position: absolute; font-size: 11pt; background-color: #d4d4d4;">
              <div v-on:click="moveItem(idx, -1)" style="padding: 0 2pt; border-bottom: solid gray 1pt">
                <span>
                  +
                </span>
              </div>
              <div v-on:click="moveItem(idx, 1)" style="padding: 0 2pt">
                <span>
                  -
                </span>
              </div>
            </div>
            <div style="height: 20px; position: relative; left: 14pt; width: 97%">
            <span class="mini-close" v-on:click="removeDeal(idx)">
              &times;
            </span>
            <span :class="{error : deal.error}">
              {{idx+1}}.
            </span>
              <div style="display: inline-block" :class="{error : deal.error}" v-on:click="deal.error = false">
                <object-input :props="counterpartyProps" :object="deal.counterparty" :item="deal"></object-input>
              </div>
            <span v-if="deal.counterparty.id > 0">
              <select v-if="deal.addressList.length > 0" v-model="deal.address.id" v-on:change="checkAddress(deal)">
                <option value="-1" disabled>
                  <fmt:message key="need.select"/>
                </option>
                <option v-for="address in deal.addressList" :value="address.id">
                  {{address.city}}, {{address.street}}, {{address.build}}
                </option>
                <option value="-2">
                  <fmt:message key="add.address"/>
                </option>
              </select>
              <a v-else v-on:click="addAddress(deal)">
                +<fmt:message key="add.address"/>
              </a>
            </span>
            <span style="float: right">
              <span class="mini-close" v-on:click="print(deal)">
                <fmt:message key="document.print"/>
              </span>
              <fmt:message key="amount.total"/>:
              {{total(deal.products).toLocaleString()}}
            </span>
            </div>
          </div>

          <table style="font-size: 10pt; border-collapse: collapse" border="1">
            <tr>
              <th style="width: 310pt">
                <fmt:message key="deal.product"/>
              </th>
              <th style="width: 360pt">
                <fmt:message key="seal.quantity"/>
              </th>
              <th style="width: 144pt">
                <fmt:message key="deal.price"/>/<fmt:message key="deal.realisation"/>
              </th>
              <th style="width: 70px">
                <fmt:message key="amount.total"/>
              </th>
            </tr>
            <tr v-for="(product, pId) in deal.products" class="product-row">
              <td>
                <a v-on:click="removeProduct(deal, pId)">
                  -
                </a>
                <div style="display: inline-block;" :class="{error : product.productError}" v-on:click="product.productError = false">
                  <object-input :props="productProps" :object="product.product" :item="product"></object-input>
                </div>
              </td>
              <td>
                <div>
                  <fmt:message key="pallets"/>:
                  <input id="pallets" type="number" step="1" autocomplete="off" v-model="product.pallet"
                         v-on:change="checkPallets(product)" onfocus="this.select()">
                  &times; {{product.product.pallet}} =
                  <input id="amount" type="number" v-model="product.amount" autocomplete="off" onfocus="this.select()" v-on:change="checkAmount(product)">
                  <select v-model="product.unit.id" :class="{error : product.unitError}" v-on:click="product.unitError = false">
                    <option value="-1" disabled>
                      !!
                    </option>
                    <option v-for="unit in units" :value="unit.id">
                      {{unit.name}}
                    </option>
                  </select>
                </div>
              </td>
              <td>
                <input id="price" type="number" v-model="product.price" autocomplete="off" onfocus="this.select()">
                <select v-model="product.shipper.id">
                  <option v-for="shipper in shippers" :value="shipper.id">
                    {{shipper.name}}
                  </option>
                </select>
              </td>
              <td align="right">
                {{(product.amount * product.price).toLocaleString()}}
              </td>
            </tr>
          </table>
          <div>
            <a v-on:click="addField(deal)"><fmt:message key="button.add.product"/></a>
          </div>
        </div>
        <div style="width: 100%; border-bottom: solid 1pt"></div>

      </div>
      <a v-on:click="addDeal">
        + <fmt:message key="deal.add"/>
      </a>
        <span class="mini-close" v-on:click="waybillPrint">
          <fmt:message key="print.waybill"/>
        </span>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="saveAndClose">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
