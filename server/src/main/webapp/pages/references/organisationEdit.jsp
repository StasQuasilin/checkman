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
      }
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
  editor.organisation={
    id:${organisation.id},
    name:'${organisation.name}',
    type:'${organisation.type}'
  };
  </c:when>
  </c:choose>
</script>
<table id="edit">
  <tr>
    <td>
      <label for="name">
        <fmt:message key="organisation.name"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="name" v-model="organisation.name" v-on:click="err.name = false"
          onfocus="this.select()" :class="{error : err.name}" autocomplete="off">
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
