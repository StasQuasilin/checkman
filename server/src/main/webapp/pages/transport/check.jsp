<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 24.12.2019
  Time: 11:31
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var checker = new Vue({
    el:'#checker',
    data:{
      api:{},
      input:'',
      answer:[],
      isCheck:false,
      noResult:''
    },
    methods:{
      keys:function(){
        return Object.keys(this.answer);
      },
      check:function(){
        const self = this;
        PostApi(this.api.check, {value:this.input}, function(a){
          console.log(a);
          self.isCheck = true;
          self.answer = a.result;
        })
      }
    }
  });
  checker.api.check= '${check}';
  checker.noResult = '<fmt:message key="checker.no.result"/>'
</script>
<table id="checker">
  <tr>
    <td>
      <label for="input">
        <fmt:message key="transportation.automobile.number"/>
      </label>
    </td>
    <td>
      <input id="input" v-model="input" autocomplete="off" onfocus="this.select()">
    </td>
  </tr>
  <template v-if="isCheck">
    <template v-if="answer.length > 0" v-for="(a, key) in answer">
      <tr>
        <td colspan="2" style="background-color: #7fd0d0">
          {{key + 1}}. {{a.brand}}&nbsp;{{a.model}}
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="truck.color"/>
        </td>
        <td>
          {{a.color}}
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="year"/>
        </td>
        <td>
          {{a.year}}
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="truck.document"/>
        </td>
        <td>
          {{a.document}}
        </td>
      </tr>
      <tr>
        <td>
          VIN
        </td>
        <td>
          {{a.vin}}
        </td>
      </tr>
    </template>
    <tr v-else>
      <td colspan="2">
        {{noResult}}
      </td>
    </tr>
  </template>

  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="check()">
        <fmt:message key="button.check"/>
      </button>
    </td>
  </tr>
</table>
</html>
