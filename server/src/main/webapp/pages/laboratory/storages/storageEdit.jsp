<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script>
  var editor = new Vue({
    el: '#editor',
    data:{
      api:{},
      storages:[],
      analyses:{}
    },
    methods:{
      save:function(){
        var year = this.analyses.date.getFullYear();
        var month = this.analyses.date.getMonth();
        var date = this.analyses.date.getDate();
        this.analyses.convert = year + '-' + (month + 1) + '-' + date + ' ' + this.analyses.date.toLocaleTimeString();
        console.log(this.analyses);
        PostApi(this.api.save, this.analyses, function(a){
          if (a.status === 'success'){
            closeModal();
          }
        })
      },
      selectDate:function(){
        const self = this;
        datepicker.show(function(a){
          var date = new Date(a);
          self.analyses.date = new Date(
              date.getFullYear(),
              date.getMonth(),
              date.getDate(),
              self.analyses.date.getHours(),
              self.analyses.date.getMinutes(),
              0,0
          )
        }, this.analyses.date.toISOString().substring(0, 10));
      },
      selectTime:function(){
        const self = this;
        timepicker.show(function(a){
          self.analyses.date = new Date(
              self.analyses.date.getFullYear(),
              self.analyses.date.getMonth(),
              self.analyses.date.getDate(),
            a.getHours(),
            a.getMinutes(),
            0,0
          )
        }, this.analyses.date.getHours(), this.analyses.date.getMinutes())
      }
    }
  });
  editor.api.save ='${edit}';
  <c:forEach items="${storages}" var="storage">
  editor.storages.push({
    id:${storage.id},
    name:'${storage.name}'
  });
  </c:forEach>
  editor.analyses={
    storage:-1,
    date:new Date(),
    phosphorus:0,
    acid:0,
    peroxide:0,
    color:0
  }
</script>
<table id="editor" class="editor">
  <tr>
    <td>
      <label for="storage">
        <fmt:message key="storage.${type}"/>
      </label>
    </td>
    <td>
      <select id="storage" style="width: 100%" v-model="analyses.storage">
        <option disabled value="-1"><fmt:message key="need.select"/></option>
        <option v-for="storage in storages" :value="storage.id">
          {{storage.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="date">
        <fmt:message key="select.date"/>
      </label>
    </td>
    <td v-if="analyses.date">
      <input id="date" readonly v-model="analyses.date.toLocaleDateString()" v-on:click="selectDate" style="width: 7em">
      <input readonly v-model="analyses.date.toLocaleTimeString().substring(0,5)" style="width: 4.4em" title="Time" v-on:click="selectTime">
    </td>
  </tr>
  <tr>
    <td>
      <label for="phosphorus">
        <fmt:message key="oil.phosphorus.mass.fraction"/>
      </label>
    </td>
    <td>
      <input id="phosphorus" type="number" step="0.01" v-model="analyses.phosphorus" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="acid">
        <fmt:message key="sun.acid.value"/>
      </label>
    </td>
    <td>
      <input id="acid" type="number" step="0.01" v-model="analyses.acid" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="peroxide">
        <fmt:message key="oil.peroxide"/>
      </label>
    </td>
    <td>
      <input id="peroxide" type="number" step="0.01" v-model="analyses.peroxide" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="color">
        <fmt:message key="oil.color.value"/>
      </label>
    </td>
    <td>
      <input id="color" type="number" step="1" v-model="analyses.color" onfocus="this.select()">
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
