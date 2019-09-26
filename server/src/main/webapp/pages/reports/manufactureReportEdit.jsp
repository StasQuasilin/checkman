<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .field-title{
    position: relative;
  }
  .field-title .add-comment{
    visibility: hidden;
  }
  .field-title:hover .add-comment{
    visibility: visible;
  }
  .add-comment{
    position: absolute;
    font-size: 8pt;
    top: 10pt;
    background-color: white;
  }
  .comment-button-container{
    position: relative;
  }
  .comment-button{
    font-size: 10pt;
    color: orangered;
    position: absolute;
    right: 0;
    top: 3pt;
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
  <c:forEach items="${categories}" var="category">
  editor.categories.push({
    id:${category.id},
    title:'${category.title}',
    number:${category.number}
  });
  </c:forEach>
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
    category:category,
  </c:when>
  </c:choose>
    //FIELD STORAGE
  <c:choose>
    <c:when test="${not empty field.storage.id}">
    storage:${field.storage.id}
    </c:when>
  </c:choose>
  });
  </c:forEach>
  editor.initField();
  editor.initCategory();
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="edit" class="editor" style="border-collapse: collapse; width: 360px">
  <%------%>
  <%--DATE--%>
  <%------%>
  <tr>
    <td>
      <label for="date">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      <input id="date" readonly style="width: 7em" v-on:click="selectDate()" v-on:dblclick="selectDate()"
             v-model="new Date(report.date).toLocaleDateString().substring(0, 10)">
    </td>
  </tr>
  <%------%>
  <%--TURN--%>
  <%------%>
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
  <template v-for="(field, key) in fields">
    <%------%>
    <%--CATEGORY NAME--%>
    <%------%>
    <tr v-if="key == 0 && field.category ||
    key > 0 && !fields[key - 1].category && field.category ||
    key > 0 && fields[key - 1].category && field.category && fields[key - 1].category.id != field.category.id">
      <td colspan="2" align="right">
        <b>
          &nbsp;{{field.category.title}}
        </b>
      </td>
    </tr>
    <%------%>
    <%--FIELD NAME--%>
    <%------%>
    <tr>
      <td>
        <div class="field-title">
          <span>
            {{key + 1}}. {{field.title}}
          </span>
          <span v-if="field.storage">
            {{storages[field.storage].title}}
          </span>
          <div v-if="!field.editComment && !field.comment" class="mini-close add-comment" style="font-size: 8pt" v-on:click="openComment(field)" >
            <span>
              <fmt:message key="comment.plus"/>
            </span>
          </div>
        </div>
      </td>
      <%------%>
      <%--FIELD VALUE--%>
      <%------%>
      <td>
        <input type="number" v-model="field.value" onclick="this.select()">
        <select v-model="field.unit">
          <option disabled value="-1"></option>
          <option v-for="unit in units" :value="unit.id">
            {{unit.name}}
          </option>
        </select>
      </td>
    </tr>
    <%------%>
    <%--COMMENT--%>
    <%------%>
    <tr class="field-title">
      <td colspan="2">
        <div v-if="field.editComment">
          <input v-model="commentInput" style="border: none; width: 100%; font-size: 8pt" ref="commentInput"
                 v-on:keyup.enter="editComment(key)" v-on:keyup.escape="field.editComment = false">
          <span class="comment-button-container">
            <span class="mini-close comment-button" style="right: 8pt; color: darkgreen; font-size: 8pt" v-on:click="editComment(key)">
              &#10003;
            </span>
            <span class="mini-close comment-button" v-on:click="field.editComment = false">
              &times;
            </span>
          </span>
        </div>
        <div v-else>
          <span v-if="field.comment" class="mini-close" v-on:click="openComment(field)" style="font-size: 8pt">
            {{field.comment}}
        </span>
        </div>
      </td>
    </tr>
  </template>
    <%------%>
    <%--NEW BUTTONS--%>
    <%------%>
  <tr>
    <td colspan="2" align="right">
      <span class="mini-close" v-if="!newField" v-on:click="newField=true">
        <fmt:message key="button.add.field"/>
      </span>
      <span class="mini-close" v-if="!newCategory" v-on:click="newCategory=true">
        <fmt:message key="button.add.category"/>
      </span>
    </td>
  </tr>
    <%------%>
    <%--NEW CATEGORY--%>
    <%------%>
  <template v-if="newCategory">
    <tr>
      <td colspan="2" align="right" style="border-top: solid gray 1pt">
        <b>
          <fmt:message key="field.new.category"/>
        </b>
      </td>
    </tr>
    <tr>
      <td>
        <label for="categoryName">
          <fmt:message key="field.category.name"/>
        </label>
      </td>
      <td>
        <input id="categoryName" v-model="category.title" autocomplete="off"
               v-on:click="errors.categoryName = false" :class="{error : errors.categoryName}">
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center" style="border-bottom: solid gray 1pt">
        <span class="mini-close" v-on:click="saveCategory()">
          +
        </span>
        <span class="mini-close" v-on:click="newCategory = false">
          &times;
        </span>
      </td>
    </tr>
  </template>
    <%------%>
    <%--NEW FIELD--%>
    <%------%>
  <template v-if="newField">
    <%--LABEL--%>
    <tr>
      <td colspan="2" align="right" style="border-top: solid gray 1pt">
        <b>
          <fmt:message key="field.edit"/>
        </b>
      </td>
    </tr>
      <%--CATEGORY--%>
    <tr>
      <td>
        <label for="category">
          <fmt:message key="report.category"/>
        </label>
      </td>
      <td>
        <select id="category" v-model="field.category">
          <option value="-1"><fmt:message key="none"/></option>
          <option v-for="(category, categoryKey) in categories" :value="categoryKey">
            {{category.title}}
          </option>
        </select>
      </td>
    </tr>
      <%--NAME--%>
    <tr>
      <td>
        <label for="fieldName">
          <fmt:message key="field.name"/>
        </label>
      </td>
      <td>
        <input id="fieldName" v-model="field.title" autocomplete="off"
               :class="{error : errors.newFieldName}" v-on:click="errors.newFieldName = false">
      </td>
    </tr>
      <%--UNIT--%>
    <tr>
      <td>
        <label for="unit">
          <fmt:message key="unit"/>
        </label>
      </td>
      <td>
        <select id="unit" v-model="field.unit">
          <option v-for="unit in units" :value="unit.id">
            {{unit.name}}
          </option>
        </select>
      </td>
    </tr>
      <%--STORAGE--%>
    <tr>
      <td>
        <label for="storage">
          <fmt:message key="storage.oil"/>
        </label>
      </td>
      <td>
        <select id="storage" v-model="field.storage"
                :class="{error : errors.newFieldName}" v-on:click="errors.newFieldName=false">
          <option value="-1"><fmt:message key="none"/></option>
          <option v-for="(storage, storageKey) in storages" :value="storageKey">
            {{storage.title}}
          </option>
        </select>
      </td>
    </tr>
      <%--NAME ERROR--%>
    <tr v-if="errors.newFieldName">
      <td colspan="2">
        <span class="note error">
          <fmt:message key="field.name.error"/>
        </span>
      </td>
    </tr>
      <%--ONCE--%>
    <tr>
      <td colspan="2" style="width: 100%">
        <div>
          <input id="once" type="checkbox" v-model="field.once">
          <label for="once">
            <fmt:message key="field.once"/>
          </label>
        </div>
        <div style="max-width: 100%">
          <div v-if="field.once" class="note">
            <fmt:message key="field.note.no.save"/>
          </div>
          <div v-else class="note">
            <fmt:message key="field.note.save"/>
          </div>
        </div>
      </td>
    </tr>
      <%--SAVE BUTTONS--%>
    <tr>
      <td colspan="2" align="center" style="border-bottom: solid gray 1pt">
        <span class="mini-close" v-on:click="newField = false">
          &times;
        </span>
        <span class="mini-close" v-on:click="addNewField()">
          &#10003;
        </span>
      </td>
    </tr>
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
