<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dailyReportPrint.js"></script>
<script>
  report.api.print = '${print}'
</script>
<div id="report">
  <table>
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
        <input id="date" readonly v-model="new Date(parameters.date).toLocaleDateString()" v-on:click="pickDate">
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center">
        <button onclick="closeModal()">
          <fmt:message key="button.close"/>
        </button>
        <button v-on:click="print">
          <fmt:message key="document.print"/>
        </button>
      </td>
    </tr>
  </table>
</div>
</html>
