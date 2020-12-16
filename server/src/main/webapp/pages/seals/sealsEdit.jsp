<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
  <table id="editor" class="editor">
  <tr>
    <td>
      <label for="prefix">
        <fmt:message key="seal.prefix"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="prefix" v-model="prefix" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="number">
        <fmt:message key="seal.number"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="number" v-model.number="number" autocomplete="off" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="suffix">
        <fmt:message key="seal.suffix"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="suffix" v-model="suffix" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="quantity">
        <fmt:message key="seal.quantity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="quantity" v-model.number="quantity" autocomplete="off">
    </td>
  </tr>
    <template v-if="number > 0 && quantity > 0">
      <tr>
        <td>
          <fmt:message key="seal.preview"/>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          {{preview()}}
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
<script>
  editor = new Vue({
    el: '#editor',
    data:{
      api:{
        save:''
      },
      prefix:'А',
      number:0,
      suffix:'',
      quantity:100
    },
    methods:{
      preview:function(){
        return this.doPreview(this.number) + (this.quantity > 0 ? ' ... ' + this.doPreview(this.number + this.quantity - 1) : '')
      },
      doPreview:function(number){
        return (this.prefix ? (this.prefix).toUpperCase() + '-' : '') + number + (this.suffix ? '-' + (this.suffix).toUpperCase() : '')
      },
      save:function(){
        let parameters = {};
        parameters.prefix = this.prefix;
        parameters.number = this.number;
        parameters.suffix = this.suffix;
        parameters.quantity = this.quantity;
        PostApi(this.api.save, parameters, function(a){
          if (a.status === 'success'){
            closeModal();
          }
        })
      }
    }
  });
  editor.api.save = '${save}'
</script>
</html>
