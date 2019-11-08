<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 07.11.2019
  Time: 9:58
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<table>
  <tr>
    <td colspan="2" align="center">
      <button>
        <fmt:message key=""
      </button>
      <button>
        <fmt:message key="button.delete"/>
      </button>
      <button>
        <fmt:message key="edit"/>
      </button>
      <button>
        <fmt:message key="chat.send"/>
      </button>
    </td>
  </tr>
</table>
</html>
