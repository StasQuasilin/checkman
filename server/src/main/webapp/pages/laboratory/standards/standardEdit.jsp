<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 18.12.2019
  Time: 13:19
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var editor = new Vue({
    el:'#editor',
    data:{
      api:{},
      products:[],
      protocol:{
        id:-1,
        date:new Date().toISOString().substring(0, 10),
        product:-1,
        number:''
      }
    },
    methods:{
      pickDate:function(){
        const self = this;
        datepicker.show(function(date){
          self.protocol.date = date;
        }, this.protocol.date);
      },
      save:function(){
        if (this.product != -1){
          const self = this;
          PostApi(this.api.save, this.protocol, function(a){
            if (a.status === 'success'){
              closeModal();
              loadContent(self.api.update);
            }
          })
        }
      }
    }
  });
  editor.api.save = '${save}';
  editor.api.update = '${update}';
  <c:forEach items="${products}" var="product">
  editor.products.push(JSON.parse('${product.toJson()}'));
  </c:forEach>
</script>
<table id="editor">
  <tr>
    <td>
      <fmt:message key="date"/>
    </td>
    <td>
      <a v-on:click="pickDate()">{{new Date(protocol.date).toLocaleString().substring(0, 10)}}</a>
    </td>
  </tr>
  <tr>
    <td>
      <label for="product">
        <fmt:message key="deal.product"/>
      </label>
    </td>
    <td>
      <select id="product" v-model="protocol.product">
        <option value="-1">
          <fmt:message key="need.select"/>
        </option>
        <option v-for="product in products" :value="product.id">
          {{product.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="number">
        <fmt:message key="transportation.automobile.number"/>
      </label>
    </td>
    <td>
      <input id="number" v-model="protocol.number" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
