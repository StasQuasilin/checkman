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
      answer:{}
    },
    methods:{
      keys:function(){
        return Object.keys(this.answer);
      },
      check:function(){
        const self = this;
        PostApi(this.api.check, {value:this.input}, function(a){
          console.log(a);
          self.answer = a.result;
        })
      }
    }
  });
  checker.api.check= '${check}';
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
  <tr v-for="key in keys()">
    <td>
      {{key}}
    </td>
    <td>
      {{answer[key]}}
    </td>
  </tr>
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
