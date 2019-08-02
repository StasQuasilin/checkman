<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/notesListEdit.vue">

</script>
<script>
  notes.api.update = '${update}';
  notes.api.save = '${save}';
  notes.api.remove = '${remove}';
  notes.plan = ${plan.id};
  notes.worker={
    id:${worker.id},
    value:'${worker.person.value}'
  };
  notes.doRequest();
  addOnCloseEvent(function(){
    notes.stop();
  })
</script>
<c:set var="inputTitle"><fmt:message key="note.add"/></c:set>
<c:set var="inputHolder"><fmt:message key="note.holder"/></c:set>
<table id="notes">
  <tr>
    <td>
      <fmt:message key="deal.organisation"/>
    </td>
    <td>
      ${plan.deal.organisation.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="deal.product"/>
    </td>
    <td>
      ${plan.deal.product.name}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.automobile"/>
    </td>
    <td>
      ${plan.transportation.vehicle.value}
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.driver"/>
    </td>
    <td>
      ${plan.transportation.driver.person.value}
    </td>
  </tr>
  <tr v-for="note in notes">
    <td>
      <span v-if="note.creator.id === worker.id">
        <span class="mini-close" v-on:click="removeNote(note.id)">
          &times;
        </span>
        <fmt:message key="you"/>
      </span>
      <span v-else>
        {{note.creator.person.value}}
      </span>:
    </td>
    <td>
      <span style="word-break: break-all; width: 20em; display: inline-block;">
        {{note.note}}
      </span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <input placeholder="${inputHolder}" style="width: 100%" title="${inputTitle}" v-model="noteInput" v-on:keyup.escape="noteInput =''" v-on:keyup.enter="saveNote">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
