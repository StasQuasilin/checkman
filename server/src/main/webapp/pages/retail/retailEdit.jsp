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
    background-color: lightgray;
  }
  .product-row select{
    border: none;
    background: transparent;
  }
  .product-row select:focus{
    outline: none;
  }
</style>
<script src="${context}/vue/templates/dateInput.vue"></script>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script>
  var editor = new Vue({
    el:'#editor',
    components:{
      'object-input':objectInput
    },
    data:{
      api:{},
      driver:{
        id:-1
      },
      vehicle:{
        id:-1
      },
      trailer:{
        id:-1
      },
      shippers:[],
      deals:[],
      driverProps:{
        header:'<fmt:message key="driver.add" />'
      },
      vehicleProps:{
        header:'<fmt:message key="button.add.vehicle"/>'
      },
      trailerProps:{
        header:'<fmt:message key="button.add.trailer" />'
      },
      counterpartyProps:{
        find:'${findOrganisation}',
        header:'<fmt:message key="counterparty.select"/>',
        put:function(a, b){
          b.counterparty = a;
        },
        show:['value']
      },
      productProps:{
        find:'${findProduct}',
        edit:'${editProduct}',
        add:'${parseProduct}',
        addHeader:'<fmt:message key="product.new"/>',
        header:'<fmt:message key="button.select.product"/>',
        put:function(product, deal){
          deal.product = product;
        },
        show:['name']
      }
    },
    methods:{
      addDeal:function(){
        let deal = {
          key:randomUUID(),
          counterparty:{
            id:-1
          },
          addressList:[],
          products:[],
          address:-1
        };
        this.deals.push(deal);
        this.addField(deal);
      },
      addCounterparty:function(counterpart, deal){
        console.log(deal);
        console.log(counterpart);
      },
      addField:function(deal){
        deal.products.push({
          product:{
            id:-1
          },
          amount:0,
          price:0,
          shipper:this.shippers[0].id
        })
      },
      removeProduct:function(deal, id){
        if (deal.products.length > 1) {
          deal.products.splice(id, 1);
        }
      },
       total:function(products){
         let total = 0;
         if (products) {
           products.forEach(function (a) {
             total += a.amount * a.price;
           });
         }
         return total;
       },
      checkAddress:function(deal){
        if (deal.address == -2){
          deal.address = -1;
          this.addAddress(deal);
        }
      },
      addAddress:function(deal){
        loadModal(this.api.editAddress, {counterparty:deal.counterparty.id}, function(a){
          console.log(deal);
          console.log(a.address);
          deal.addressList.push(a.address);
          deal.address = a.address.id;
        });
      }
    }
  });
  <c:forEach items="${shippers}" var="shipper">
  editor.shippers.push({
    id:${shipper.id},
    name:'${shipper.value}'
  });
  </c:forEach>
  editor.api.editAddress = '${editAddress}'
</script>
<table id="editor" width="580px" class="editor">
  <tr>
    <td>
      <fmt:message key="date"/>
    </td>
    <td>

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
      <fmt:message key="pallets"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="height: 200pt; width: 100%; font-size: 10pt; overflow-y: scroll">
        <div v-for="(deal, idx) in deals" style="border: solid 1pt; margin: 1pt; padding: 2pt">
          <div>
            <span>
              {{idx+1}}.
            </span>
            <object-input :props="counterpartyProps" :object="deal.counterparty" :item="deal"></object-input>
            <select v-if="deal.addressList.length > 0" v-model="deal.address" v-on:change="checkAddress(deal)">
              <option value="-1" disabled>
                <fmt:message key="need.select"/>
              </option>
              <option v-for="address in deal.addressList" :value="address.id">
                {{address.city}} {{address.street}}, {{address.build}}
              </option>
              <option value="-2">
                <fmt:message key="add.address"/>
              </option>
            </select>
            <a v-else v-on:click="addAddress(deal)">
              +<fmt:message key="add.address"/>
            </a>
            <span style="float: right">
              <fmt:message key="amount.total"/>:
              {{total(deal.products).toLocaleString()}}
            </span>
          </div>
          <table style="font-size: 10pt; border-collapse: collapse">
            <tr>
              <th style="width: 220px">
                <fmt:message key="deal.product"/>
              </th>
              <th style="width: 90px">
                <fmt:message key="seal.quantity"/>
              </th>
              <th style="width: 170px">
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
                <object-input :props="productProps" :object="product.product" :item="product"></object-input>
              </td>
              <td>
                <input id="amount" type="number" v-model="product.amount" autocomplete="off" onfocus="this.select()">
              </td>
              <td>
                <input id="price" type="number" v-model="product.price" autocomplete="off" onfocus="this.select()">
                <select v-model="product.shipper">
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
        <a v-on:click="addDeal">
          + <fmt:message key="deal.add"/>
        </a>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button>
        <fmt:message key="button.save"/>
      </button>
    </td>

  </tr>
</table>
</html>
