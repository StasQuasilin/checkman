<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 25.11.2019
  Time: 10:35
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
      from:new Date().toISOString().substring(0, 10),
      to:new Date().toISOString().substring(0, 10),
      organisation:{},
      driver:{},
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
        header:'<fmt:message key="button.add"/>',
        put:function(driver){
          print.driver = driver;
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
      print:function(){
        this.err.organisation = this.organisation.id;
        this.err.driver = this.driver.id;
        let params = {};
        if (this.from){
          params.from = this.from;
        }
        if (this.to){
          params.to = this.to;
        }
        if (this.organisation){
          params.organisation = this.organisation.id
        }
        if (this.driver){
          params.driver = this.driver.id;
        }
        PostReq(this.api.print, params, function(a){
          var print = window.open();
          print.document.write(a);
          print.print();
        })
      }
    }
  });

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
      <object-input :props="driverProps" :object="driver"></object-input>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="print()">
        <fmt:message key="document.print"/>
      </button>
    </td>
  </tr>
</table>
</html>
