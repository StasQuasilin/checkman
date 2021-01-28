<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/laboratory/laboratoryTurnEdit.vue"></script>
<script>
  edit.api.save = '${save}';
  <c:forEach items="${turns}" var="turn">
  edit.turns.push({
    id:${turn.id},
    name:'<fmt:message key="turn"/> ${turn.number} ',
    number: ${turn.number}
  });
  </c:forEach>
  <c:forEach items="${laborants}" var="l">
  edit.laborants.push({
    id:${l.id},
    name:'${l.person.value}'
  });
  </c:forEach>
  <c:choose>
  <c:when test="${not empty turn.id}">
  var turnId = -1;
  for(var t in edit.turns){
    if (edit.turns.hasOwnProperty(t)){
      if (edit.turns[t].number == ${turn.number}){
        turnId = edit.turns[t].id;
      }
    }
  }
  edit.turn = {
    date:new Date('${turn.date}').toISOString().substring(0, 10),
    turn:turnId,
    personal:[
      <c:forEach items="${laboratory}" var="l">
      {
        id:${l.worker.id},
        name:'${l.worker.person.value}'
      },
      </c:forEach>
    ]
  };
  </c:when>
  <c:otherwise>
  edit.turn = {
    date:new Date().toISOString().substring(0, 10),
    turn:-1,
    personal:[]
  };
  </c:otherwise>
  </c:choose>
</script>
<table style="width: 100%" id="editor">
  <tr>
    <td>
      <label for="date">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      <input id="date" readonly style="width: 7em"
             v-model="new Date(turn.date).toLocaleDateString()" v-on:click="pickDate">
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
      <span class="mini-close" v-on:click="openInput">
        +&nbsp;<fmt:message key="button.add"/>
      </span>
    </td>
  </tr>
  <tr>
    <c:set var="title"><fmt:message key="transport.insert.infortation"/> </c:set>
    <td colspan="2">
      <div v-for="(value, key) in turn.personal">
        <span class="mini-close" v-on:click="remove(key)">
          &times;
        </span>
        {{key+1}}.{{value.name}}
      </div>
      <div v-if="isInput" >
        <input title="${title}" style="width: 100%" v-model="inp">
        <div class="custom-data-list">
          <div class="custom-data-list-item" v-for="l in find()" v-on:click="add(l)">
            {{l.name}}
          </div>
        </div>
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
