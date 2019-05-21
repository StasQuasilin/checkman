<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="editor" class="editor">
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
      <input id="date" readonly autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="3">

    </td>
  </tr>
  <tr>
    <td>
      <label for="seed">
        <fmt:message key="oil.mass.fraction.seed"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="seed" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="seedHumidity">
        <fmt:message key="oil.mass.fraction.seed.humidity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="seedHumidity" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="husk">
        <fmt:message key="oil.mass.fraction.husk"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="husk" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="huskHumidity">
        <fmt:message key="oil.mass.fraction.husk.humidity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="huskHumidity" autocomplete="off">
    </td>
  </tr>
  <tr>
    <th>
      <fmt:message key="forpress"/>
      <span class="mini-close">
        +
      </span>
    </th>
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
