<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .editor input{
    border: solid gray 1pt;
  }
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
  .row .edit{
    visibility: hidden;
  }
  .row:hover .edit{
    visibility: visible;
  }
</style>
<script src="${context}/vue/reportEdit.vue"></script>
<script>
  var category = null;
  //API
  editor.api.save = '${save}';
  editor.api.preview = '${preview}';

  //CATEGORIES
  <c:forEach items="${categories}" var="category">
  editor.addCategory({
    id:${category.id},
    title:'${category.title}',
    number:${category.number},
    summary:${category.summary},
    fields:[]
  });
  </c:forEach>

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

  //FIELDS
  <c:forEach items="${fields}" var="field">
  editor.addField({
    id:${field.id},
    title:'${field.title}',
    value:${field.value},
    unit:${field.unit.id},
    index:${field.index},
    comment:'${field.comment}'
  },
  ${field.category.id},
  '${field.category.title}',
  ${field.category.number});

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
  //PRODUCTS
  <c:forEach items="${products}" var="product">
  editor.products.push({
    id:${product.id},
    name:'${product.name}'
  });
  </c:forEach>
  editor.initField();
  editor.initCategory();
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="edit" class="editor" style="border-collapse: collapse; width: 390px; font-size: 10pt">
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
    <template v-for="category in categories">
      <tr v-if="category.title" style="font-weight: bold; border-bottom: solid gray 1pt">
        <td colspan="2">
          {{category.title}}
          <span v-if="category.summary">
            <span v-for="unit in getUnits(category.fields)">
              {{summary(category, unit).toLocaleString()}} {{unit.name}}
            </span>
          </span>
        </td>
      </tr>
      <tr v-for="field in category.fields">
        <td>
          <div class="field-title">
            <span>
              {{field.title}}
            </span>
            <div v-if="!field.editComment && !field.comment" class="mini-close add-comment"
              style="font-size: 8pt" v-on:click="openComment(field)" >
              <span>
                <fmt:message key="comment.plus"/>
              </span>
            </div>
          </div>
        </td>
        <td>
          <input type="number" v-model="field.value" autocomplete="off" onfocus="this.select()">
          <select v-model="field.unit">
            <option v-for="unit in units" :value="unit.id">
              {{unit.name}}
            </option>
          </select>
        </td>
      </tr>
    </template>
  <%--<template v-for="(field, key) in fields">--%>
    <%--&lt;%&ndash;--&ndash;%&gt;--%>
    <%--&lt;%&ndash;CATEGORY NAME&ndash;%&gt;--%>
    <%--&lt;%&ndash;--&ndash;%&gt;--%>
    <%--<tr v-if="key == 0 && field.category ||--%>
    <%--key > 0 && !fields[key - 1].category && field.category ||--%>
    <%--key > 0 && fields[key - 1].category && field.category && fields[key - 1].category.id != field.category.id">--%>
      <%--<td colspan="2" align="right">--%>
        <%--<b>--%>
          <%--&nbsp;{{field.category.title}}--%>
        <%--</b>--%>
      <%--</td>--%>
    <%--</tr>--%>
      <%--<template>--%>
        <%--&lt;%&ndash;--&ndash;%&gt;--%>
        <%--&lt;%&ndash;FIELD NAME&ndash;%&gt;--%>
        <%--&lt;%&ndash;--&ndash;%&gt;--%>
        <%--<tr class="row">--%>
          <%--<td>--%>
            <%--<div class="field-title">--%>
          <%--<span>--%>
            <%--{{key + 1}}. {{field.title}}--%>
          <%--</span>--%>
              <%--<div v-if="!field.editComment && !field.comment" class="mini-close add-comment"--%>
                   <%--style="font-size: 8pt" v-on:click="openComment(field)" >--%>
            <%--<span>--%>
              <%--<fmt:message key="comment.plus"/>--%>
            <%--</span>--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</td>--%>
          <%--&lt;%&ndash;--&ndash;%&gt;--%>
          <%--&lt;%&ndash;FIELD VALUE&ndash;%&gt;--%>
          <%--&lt;%&ndash;--&ndash;%&gt;--%>
          <%--<td>--%>
            <%--<input type="number" v-model="field.value" onfocus="this.select()">--%>
            <%--<select v-model="field.unit">--%>
              <%--<option disabled value="-1"></option>--%>
              <%--<option v-for="unit in units" :value="unit.id">--%>
                <%--{{unit.name}}--%>
              <%--</option>--%>
            <%--</select>--%>
            <%--<span class="mini-close edit" v-on:click="editField(field)">--%>
              <%--!--%>
            <%--</span>--%>
          <%--</td>--%>
        <%--</tr>--%>
      <%--</template>--%>

    <%--&lt;%&ndash;--&ndash;%&gt;--%>
    <%--&lt;%&ndash;COMMENT&ndash;%&gt;--%>
    <%--&lt;%&ndash;--&ndash;%&gt;--%>
    <%--<tr class="field-title">--%>
      <%--<td colspan="2">--%>
        <%--<div v-if="field.editComment">--%>
          <%--<input v-model="commentInput" style="border: none; width: 100%; font-size: 8pt" ref="commentInput"--%>
                 <%--v-on:keyup.enter="editComment(key)" v-on:keyup.escape="field.editComment = false">--%>
          <%--<span class="comment-button-container">--%>
            <%--<span class="mini-close comment-button" style="right: 8pt; color: darkgreen; font-size: 8pt" v-on:click="editComment(key)">--%>
              <%--&#10003;--%>
            <%--</span>--%>
            <%--<span class="mini-close comment-button" v-on:click="field.editComment = false">--%>
              <%--&times;--%>
            <%--</span>--%>
          <%--</span>--%>
        <%--</div>--%>
        <%--<div v-else>--%>
          <%--<span v-if="field.comment" class="mini-close" v-on:click="openComment(field)" style="font-size: 8pt">--%>
            <%--{{field.comment}}--%>
        <%--</span>--%>
        <%--</div>--%>
      <%--</td>--%>
    <%--</tr>--%>
  <%--</template>--%>
    <%------%>
    <%--NEW BUTTONS--%>
    <%------%>
  <tr v-if="!newField && !newCategory">
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
