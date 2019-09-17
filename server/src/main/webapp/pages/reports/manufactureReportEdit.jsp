<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .field-title .comment-button{
    visibility: hidden;
  }
  .field-title:hover .comment-button{
    visibility: visible;
  }
</style>
<script src="${context}/vue/reportEdit.vue"></script>
<script>
  //API
  editor.api.save = '${save}';
  //REPORT
  <c:choose>
  <c:when test="${not empty report}">
  editor.report = {
    id:${report.id},
    date:new Date('${report.turn.date}').toISOString().substring(0, 10),
    turn:${report.turn.number}
  };
  </c:when>
  <c:otherwise>
  editor.report = {
    date:new Date().toISOString().substring(0, 10),
    turn:-1
  };
  </c:otherwise>
  </c:choose>
  //CATEGORIES
  <%--<c:forEach items="${categories}" var="category">--%>
  <%--editor.categories[${category.id}] = {--%>
    <%--id:${category.id},--%>
    <%--title:'${category.title}',--%>
    <%--number:${category.number}--%>
  <%--};--%>
  <%--</c:forEach>--%>
  //STORAGES
  <c:forEach items="${storages}" var="storage">
  editor.storages[${storage.id}] = {
    id:${storage.id},
    title:'${storage.name}'
  };
  </c:forEach>
  //TURNS
  <c:forEach items="${turns}" var="turn">
  editor.turns.push({
    id:${turn.id},
    title:'<fmt:message key="turn"/> #${turn.number}',
    number:${turn.number}
  });
  </c:forEach>
  //UNITS
  <c:forEach items="${units}" var="unit">
  editor.units.push({
    id:${unit.id},
    name:'${unit.name}'
  });
  </c:forEach>
  //FIELDS
  <c:forEach items="${fields}" var="field">
  var category = null;
  <c:if test="${not empty field.category.id}">
  category = {
    id:${field.category.id},
    title:'${field.category.title}',
    number:${field.category.number}
  };
  </c:if>
  editor.addField({
    setting:${field.id},
    title:'${field.title}',
    unit:${field.unit.id},
    //FIELD CATEGORY
  <c:choose>
  <c:when test="${not empty field.category.id}">
    category:${field.category.id},
  </c:when>
  </c:choose>
    //FIELD STORAGE
  <c:choose>
    <c:when test="${not empty field.storage.id}">
    storage:${field.storage.id}
    </c:when>
  </c:choose>
  }, category);
  </c:forEach>
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="edit" class="editor" style="border-collapse: collapse">
  <tr>
    <td>
      <label for="date">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      <input id="date" readonly style="width: 7em" v-model="new Date(report.date).toLocaleDateString().substring(0, 10)">
    </td>
  </tr>
  <tr>
    <td>
      <label for="turn">
        <fmt:message key="turn"/>
      </label>
    </td>
    <td>
      <select id="turn" style="width: 100%" v-model="report.turn" :class="{error : errors.turn}" v-on:click="errors.turn = false">
        <option disabled value="-1"><fmt:message key="need.select"/></option>
        <option v-for="turn in turns" :value="turn.number">
          {{turn.title}}
        </option>
      </select>
    </td>
  </tr>
  <template v-for="category in getCategories()">
    <tr v-if="category">
      <th colspan="2">
        {{category.title}}
      </th>
    </tr>
    <template v-for="(field, key) in getFields(category)">
      <tr>
        <td valign="top" class="field-title">
          <label :for="field.id">
            {{field.title}}
          <span v-if="field.storage">
            {{storages[field.storage].title}}
          </span>
          </label>

        </td>
        <td valign="top">
          <input :id="field.id" type="number" v-model="field.value" style="border-radius: 8pt 0 0 8pt">
          <select v-model="field.unit">
            <option v-for="unit in units" :value="unit.id">
              {{unit.name}}
            </option>
          </select>
        </td>
      </tr>
      <%--<tr>--%>
        <%--<td colspan="2">--%>
          <%--<div class="mini-close comment-button" style="font-size: 8pt" v-on:click="editComment(key, true)">--%>
            <%--<fmt:message key="comment.plus"/>--%>
          <%--</div>--%>
        <%--</td>--%>
      <%--</tr>--%>

      <tr>
        <td colspan="2">
         {{field.setting}} {{settings[field.setting].comment}}
        </td>
      </tr>
    </template>
  </template>
  <tr>
    <td colspan="2" align="center">
      <button class="close-button left-button" onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button class="save-button right-button" v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
