<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 06.11.2019
  Time: 22:37
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/editor.css">
<html>
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
     <input id="date" class="date" readonly autocomplete="off">
   </td>
 </tr>
  <tr>
    <td>
      <label for="time">
        <fmt:message key="time"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="time" class="date" readonly autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button>
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
