<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/extractionOil.vue"></script>
<script>
  editor.api.save = '${save}';
  <c:forEach items="${turns}" var="turn">
    editor.turns.push({
      id:${turn.id},
      value:'<fmt:message key="turn"/> #${turn.number}',
      <c:choose>
        <c:when test="${turn.begin lt turn.end}">
          day:0
        </c:when>
        <c:otherwise>
          day:-1
        </c:otherwise>
      </c:choose>
    });
  </c:forEach>
<c:choose>
<c:when test="${not empty protein}">
editor.oil = {
  id:${protein.id},
  date : new Date('${protein.turn.turn.date}').toISOString().substring(0, 10),
  turn : ${protein.turn.turn.number},
  humidity: ${protein.humidity},
  protein:${protein.protein},
  nuclear:${protein.nuclearGrease}
};
</c:when>
<c:otherwise>
editor.oil = {
  date : new Date().toISOString().substring(0, 10),
  turn : -1,
  humidity:0,
  protein:0,
  nuclear:0
};
</c:otherwise>
</c:choose>
  editor.valid = function() {
    return typeof editor.oil.turn != 'undefined';
  }
</script>
<table id="editor" class="editor">
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
      <input id="date" readonly style="width: 7em" v-on:click="datePicker"
             v-model="new Date(oil.date).toLocaleDateString()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="time">
        <fmt:message key="turn"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <select id="time" v-model="oil.turn" :class="{error : err.turn}">
        <option disabled value="-1">
          <fmt:message key="need.select"/>
        </option>
        <option v-for="turn in turns" :value="turn.id">
          {{turnDate(turn.day)}}
          {{turn.value}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="protein">
        <fmt:message key="extraction.raw.protein"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="protein" type="number" step="0.01" autocomplete="off" v-model="oil.protein" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="humidity">
        <fmt:message key="sun.humidity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="humidity" type="number" step="0.01" autocomplete="off" v-model="oil.humidity" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="nuclear">
        <fmt:message key="oil.nmr.grease"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="nuclear" type="number" step="0.01" autocomplete="off" v-model="oil.nuclear" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
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
