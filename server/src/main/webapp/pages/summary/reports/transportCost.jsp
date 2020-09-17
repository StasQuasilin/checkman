<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 25.11.2019
  Time: 10:35
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script>
  var print = new Vue({
    el:'#print',
    components:{
      'object-input':objectInput
    },
    data:{
      api:{
        print:'${print}'
      },
      already:false,
      from:new Date().toISOString().substring(0, 10),
      to:new Date().toISOString().substring(0, 10),
      organisation:{},
      drivers:[],
      driver:{id:-1},
      vehicle:'',
      product:-1,
      products:[],
      err:{
        organisation:false,
        driver:false
      },
      organisationProps :{
        find:'${findOrganisation}',
        header:'<fmt:message key="button.add"/>',
        put:function(org){
          print.organisation = org
        },
        show:['value']
      },
      driverProps:{
        find:'${findDriver}',
        header:'<b>++<fmt:message key="button.add"/></b>',
        put:function(driver){
          print.drivers.push(driver);
        },
        show:['person/value']
      }
    },
    methods:{
      selectFrom:function(){
        const self = this;
        datepicker.show(function(date){
          self.from = date;
        }, this.from)
      },
      selectTo:function(){
        const self = this;
        datepicker.show(function(date){
          self.to = date;
        }, this.to)
      },
      removeDriver:function(idx){
        this.drivers.splice(idx, 1);
      },
      print:function(){
        if (!this.already) {
          this.already = true;
          let params = {};
          if (this.from) {
            params.from = this.from;
          }
          if (this.to) {
            params.to = this.to;
          }
          if (this.organisation) {
            params.organisation = this.organisation.id
          }

          params.drivers = [];
          for (let i in this.drivers){
            if (this.drivers.hasOwnProperty(i)){
              params.drivers.push(this.drivers[i].id)
            }
          }

          if (this.vehicle) {
            params.vehicleContain = this.vehicle;
          }
          if (this.product !== -1) {
            params.product = this.product;
          }
          const self = this;

          PostReq(this.api.print, params, function (a) {
            self.already = false;
            let print = window.open();
            print.document.write(a);
            print.print();
          })
        }
      }
    }
  });
  <c:forEach items="${products}" var="product">
  print.products.push({
    id:'${product.id}',
    name:'${product.name}'
  });
  </c:forEach>
</script>
<table id="print" style="width: 280pt">
  <tr>
    <td>
      <label for="from">
        <fmt:message key="date.from"/>
      </label>
    </td>
    <td width="100%">
      <input id="from" readonly style="width: 7em" v-on:click="selectFrom()"
             v-model="new Date(from).toLocaleDateString().substring(0, 10)">
    </td>
  </tr>
  <tr>
    <td>
      <label for="to">
        <fmt:message key="date.to"/>
      </label>
    </td>
    <td>
      <input id="to" readonly style="width: 7em" v-on:click="selectTo()"
             v-model="new Date(to).toLocaleDateString().substring(0, 10)">
    </td>
  </tr>
  <tr style="font-size: 10pt">
    <td>
      <label for="products">
        <fmt:message key="deal.product"/>
      </label>
    </td>
    <td>
      <select id="products" v-model="product">
        <option value="-1">
          <fmt:message key="all"/>
        </option>
        <option v-for="product in products" :value="product.id">
          {{product.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr :class="{error : err.organisation}" style="font-size: 10pt">
    <td>
      <fmt:message key="deal.organisation"/>
    </td>
    <td>
      <object-input :props="organisationProps" :object="organisation"></object-input>
    </td>
  </tr>
  <tr :class="{error : err.driver}" style="font-size: 10pt">
    <td>
      <fmt:message key="transportation.driver"/>
    </td>
    <td>
      <span v-for="(d, dIdx) in drivers">
        <span style="padding-left: 4pt">
          {{d.person.value}}
        </span>
        <span v-on:click="removeDriver(dIdx)">
          &times;
        </span>
        <template v-if="dIdx < drivers.length - 1">,</template>
      </span>
      <object-input :props="driverProps" :object="driver"></object-input>
    </td>
  </tr>
  <tr style="font-size: 10pt">
    <td>
      <label for="contain">
        <fmt:message key="transportation.automobile"/> <fmt:message key="contain"/>
      </label>
    </td>
    <td>
      <input id="contain" v-model="vehicle" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-if="already" disabled>
        <fmt:message key="document.print"/>...
      </button>
      <button v-on:click="print()" v-else>
        <fmt:message key="document.print"/>
      </button>
    </td>
  </tr>
</table>
</html>
