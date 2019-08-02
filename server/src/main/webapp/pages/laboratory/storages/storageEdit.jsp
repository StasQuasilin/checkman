<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script>
  var editor = new Vue({
    el: '#editor',
    data:{
      api:{
        save:''
      },
      storages:[],
      analyses:{}
    },
    methods:{
      save:function(){
        PostApi(this.api.save, this.analyses, function(a){
          if (a.status == 'success'){
            closeModal();
          }
        })
      }
    }
  });
  editor.api.save ='${edit}';
  <c:forEach items="${storages}" var="storage">
  editor.storages.push({
    id:${storage.id},
    name:'${storage.name}'
  });
  </c:forEach>
  editor.analyses={
    storage:-1,
    phosphorus:0,
    acid:0,
    peroxide:0,
    color:0
  }
</script>
<table id="editor" class="editor">
  <tr>
    <td>
      <label for="storage">
        <fmt:message key="storage.${type}"/>
      </label>
    </td>
    <td>
      <select id="storage" style="width: 100%" v-model="analyses.storage">
        <option v-for="storage in storages" :value="storage.id">
          {{storage.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="phosphorus">
        <fmt:message key="oil.phosphorus.mass.fraction"/>
      </label>
    </td>
    <td>
      <input id="phosphorus" type="number" step="0.01" v-model="analyses.phosphorus">
    </td>
  </tr>
  <tr>
    <td>
      <label for="acid">
        <fmt:message key="sun.acid.value"/>
      </label>
    </td>
    <td>
      <input id="acid" type="number" step="0.01" v-model="analyses.acid">
    </td>
  </tr>
  <tr>
    <td>
      <label for="peroxide">
        <fmt:message key="oil.peroxide"/>
      </label>
    </td>
    <td>
      <input id="peroxide" type="number" step="0.01" v-model="analyses.peroxide">
    </td>
  </tr>
  <tr>
    <td>
      <label for="color">
        <fmt:message key="oil.color.value"/>
      </label>
    </td>
    <td>
      <input id="color" type="number" step="1" v-model="analyses.color">
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
