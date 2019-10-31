<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 31.10.2019
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <table id="static-content" style="width: 150pt">
    <tr>
      <td style="border-bottom: dashed black 1pt;">
        <jsp:include page="../storages/storageList.jsp"/>
      </td>
    </tr>
    <tr>
      <td>
        <jsp:include page="../summary/calendar.jsp"/>
      </td>
    </tr>
  </table>
</html>
