<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 04.01.2020
  Time: 10:41
--%>
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
  <c:choose>
  <c:when test="${not empty analyses.id}">
  editor.oil = {
    id:${analyses.id},
    date:new Date('${analyses.turn.turn.date}').toISOString().substring(0, 10),
    scree:${analyses.scree},
    density:${analyses.density},
    humidity:${analyses.humidity},
    length:${analyses.length},
    diameter:${analyses.diameter},
    match:${analyses.match}
  };
  </c:when>
  <c:otherwise>
  editor.oil = {
    date:new Date().toISOString().substring(0, 10),
    scree:0,
    density:0,
    humidity:0,
    length:0,
    diameter:0,
    match:false
  };
  </c:otherwise>
  </c:choose>
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
      <input id="date" readonly v-on:click="datePicker()"
             v-model="new Date(oil.date).toLocaleDateString()" style="width: 7em">
    </td>
  </tr>
  <tr>
    <td>
      <label for="scree">
        <fmt:message key="meal.scree"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input type="number" id="scree" v-model="oil.scree" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="density">
        <fmt:message key="meal.density"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input type="number" id="density" v-model="oil.density" autocomplete="off">
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
      <input type="number" id="humidity" v-model="oil.humidity" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="length">
        <fmt:message key="meal.length"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input type="number" id="length" v-model="oil.length" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="diameter">
        <fmt:message key="meal.diameter"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input type="number" id="diameter" v-model="oil.diameter" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="3" align="right">
      <span v-on:click="oil.match = !oil.match" style="font-weight: bold">
        <span v-if="oil.match">
          <fmt:message key="match.dstu"/>
        </span>
        <span v-else style="color: orangered">
          <fmt:message key="dsnt.match.dstu"/>
        </span>
      </span>
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
