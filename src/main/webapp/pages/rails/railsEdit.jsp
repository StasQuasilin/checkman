<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<html>
<script src="${context}/vue/railsEdit.js"></script>
<script>
  edit.api.save = '${save}';
  <c:forEach items="${trains}" var="train">
  edit.rails.push({
    id:${train.id},
    name:'${train.deal.organisation.value} ${train.deal.product.name}'
  });
  </c:forEach>
  var trainId = -1;
  if (edit.rails.length == 1){
    trainId = edit.rails[0].id;
  }
  edit.truck={
    train:trainId,
    number:''
  }
</script>
<div id="editor">
  <table>
    <tr>
      <td>
        <label for="load">
          <fmt:message key="rail.load"/>
        </label>
      </td>
      <td>
        <select id="load" style="width: 100%" v-model="truck.train">
          <option disabled value="-1"><fmt:message key="need.select"/></option>
          <option v-for="rail in rails" :value="rail.id">
            {{rail.name}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <label for="number">
          <fmt:message key="rail.number"/>
        </label>
      </td>
      <td>
        <input id="number" v-model="truck.number">
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
</div>
</html>
