<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css?v=${now}">
<script src="${context}/vue/sealEdit.vue?v=${now}"></script>
<script>
  editor.api.save = '${save}'
</script>
<div id="editor" class="editor">
  <table>
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
  <div v-if="err" class="error">
    {{err}}
  </div>
</div>

</html>
