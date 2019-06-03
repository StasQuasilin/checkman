<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var edit = new Vue({
    el: '#editor',
    data:{
      turn:{},
      turns:[]
    },
    methods:{
      add:function(){
        this.turn.personal.push({

        })
      },
      save:function(){

      }
    }
  });
  <c:forEach items="${turns}" var="turn">
  edit.turns.push({
    id:${turn.id},
    name:'<fmt:message key="turn"/> ${turn.number}'
  });
  </c:forEach>
  edit.turn = {
    date:new Date().toISOString().substring(0, 10),
    turn:-1,
    personal:[]
  }
</script>
<table style="width: 100%" id="editor">
  <tr>
    <td>
      <label for="date">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      <input id="date" readonly style="width: 7em" v-model="new Date(turn.date).toLocaleDateString()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="turn">
        <fmt:message key="turn"/>
      </label>
    </td>
    <td>
      <select id="turn" style="width: 100%" v-model="turn.turn">
        <option v-for="turn in turns" :value="turn.id">
          {{turn.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="right">
      <span class="mini-close" v-on:click="add">
        +&nbsp;<fmt:message key="button.add"/>
      </span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div v-for="(value, key) in turn.personal">
        <span class="mini-close">
          &times;
        </span>
        {{value}}
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="menu.cancel"/>
      </button>
      <button v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
