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
<script>
  var editor = new Vue({
    el:'#editor',
    components:{
      'object-input':objectInput
    },
    data:{
      id:-1,
      type:'${type}',
      api:{},
      date:new Date().toISOString().substring(0, 10),
      customer:-1,
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
      customers:[],
      units:[],
      deals:[],
      counterpartyDeals:{},
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
          editor.getContracts(a, b);
          editor.getAddress(a, b);
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
          if (product.unit && product.unit.id) {
            deal.unit = product.unit;
          } else {
            deal.unit = {id:-1};
          }
          editor.getPrice(deal, product);
        },
        show:['name']
      }
    },
    methods:{
      getCounterpartyDeals:function(counterparty){
        if(this.counterpartyDeals[counterparty.id]){
          return this.counterpartyDeals[counterparty.id];
        }
      },
      addDeal:function(){
        let deal = {
          id:-1,
          key:randomUUID(),
          counterparty:{
            id:-1
          },
          addressList:[],
          products:[],
          address:{
            id:-1
          }
        };
        this.deals.push(deal);
        this.addField(deal);
      },
      getContracts:function(counterparty, deal){
        if (counterparty.name){
          console.log("Get contracts for " + counterparty.name);
          loadModal(this.api.findContracts, {counterparty:counterparty.id}, function(a){
            console.log(a);
            Object.assign(deal, a);
//            deal = a;
          });
        }
      },
      getAddress:function(counterparty, deal){
        if (counterparty.name) {
          console.log('Get Address for ' + counterparty.name);
          PostApi(this.api.findAddress, {counterparty: counterparty.id}, function (a) {
            deal.addressList = a;
            if (a.length == 1){
              deal.address = a[0].id
            }
          })
        } else {
          console.log('Clear address list');
          deal.addressList = [];
        }
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
          unit:{
            id:-1
          },
          amount:0,
          price:0,
          shipper:{
            id:this.shippers[0].id
          }
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
          deal.addressList.push(a.address);
          deal.address = a.address.id;
        });
      },
      getPrice:function(deal, product){

      },
      waybillPrint:function(){
        PostReq(this.api.waybill, {id:this.id}, function(a){
          let print = window.open();
          print.document.write(a);
        });
      },
      print:function(deal){

      },
      save:function(){
        let data = {
          id:this.id,
          date:this.date,
          driver:this.driver.id,
          vehicle:this.vehicle.id,
          trailer:this.trailer.id,
          customer:this.customer,
          deals:[]
        };
        for (let i in this.deals){
          if (this.deals.hasOwnProperty(i)){
            let d = this.deals[i];
            let deal = {
              id: d.id,
              from:this.date,
              to:this.date,
              counterparty: d.counterparty.id,
              address: d.address.id,
              products: [],
              index:i
            };
            for (let j in d.products){
              if (d.products.hasOwnProperty(j)){
                let p = d.products[j];
                deal.products.push({
                  id: p.id,
                  amount: p.amount,
                  unit: p.unit.id,
                  type:this.type,
                  price: p.price,
                  product: p.product.id,
                  shipper: p.shipper.id
                });
              }
            }
            data.deals.push(deal)
          }
        }
        PostApi(this.api.save, data, function(a){
          if(a.status === 'success'){
            closeModal();
          }
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
  <c:forEach items="${customers}" var="customer">
  editor.customers.push({
    id:'${customer}',
    name:'<fmt:message key="${customer}"/>'
  });
  </c:forEach>
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
  <c:choose>
  <c:when test="${not empty transportation}">
//  TRANSPORTATION INIT HERE
  editor.id = '${transportation.id}';
  editor.customer = '${transportation.customer}';
  <c:forEach items="${transportation.documents}" var="document">
  var deal = JSON.parse('${document.getContract().toJson()}');
  <%--deal.id=${document.getContract().id};--%>
  console.log(deal);
  deal.addressList = [];
  editor.getAddress(deal.counterparty, deal);
//  console.log(deal);
  editor.deals.push(deal);
  </c:forEach>
  </c:when>
  </c:choose>
</script>
<table id="editor" width="680px" class="editor">
  <tr>
    <td>
      <fmt:message key="date"/>
    </td>
    <td>

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
      <fmt:message key="pallets"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="height: 290pt; width: 100%; font-size: 10pt; overflow-y: scroll">
        <div v-for="(deal, idx) in deals" style="border: solid 1pt; margin: 1pt; padding: 2pt">
          <div style="height: 20px">
            <span class="mini-close">
              &times;
            </span>
            <span>
              {{idx+1}}.
            </span>
            <object-input :props="counterpartyProps" :object="deal.counterparty" :item="deal"></object-input>
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
              <span class="mini-close">
                <fmt:message key="document.print"/>
              </span>
              <fmt:message key="amount.total"/>:
              {{total(deal.products).toLocaleString()}}
            </span>
          </div>
          <table style="font-size: 10pt; border-collapse: collapse">
            <tr>
              <th style="width: 280pt">
                <fmt:message key="deal.product"/>
              </th>
              <th style="width: 150pt">
                <fmt:message key="seal.quantity"/>
              </th>
              <th style="width: 200pt">
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
                <select v-model="product.unit.id">
                  <option value="-1" disabled>
                    !!
                  </option>
                  <option v-for="unit in units" :value="unit.id">
                    {{unit.name}}
                  </option>
                </select>
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
        <a v-on:click="addDeal">
          + <fmt:message key="deal.add"/>
        </a>
        <span class="mini-close" v-on:click="waybillPrint">
          <fmt:message key="print.waybill"/>
        </span>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
