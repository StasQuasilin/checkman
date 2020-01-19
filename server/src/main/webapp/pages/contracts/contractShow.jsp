<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 17.01.2020
  Time: 8:51
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/contractLoadPlan.css"/>
<link rel="stylesheet" href="${context}/css/editor.css"/>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/contractLoadPlan.vue"></script>
<script>
  loadPlan.api.save = '${save}';
  loadPlan.api.remove = '${remove}';
  loadPlan.contract = ${contract.id};
  loadPlan.types['buy'] = '<fmt:message key="buy"/>';
  loadPlan.types['sell'] = '<fmt:message key="sell"/>';
  loadPlan.driverProps = {
    find:'${findDriver}',
    edit:'${editDriver}',
    add:'${parseDriver}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="driver.add" />',
    show:["person/surname", "person/forename", "person/patronymic"],
    put:function(driver, item){
      loadPlan.putDriver(driver, item);
    }
  };
  loadPlan.vehicleProps = {
    find:'${findVehicle}',
    add:'${parseVehicle}',
    edit:'${editVehicle}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="button.add.vehicle"/>',
    put:function(vehicle, item){
      loadPlan.putVehicle(vehicle, item)
    },
    show:['model', 'number']
  };
  loadPlan.trailerProps = {
    find:'${findTrailer}',
    add:'${parseTrailer}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="button.add.trailer"/>',
    put:function(trailer, item){
      loadPlan.putVehicle(trailer, item)
    },
    show:['number']
  };
  <c:forEach items="${customers}" var="customer">
  loadPlan.customers.push({
    id:'${customer}',
    name:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
  <c:forEach items="${contract.products}" var="product">
  loadPlan.addProduct(JSON.parse('${product.toJson()}'))
  </c:forEach>

  function edit(){
    loadModal('${edit}', {id:${contract.id}});
    closeModal();
  }
</script>
<html>
  <table id="loadPlan" class="editor">
    <tr>
      <td valign="top">
        <%--CONTRACT DATA--%>
        <table>
          <tr>
            <td>
              <fmt:message key="date"/>
            </td>
            <td>
              <c:if test="${contract.from ne contract.to}">
                <fmt:message key="date.from"/>
              </c:if>
              <fmt:formatDate value="${contract.from}" pattern="dd.MM.yyyy"/>
              <c:if test="${contract.from ne contract.to}">
                <fmt:message key="date.to"/>
                <fmt:formatDate value="${contract.to}" pattern="dd.MM.yyyy"/>
              </c:if>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.organisation"/>
            </td>
            <td>
              ${contract.counterparty.value}
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <fmt:message key="deal.product"/>:
              <span style="font-size: 8pt;" v-if="products.length > 1">
                <a v-on:click="selectProducts(true)" style="color: grey">
                  <fmt:message key="select.all"/>
                </a>
                <a v-on:click="selectProducts(false)" style="color: grey">
                  <fmt:message key="unselect.all"/>
                </a>
                <a v-on:click="inverseSelection()" style="color: grey">
                  <fmt:message key="select.inverse"/>
                </a>
              </span>
            </td>
          </tr>
          <tr>
            <td valign="top" colspan="2" style="height: 200pt; overflow-y: scroll; ">
              <table border="0" style="width: 380px">
                <template v-for="product in products">
                  <tr style="border-bottom: solid gray 1pt" v-on:click="selectProduct(product)">
                    <td style="width: 12px; font-size: 10pt">
                      <span v-if="product.select" style="font-size: 10pt">
                          &check;
                        </span>
                    </td>
                    <td style="width: 290px" colspan="2">
                      <div>
                        {{types[product.type]}}
                        {{product.product.name}}
                      </div>
                      <div>
                        <span style="display: inline-block; width: 265px">
                          <span>
                            {{getPlan(product).toLocaleString()}}
                          </span>
                          /
                          <span>
                            {{product.amount.toLocaleString()}}
                          </span>
                          <span>
                            {{product.unit.name}}
                          </span>
                          <span v-if="product.plan > 0 && product.plan < product.amount" style="font-size: 10pt">
                            ({{(product.plan / product.amount * 100).toLocaleString()}} %)
                          </span>
                          <span v-else-if="product.plan > product.amount" style="font-size: 10pt; color: green">
                            (+{{(product.plan - product.amount).toLocaleString()}})
                          </span>
                        </span>
                        <span>
                          &times;{{product.price.toLocaleString()}}
                        </span>
                      </div>
                    </td>
                  </tr>
                </template>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <button onclick="edit()">
                <fmt:message key="edit"/>
              </button>
            </td>
          </tr>
        </table>
      </td>
      <td style="border: solid grey 1pt; width: 120px" valign="top">
        <%--LOAD FILTER--%>
        <div>
          <div v-for="(date, key) in getDates()" class="mini-close" v-on:click="setFilterDate(key)">
            <span v-if="key === filterDate">
              &times;
            </span>
            <span v-else>
              &nbsp
            </span>
            {{new Date(key).toLocaleDateString().substring(0, 5)}}:{{date}}
          </div>
        </div>
      </td>
      <td style="border: solid grey 1pt" valign="top">
        <%--LOAD LIST--%>
        <table>
          <tr>
            <td align="center">
              <button v-on:click="addTransportation()">
                <fmt:message key="button.add"/>
              </button>
            </td>
          </tr>
          <tr>
            <td style="border: solid gray 1pt; max-height: 150pt; width: 380px; overflow-y: scroll">
              <div v-for="transport in filteredTransportations()" class="transport-row"
                   :class="'transport-row-' + new Date(transport.date).getDay()">
                <div style="display: inline-block; vertical-align: top; width: 8px">
                  <div class="mini-close" v-on:click="removeTransportation(transport)">
                    &times;
                  </div>
                  <div class="mini-id" style="color: green" v-if="transport.save == -1 && transport.id != -1">
                    &check;
                  </div>
                  <div class="mini-id" v-else>
                    !
                  </div>
                  <div class="mini-id" v-if="transport.id != -1" :title="'ID:' + transport.id">
                    ID
                  </div>
                </div>
                <div style="display: inline-block">
                  <div>
                  <span class="mini-close" v-on:click="pickDate(transport)">
                    {{new Date(transport.date).toLocaleDateString().substring(0, 5)}}
                  </span>
                    <label for="customer">
                      <fmt:message key="load.customer.title"/>
                    </label>
                    <select id="customer" v-model="transport.customer" v-on:change="initSaveTimer(transport)">
                      <option v-for="customer in customers" :value="customer.id">
                        {{customer.name}}
                      </option>
                    </select>
                  </div>
                  <div style="padding-left: 4pt">
                    <div v-for="(product, productIdx) in transport.products">
                    <span v-if="transport.products.length > 1"
                          v-on:click="removeTransportationProduct(transport, productIdx)">
                      &times;
                    </span>
                    <span v-else>
                      &nbsp;
                    </span>
                    <span style="width: 210px; display: inline-block">
                      {{product.product.name}}
                    </span>
                      <input type="number" v-model="product.plan" autocomplete="off" onfocus="this.select()" v-on:change="initSaveTimer(transport)">
                      {{product.unit.name}}
                    </div>
                  </div>
                  <div style="font-size: 10pt; padding: 2pt">
                    <object-input :props="driverProps" :object="transport.driver" :item="transport"></object-input>
                  </div>
                  <div style="font-size: 10pt; padding: 2pt">
                    <object-input :props="vehicleProps" :object="transport.vehicle" :item="transport"></object-input>
                    <object-input :props="trailerProps" :object="transport.trailer" :item="transport"></object-input>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </table>
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
</html>
