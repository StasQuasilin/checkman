<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 16.12.2019
  Time: 16:33
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/editor.css"/>
<script>
  var corrector = new Vue({
    el:'#correct',
    data:{
      api:{},
      id:-1,
      date:new Date().toISOString().substring(0, 10),
      storages:[],
      open:false,
      selectedStorage:-1,
      alreadySave:false
    },
    methods:{
      getCorrected:function(val){
        return this.storages.filter(function(item){
          return item.corrected == val;
        })
      },
      openInput:function(){
        this.open = !this.open;
      },
      selectStorage:function(){
        this.storages[this.selectedStorage].corrected = true;
        this.open = false;
        this.selectedStorage = -1;
      },
      shipperSum:function(product, field){
        let sum = 0;
        product.values.forEach(function(item){
          sum += parseFloat(item[field]);
        });
        return sum;
      },
      selectDate:function(){
        const self = this;
        datepicker.show(function(date){
          self.date = date
        }, this.date)
      },
      save:function(){
        if (!this.alreadySave) {
          this.alreadySave = true;
          var res = [];
          this.getCorrected(true).forEach(function (item) {
            Object.values(item.values).forEach(function (product) {
              product.values.forEach(function (shipper) {
                if (shipper.value != shipper.correction) {
                  res.push({
                    id: item.id,
                    storage: item.id,
                    product: product.id,
                    shipper: shipper.shipper.id,
                    correction: shipper.correction - shipper.value
                  })
                }
              });

            });
          });
          const self = this;
          PostApi(this.api.save, {id: this.id, date: this.date, values: res}, function (a) {
            self.alreadySave = true;
            if (a.status === 'success') {
              closeModal();
            }
          })
        }
      }
    }
  });
  var storage;
  var product;
  <c:forEach items="${stocks}" var="stock">
  storage = JSON.parse('${stock.key.toJson()}');
  storage.corrected = false;
  storage.values = {};
  <c:forEach items="${stock.value}" var="product">
  product = JSON.parse('${product.key.toJson()}');
  product.value = 0;
  product.values = [];
  <c:forEach items="${product.value}" var="shipper">
  var n = ${shipper.value};
  var v = Math.round(n * 100) / 100;
  product.values.push({
    shipper:JSON.parse('${shipper.key.toJson()}'),
    value: v,
    correction:v
  });
  </c:forEach>

  if (!storage.values[product.id]){
    storage.values[product.id] = product;
  }
  </c:forEach>
  corrector.storages.push(storage);
  </c:forEach>
  corrector.api.save='${save}';
</script>
<html>
  <table id="correct" class="editor" style="width: 544px">
    <tr>
      <td colspan="3">
        <fmt:message key="date"/>
        <span class="mini-close" v-on:click="selectDate">
          {{new Date(date).toLocaleDateString()}}
        </span>
      </td>
    </tr>
    <template v-for="(storage, storageIdx) in getCorrected(true)">
      <tr>
        <td style="background-color: darkgray" colspan="3">
          {{storage.name}}
        </td>
      </tr>
      <template v-for="product in storage.values">
        <tr>
          <td style="padding-left: 8pt; width: 180px">
            {{product.name}}
          </td>
          <td style="width: 120px">
            {{shipperSum(product, 'value').toLocaleString()}}
          </td>
          <td style="width: 220px">
            {{shipperSum(product, 'correction').toLocaleString()}}
          </td>
        </tr>
        <tr style="font-size: 10pt" v-for="shipper in product.values">
          <td align="right">
            {{shipper.shipper.name}}:
          </td>
          <td>
            {{shipper.value.toLocaleString()}}
          </td>
          <td>
            <input type="number" style="width: 7em" v-model="shipper.correction" onfocus="this.select()">
            <span>
            {{(shipper.correction-shipper.value).toLocaleString()}}&nbsp;{{product.unit.name}}
            </span>
          </td>
        </tr>
        <tr>
          <td colspan="3" style="border-bottom: solid gray 1pt"></td>
        </tr>
      </template>
    </template>
    <tr v-if="open">
      <td colspan="3">
        <select style="width: 100%" v-on:change="selectStorage()" v-model="selectedStorage">
          <option disabled value="-1">
            <fmt:message key="need.select"/>
          </option>
          <option v-for="(storage, storageIdx) in storages" :value="storageIdx" v-if="!storage.corrected">
            {{storageIdx + 1}}: {{storage.name}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="right">
        <span class="mini-close" v-on:click="openInput()">
          <template v-if="open">
            <fmt:message key="button.cancel"/>
          </template>
          <template v-else>
            <fmt:message key="button.add"/>
          </template>
        </span>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center">
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
