<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/daily.js"></script>
<script>
  editor.api.save = '${save}';
  //    TURNS
  <c:forEach items="${turns}" var="t">
  editor.turns.push({
    number:${t.number},
    begin:'${t.begin}',
    end:'${t.end}'
  });
  </c:forEach>
  editor.turns.sort(function(a, b){
    return a - b;
  });
  //    LABORANTS
  <c:forEach items="${laborants}" var="l">
  editor.laborants.push({
    id:${l.id},
    value:'${l.person.value}'
  });
  </c:forEach>
  //    FORPRESS
  <c:forEach items="${forpress}" var="fp">
  editor.forpress.push({
    id:${fp.id},
    name:'${fp.name}'
  });
  </c:forEach>

  //    BODY
  <c:choose>
  <c:when test="${not empty oil}">
  editor.daily = {
    id:${oil.id},
    date:new Date().toISOString().substring(0, 10),
    seed:${oil.seed},
    husk:${oil.husk},
    creator:${worker.id},
    forpress:[]
  };
  </c:when>
  <c:otherwise>
  editor.daily = {
    date:new Date().toISOString().substring(0, 10),
    seed:0,
    husk:0,
    creator:${worker.id},
    forpress:[]
  };
  </c:otherwise>
  </c:choose>
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="editor" class="editor">
  <c:if test="${not empty oil}">
    <tr>
      <td colspan="3">
        <button :id="daily.id" onclick="editableModal('${delete}')">
          <fmt:message key="button.delete"/>
        </button>
      </td>
    </tr>
  </c:if>
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
      <input id="date" readonly v-on:click="pickDate()" v-model="new Date(daily.date).toLocaleDateString()" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="3">
        <span v-if="turns.length > 0" style="font-size: 10pt">
            <fmt:message key="period"/>: {{info()}}
        </span>
    </td>
  </tr>
  <tr>
    <td>
      <label for="seed">
        <fmt:message key="oil.mass.fraction.seed"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="seed" type="number" step="0.1" v-model="daily.seed" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="husk">
        <fmt:message key="oil.mass.fraction.husk"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="husk" type="number" step="0.1" v-model="daily.husk" autocomplete="off">
    </td>
  </tr>
  <tr>
    <th align="right" colspan="3">
        <span v-if="daily.forpress.length < forpress.length" class="mini-close" v-on:click="addForpress">
        +
        </span>
      <fmt:message key="forpress"/>
    </th>
  </tr>
  <template v-for="(value, key) in daily.forpress">
    <tr>
      <td>
        <span class="mini-close" v-on:click="removeForpress(key)">
            -
        </span>
        <label for="forpress">
          <fmt:message key="forpress"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <select id="forpress" v-model="value.forpress">
          <option v-for="f in forpress" :value="f.id">{{f.name}}</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="oilcake">
          <fmt:message key="oilcake"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="oilcake" type="number" step="0.1" v-model="value.oiliness">
      </td>
    </tr>
  </template>
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
