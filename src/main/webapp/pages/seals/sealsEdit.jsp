<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
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
      <input id="prefix">
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
      <input id="number">
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
      <input id="suffix">
    </td>
  </tr>
</table>
<button onclick="closeModal()">
  <fmt:message key="button.cancel"/>
</button>
<button>
  <fmt:message key="button.save"/>
</button>
</html>
