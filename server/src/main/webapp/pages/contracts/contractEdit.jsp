<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 04.12.2019
  Time: 14:03
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/contractEdit.vue"></script>
<script>
  editor.counterpartyProps = {
    find:'${findOrganisation}',
    edit:'${editOrganisation}',
    add:'${parseOrganisation}',
    addHeader:'<fmt:message key="button.add"/>',
    header:'<fmt:message key="counterparty.select"/>',
    put:function(a, b){
      b.counterparty = a;
      editor.getAddress(a, b);
    },
    show:['value']
  };
  editor.productProps = {
    find:'${findProduct}',
    edit:'${editProduct}',
    add:'${parseProduct}',
    addHeader:'<fmt:message key="product.new"/>',
    header:'<fmt:message key="button.select.product"/>',
    put:function(product, contract){
      contract.product = product;
      if (product.unit && product.unit.id) {
        contract.unit = product.unit;
      } else {
        contract.unit = {id:-1};
      }
      editor.getPrice(contract, product);
    },
    show:['name']
  };
  editor.api.save = '${save}';
  editor.api.show = '${show}';
  editor.api.findAddress = '${findLoadAddress}';
  editor.api.findPrice = '${findPrice}';
  editor.types.push({
    id:'buy',
    name:'<fmt:message key="buy"/>'
  });
  editor.types.push({
    id:'sell',
    name:'<fmt:message key="sell"/>'
  });
  <c:forEach items="${shippers}" var="shipper">
  editor.shippers.push({
    id:'${shipper.id}',
    name:'${shipper.value}'
  });
  </c:forEach>
  <c:forEach items="${units}" var="unit">
  editor.units.push({
    id:'${unit.id}',
    name:'${unit.name}'
  });
  </c:forEach>
  editor.shipper = editor.shippers[0].id;
  <c:choose>
  <c:when test="${not empty contract}">
  editor.contract.id = '${contract.id}';
  editor.contract.number = '${contract.number}';
  editor.contract.from = new Date('${contract.from}').toISOString().substring(0, 10);
  editor.contract.to = new Date('${contract.to}').toISOString().substring(0, 10);
  editor.contract.counterparty = JSON.parse('${contract.counterparty.toJson()}');
  <c:forEach items="${contract.products}" var="product">
  editor.contract.products.push(JSON.parse('${product.toJson()}'));
  </c:forEach>
  </c:when>
  <c:otherwise>
  editor.addProductLine();
  </c:otherwise>
  </c:choose>
</script>
<html>
<c:set var="saveAndCloseDescription">
  <fmt:message key="save.close.description"/>
</c:set>
<c:set var="saveAndContinueDescription">
  <fmt:message key="save.continue.description"/>
</c:set>
<c:set var="amount">
  <fmt:message key="seal.quantity"/>
</c:set>
<c:set var="price">
  <fmt:message key="deal.price"/>
</c:set>
<link rel="stylesheet" href="${context}/css/editor.css">
  <div id="editor" class="editor" >
    <span style="font-size: 6pt">
      ID:{{contract.id}}
    </span>
    <table style="width: 100%" border="0">
      <tr>
        <td>
          <label for="type">
            <fmt:message key="deal.type"/>
          </label>
        </td>
        <td>
          <select id="type" v-model="contract.type">
            <option v-for="type in types" :value="type.id">
              {{type.name}}
            </option>
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <label for="number">
            <fmt:message key="deal.number"/>
          </label>
        </td>
        <td width="70%">
          <input id="number" v-model="contract.number" autocomplete="off">
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="date"/>
        </td>
        <td>
          <fmt:message key="date.from"/>
          <span class="mini-close" v-on:click="selectFrom">{{new Date(contract.from).toLocaleDateString()}}</span>
          <fmt:message key="date.to"/>
          <span class="mini-close" v-on:click="selectTo">{{new Date(contract.to).toLocaleDateString()}}</span>
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="deal.organisation"/>:
        </td>
        <td>
          <object-input :props="counterpartyProps" :object="contract.counterparty" :item="contract"></object-input>
        </td>
      </tr>
      <tr v-if="contract.counterparty.id != -1">
        <td>
          <label for="address">
            <fmt:message key="address"/>
          </label>
        </td>
        <td>
          <select id="address" v-model="contract.address.id" v-on:change="checkAddress(contract)">

            <option value="-1" disabled v-if="contract.addressList.length > 1">
              <fmt:message key="need.select"/>
            </option>
            <option v-else value="-1">
              <fmt:message key="no.data"/>
            </option>
            <option v-for="address in contract.addressList" :value="address.id">
              {{address.city}}, {{address.street}}, {{address.build}}
            </option>
            <option value="-2">
              <fmt:message key="add.address"/>
            </option>
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <label for="realisation">
            <fmt:message key="deal.realisation"/>
          </label>
        </td>
        <td>
          <select id="realisation" v-model="shipper">
            <option v-for="shipper in shippers" :value="shipper.id">
              {{shipper.name}}
            </option>
          </select>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <fmt:message key="deal.product"/>
        <span class="mini-close" style="font-size: 10pt" v-on:click="addProductLine" v-if="!anyUnselected()">
          +&nbsp;<fmt:message key="button.add"/>
        </span>
        </td>
      </tr>
      <tr>
        <td colspan="2" style="max-height: 200pt; width: 753px; border: solid gray 1pt; overflow-y: scroll">
          <table>
            <template v-for="(product, productIdx) in contract.products">
              <tr>
                <td>
                  <span style="font-size: 6pt">
                    ID:{{product.id}}
                  </span>
                </td>
              </tr>
              <tr>
                <td v-if="contract.products.length > 1" v-on:click="removeProduct(productIdx)">
                  -
                </td>
                <td v-else>
                  &nbsp;
                </td>
                <td style="width: 250px">
                  <object-input :props="productProps" :object="product.product" :item="product"></object-input>
                </td>
                <td style="width: 140px">
                  <input type="number" title="${amount}" v-model="product.amount" autocomplete="off" onfocus="this.select()"/>
                  <select v-model="product.unit.id">
                    <option v-for="unit in units" :value="unit.id">
                      {{unit.name}}
                    </option>
                  </select>
                </td>
                <td style="width: 170px">
                  <input type="number" title="${price}" v-model="product.price" autocomplete="off" onfocus="this.select()"/>
                  <select v-model="product.shipper.id">
                    <option v-for="shipper in shippers" :value="shipper.id">
                      {{shipper.name}}
                    </option>
                  </select>
                </td>
                <td style="width: 140px" align="right">
                  {{(product.amount * product.price).toLocaleString()}}
                </td>
              </tr>
            </template>
          </table>
        </td>
      </tr>
      <tr v-if="contract.products.length > 1">
        <td colspan="2" style="font-weight: bold">
          <fmt:message key="amount.total"/>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <button onclick="closeModal()">
            <fmt:message key="button.cancel"/>
          </button>
          <button title="${saveAndCloseDescription}" v-on:click="saveClose()">
            <fmt:message key="button.save.and.close"/>
          </button>
          <button title="${saveAndContinueDescription}" v-on:click="saveContinue()">
            <fmt:message key="button.save.and.continue"/>
          </button>
        </td>
      </tr>
    </table>
  </div>
</html>
