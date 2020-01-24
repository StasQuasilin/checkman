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
      organisation:{
        name:'',
        type:''
      },
      err:{
        name:false
      },
      names2:false
    },
    methods:{
      save:function(){
        var err = this.err.name = this.organisation.name === '';
        if (!err) {
          PostApi(this.api.save, this.organisation, function (a) {
            saveModal(a);
            if (a.status == 'success') {
              closeModal();
            }
          })
        }
      }
    }
  });
  editor.api.save='${save}';
  <c:choose>
  <c:when test="${not empty organisation}">
  var name = "${organisation.name}";
  var fullName = "${organisation.fullName}";
  if (!fullName){
    fullName = name;
  }
  editor.organisation={
    id:${organisation.id},
    name:name,
    fullName:fullName,
    type:'${organisation.type}'
  };
  </c:when>
  </c:choose>
</script>
<table id="edit">
  <tr>
    <td colspan="3">
      <label for="name">
        <fmt:message key="organisation.name"/>
      </label>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <input id="name" v-model="organisation.fullName" v-on:click="err.name = false" style="width: 100%"
          onfocus="this.select()" :class="{error : err.name}" autocomplete="off">
    </td>
  </tr>
  <tr v-if="organisation.name !== organisation.fullName || names2">
    <td>
      <label for="shortName">
        <fmt:message key="organisation.short.name"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="shortName" v-model="organisation.name" onfocus="this.select()" autocomplete="off" maxlength="20">
      <span v-if="organisation.fullName === organisation.name" v-on:click="names2 = !names2">
        &times;
      </span>
    </td>
  </tr>
  <tr v-else>
    <td colspan="3" align="right">
      <span class="mini-close" v-on:click="names2 = !names2" style="font-size: 8pt">
        <fmt:message key="add.short.name"/>
      </span>
    </td>
  </tr>
  <tr>
    <td>
      <label for="form">
        <fmt:message key="organisation.form"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="form" v-model="organisation.type">
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
