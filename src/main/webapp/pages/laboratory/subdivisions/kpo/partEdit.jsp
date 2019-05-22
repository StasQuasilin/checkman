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
      part:{}
    },
    methods:{
      pickDate:function(){
        var self = this;
        datepicker.show(function(date){
          self.part.date = date;
        }, self.part.date)
      },
      save:function(){
        PostApi(this.api.save, this.part, function(a){
          if (a.status == 'success'){
            closeModal();
          }
        })
      }
    }
  });
  editor.api.save = '${save}'
  <c:choose>
  <c:when test="${not empty part}">
  editor.part = {
    id:${part.id},
    date:new Date('${part.date}').toISOString().substring(0, 10),
    number:${part.number},
    organoleptic:${part.organoleptic},
    color:${part.color},
    acid:${part.acid},
    peroxide:${part.peroxide},
    soap:${part.soap}
  };
  </c:when>
  <c:otherwise>
  editor.part = {
    date:new Date().toISOString().substring(0, 10),
    number:0,
    organoleptic:true,
    color:0,
    acid:0,
    peroxide:0,
    soap:false
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
      <input id="date" v-model="new Date(part.date).toLocaleDateString()" readonly v-on:click="pickDate">
    </td>
  </tr>
  <tr>
    <td align="right">
      <label for="number">
        <fmt:message key="vro.part"/>
      </label>
    </td>
    <td>
      #
    </td>
    <td>
      <input id="number" type="number" v-model="part.number">
    </td>
  </tr>
  <tr>
    <td>
      <label for="organoleptic">
        <fmt:message key="oil.organoleptic"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="organoleptic" type="checkbox" v-model="part.organoleptic">
      <span v-if="part.organoleptic">
        <fmt:message key="oil.organoleptic.match"/>
      </span>
      <span v-else>
        <fmt:message key="oil.organoleptic.doesn't.match"/>
      </span>
    </td>
  </tr>
  <tr>
    <td>
      <label for="color">
        <fmt:message key="oil.color.value"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="color" type="number" v-model="part.color">
    </td>
  </tr>
  <tr>
    <td>
      <label for="acid">
        <fmt:message key="sun.acid.value"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="acid" type="number" step="0.01" v-model="part.acid">
    </td>
  </tr>
  <tr>
    <td>
      <label for="peroxide">
        <fmt:message key="oil.peroxide"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="peroxide" type="number" step="0.01" v-model="part.peroxide">
    </td>
  </tr>
  <tr>
    <td>
      <label for="soap">
        <fmt:message key="oil.soap"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="soap" type="checkbox" v-model="part.soap">
      <span v-if="part.soap">
        <fmt:message key="oil.soap.yes"/>
      </span>
      <span v-else>
        <fmt:message key="oil.soap.no"/>
      </span>
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
