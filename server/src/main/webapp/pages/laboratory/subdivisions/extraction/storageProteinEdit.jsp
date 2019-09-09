<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <link rel="stylesheet" href="${context}/css/editor.css">
  <script src="${context}/vue/laboratory/extractionCrude.vue"></script>
  <script>
    editor.api.save = '${save}';
    editor.times = [
      {
        hour:'00',
        minute:30
      },
      {
        hour:'06',
        minute:30
      },
      {
        hour:'12',
        minute:30
      },
      {
        hour:'18',
        minute:30
      }
    ];
    <c:forEach items="${storages}" var="storage">
    editor.storages.push(
      {
        id:${storage.id},
        name:'${storage.name}'
      }
    );
    </c:forEach>
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
      id:${l.id},
      value:'${l.person.value}'
    });
    </c:forEach>
    //${protein.time}
    <c:choose>
    <c:when test="${not empty protein}">

    editor.crude = {
      id:${protein.id},
      date : new Date('${protein.time}').toISOString().substring(0, 10),
      time : editor.currentTime(new Date('${protein.time}')),
      storage: editor.storages[0].id,
      humidity:${protein.humidity},
      protein:${protein.protein},
      nuclear:${protein.nuclearGrease}
    };
    </c:when>
    <c:otherwise>
    editor.crude = {
      date : new Date().toISOString().substring(0, 10),
      time : editor.currentTime(),
      storage: editor.storages[0].id,
      humidity:0,
      protein:0,
      nuclear:0
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
      <input id="date" readonly style="width: 7em" v-on:click="datePicker"
             v-model="new Date(crude.date).toLocaleDateString()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="time">
        <fmt:message key="time"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <select id="time" v-model="crude.time">
        <option v-for="time in times">
          {{time.hour}}:{{time.minute}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="storage">
        <fmt:message key="storage"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <select id="storage" v-model="crude.storage">
        <option v-for="storage in storages" :value="storage.id">
          {{storage.name}}
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
      <input id="protein" type="number" v-model="crude.protein" step="0.1" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="humidity">
        <fmt:message key="extraction.crude.humidity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="humidity" type="number" v-model="crude.humidity" step="0.1" autocomplete="off">
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
      <input id="nuclear" type="number" step="0.01" autocomplete="off" v-model="crude.nuclear"/>
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
