<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dailyReportPrint.vue"></script>
<script>
  roundReport.dateType='month';
  roundReport.api.print='${print}'
</script>
<table id="roundReport">
  <tr>
    <td>
      <label for="month">
        <fmt:message key="month"/>
      </label>
    </td>
    <td>
      <input id="month" v-on:click="pickDate" style="width: 6em" readonly
             v-model="new Date(parameters.date).toLocaleDateString().substring(3)">
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="print">
        <fmt:message key="document.print"/>
      </button>
    </td>
  </tr>
</table>
</html>
