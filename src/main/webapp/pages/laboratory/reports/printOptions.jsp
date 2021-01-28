<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
  var options = new Vue({
    el:'#options',
    data:{
      api:{},
      date:new Date().toISOString().substring(0, 10),
      manufacture:new Date().toISOString().substring(0, 10),
      number:0,
      workers:[],
      worker:-1,
      errors:{
        worker:false
      }
    },
    methods:{
      print:function(){
        this.errors.worker = this.worker == -1;
        if (!this.errors.worker) {
          var param = {
            id: this.id,
            date: this.date,
            manufacture: this.manufacture,
            number: this.number,
            worker: this.worker,
            type: this.type
          };
          console.log(param);
          PostReq(this.api.print, param, function (a) {
            var print = window.open();
            print.document.write(a);
            print.document.close();
            setTimeout(function(){
              print.print();
              print.close();
            }, 1500)
          })
        }
      },
      selectDate:function(){
        const self = this;
        datepicker.show(function(a){
          self.date = a;
        }, this.date)
      },
      selectManufactureDate:function(){
        const self = this;
        datepicker.show(function(a){
          self.manufacture = a;
        }, this.manufacture)
      }
    }
  });
  options.api.print = '${print}';
  options.type = '${type}';
  options.id = '${id}';
  options.number = ${number};
  <c:forEach items="${workers}" var="worker">
  options.workers.push({
    id:${worker.id},
    value:'${worker.value}'
  });
  </c:forEach>
</script>
<div id="options">
  <img src="${context}/images/logo1.png" width="0">
  <table>
    <tr>
      <td>
        <label for="date">
          <fmt:message key="date"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="date" autocomplete="off" readonly
               v-on:click="selectDate()"
               v-model="new Date(date).toLocaleDateString()" style="width: 7em">
      </td>
    </tr>
    <tr>
      <td>
        <label for="number">
          <fmt:message key="act.number"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input type="number" id="number" autocomplete="off" v-model="number" style="width: 7em">
      </td>
    </tr>
    <tr>
      <td>
        <label for="response">
          <fmt:message key="laboratory.response"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <select id="response" style="width: 100%" v-model="worker" v-on:click="errors.worker = false" :class="{error : errors.worker}">
          <option disabled value="-1"><fmt:message key="need.select"/></option>
          <option v-for="worker in workers" :value="worker.id">
            {{worker.value}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="manufacture">
          <fmt:message key="manufacture.date"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="manufacture" readonly v-on:click="selectManufactureDate()" autocomplete="off"
               v-model="new Date(manufacture).toLocaleDateString()" style="width: 7em">
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center">
        <button class="left-button close-button" onclick="closeModal()">
          <fmt:message key="button.cancel"/>
        </button>
        <button class="right-button save-button" v-on:click="print">
          <fmt:message key="document.print"/>
        </button>
      </td>
    </tr>
  </table>
</div>

