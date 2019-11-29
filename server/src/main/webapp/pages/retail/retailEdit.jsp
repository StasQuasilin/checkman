<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.11.2019
  Time: 10:32
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table width="100%">
  <tr>
    <td>
      <fmt:message key="date"/>
    </td>
    <td>

    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.driver"/>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="transportation.automobile"/>/<fmt:message key="transportation.automobile.trailer"/>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="pallets"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="height: 200pt; width: 100%">

        <button>
          <fmt:message key="counterparty.add"/>
        </button>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
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
