<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.10.2019
  Time: 15:44
  To change this template use File | Settings | File Templates.
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
  <c:when test="${not empty granules.id}">
  editor.oil = {
    id:${granules.id},
    date:new Date('${granules.turn.turn.date}').toISOString().substring(0, 10),
    density:${granules.density},
    humidity:${granules.humidity},
    dust:${granules.dust},
    match:${granules.match}
    
  };
  </c:when>
  <c:otherwise>
  editor.oil = {
    date:new Date().toISOString().substring(0, 10),
    density:0,
    humidity:0,
    dust:0,
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
      <input id="date" readonly v-on:click="pickDate()"
             v-model="new Date(oil.date).toLocaleDateString()" style="width: 7em">
    </td>
  </tr>
  <tr>
    <td>
      <label for="density">
        <fmt:message key="vro.volume.density"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="density" type="number" v-model="oil.density" autocomplete="off">
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
      <input id="humidity" type="number" v-model="oil.humidity" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="dust">
        <fmt:message key="dust"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="dust" type="number" v-model="oil.dust" autocomplete="off">
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
    <td colspan="3" align="center">
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
