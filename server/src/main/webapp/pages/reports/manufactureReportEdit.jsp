<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var editor = new Vue({
    el:'#edit',
    data:{
      api:{},
      fields:{},
      categories:[],
      turns:[],
      units:[],
      report:{}
    },
    methods:{
      addField:function(field){
        var category = null;
        if (field.category){
          category = field.category.id;
        }
        if (!this.fields[category]){
          this.fields[category] = [];
          this.categories.push(field.category);
        }
        field.value = 0;
        field.editComment = false;
        field.comment = '';
        this.fields[category].push(field);
      },
      getFields:function(category){
        if (category){
          return this.fields[category.id];
        } else{
          return this.fields[null];
        }
      },
      getCategories:function(){
        return this.categories;
      },
      editComment:function(field, edit){

          field.editComment = edit;
        console.log(field);
      }
    }
  });
  //REPORT
  <c:choose>
  <c:when test="${not empty report}">

  </c:when>
  <c:otherwise>
  editor.report = {
    date:new Date().toISOString(),
    turn:-1
  };
  </c:otherwise>
  </c:choose>
  //API
  editor.api.save = '${save}';
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
  editor.addField({
    id:${field.id},
    title:'${field.title}',
    unit:{
      id:${field.unit.id},
      name:'${field.unit.name}'
    },
    //FIELD CATEGORY
  <c:choose>
  <c:when test="${not empty field.category.id}">
    category:{
      id:${field.category.id},
      title:'${field.category.title}',
      number:${field.category.number}
    },
  </c:when>
    <c:otherwise>
    category:null,
    </c:otherwise>
  </c:choose>
    //FIELD STORAGE
  <c:choose>
    <c:when test="${not empty field.storage.id}">
    storage:{
      id:${field.storage.id},
      title:'${field.storage.name}'
    }
    </c:when>
    <c:otherwise>
    storage:null
    </c:otherwise>
  </c:choose>
  });
  </c:forEach>
  console.log(editor.fields)
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="edit" class="editor">
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
      <select id="turn" style="width: 100%" v-model="report.turn">
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
    <template v-for="field in getFields(category)">
      <tr>
        <td valign="top">
          <label :for="field.id">
            {{field.title}}
          <span v-if="field.storage">
            {{field.storage.title}}
          </span>
          </label>
        </td>
        <td>
          <input :id="field.id" type="number" v-model="field.value" style="border-radius: 8pt 0 0 8pt">
          {{field.unit.name}}
        <span class="mini-close" style="font-size: 8pt" v-on:click="editComment(field, true)">
          <fmt:message key="comment.plus"/>
        </span>
        </td>
      </tr>
      <tr>
        <td>
          {{field.editComment}}
        </td>
      </tr>
      <tr v-if="field.editComment">
        <td class="2">
          <input v-model="field.comment">
        </td>
      </tr>
      <tr v-else-if="field.comment" v-on:click="editComment(field, true)">
        <td>
          {{field.comment}}
        </td>

      </tr>
    </template>
  </template>
  <tr>
    <td colspan="2" align="center">
      <button class="close-button left-button" onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button class="save-button right-button">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
