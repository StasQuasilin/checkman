<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.11.2019
  Time: 15:23
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script>
  var addressEditor = new Vue({
    el:'#addressEditor',
    components:{
      'object-input':objectInput
    },
    data:{
      api:{},
      counterpartyProps:{
        header:'<fmt:message key="deal.organisation"/>',
        show:['value']
      },
      counterparty:{
        id:-1
      },
      address:{
        city:'',
        street:'',
        house:'',
        block:'',
        flat:''
      }
    },
    methods:{
      save:function(){
        PostApi(this.api.save, {counterparty:this.counterparty.id, address:this.address}, function(a){
          if(a.status == 'success'){
            saveModal(a.result)
            closeModal();
          }
        })
      }
    }
  });
  addressEditor.api.save='${save}';
  <c:if test="${not empty counterparty }">
  addressEditor.counterparty = {
    id:${counterparty.id},
    value:'${counterparty.value}'
  };
  </c:if>
</script>
<table id="addressEditor" class="editor">
  <tr>
    <td>
      <fmt:message key="deal.organisation"/>
    </td>
    <td>
      <object-input :props="counterpartyProps" :object="counterparty"></object-input>
    </td>
  </tr>
  <tr>
    <td>
      <label for="city">
        <fmt:message key="address.city"/>
      </label>
    </td>
    <td>
      <input id="city" v-model="address.city" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="street">
        <fmt:message key="address.street"/>
      </label>
    </td>
    <td>
      <input id="street" v-model="address.street" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="build">
        <fmt:message key="address.build"/>
      </label>
    </td>
    <td>
      <input id="build" v-model="address.build" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="block">
        <fmt:message key="address.block"/>
      </label>
    </td>
    <td>
      <input id="block" v-model="address.block" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="flat">
        <fmt:message key="address.flat"/>
      </label>
    </td>
    <td>
      <input id="flat" v-model="address.flat" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="save()">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
