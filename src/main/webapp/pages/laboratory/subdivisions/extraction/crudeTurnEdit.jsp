<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
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
      <select id="time" v-model="oil.turn">
        <option v-for="turn in turns" :value="turn.id">
          {{turnDate(turn.day)}}
          {{turn.value}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button>
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>

</html>
