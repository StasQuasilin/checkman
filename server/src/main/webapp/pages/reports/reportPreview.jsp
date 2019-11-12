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
    <td>
      <fmt:message key="date"/>
    </td>
    <td>
      <fmt:formatDate value="${report.turn.date}" pattern="dd.MM.yyyy"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="right">
      <fmt:message key="turn"/> <span>#</span>${report.turn.number}
    </td>
  </tr>
  <c:forEach items="${noCategory}" var="no">
    <tr>
      <td style="padding-left: 16pt">
          ${no.title}
      </td>
      <td>
        <fmt:formatNumber value="${no.value}"/>
      </td>
    </tr>
  </c:forEach>
  <c:forEach items="${categories}" var="category">
    <c:if test="${category ne null}">
      <tr>
        <td colspan="2">
          <b>
            ${category.title}
          </b>
        </td>
      </tr>
    </c:if>
    <c:forEach items="${fields[category]}" var="field">
      <tr>
        <td style="padding-left: 16pt">
          ${field.title}
        </td>
        <td>
          <fmt:formatNumber value="${field.value}"/>
        </td>
      </tr>
    </c:forEach>
  </c:forEach>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button onclick="Remove()">
        <fmt:message key="button.delete"/>
      </button>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="Edit()">
        <fmt:message key="edit"/>
      </button>
      <button onclick="Send()">
        <fmt:message key="chat.send"/>
      </button>
    </td>
  </tr>
</table>
</html>
<script>
  id = '${report.id}';
  api = {
    remove:'${delete}',
    edit:'${edit}',
    send:'${send}'
  };

  function Remove(){
    closeModal();
    PostApi(api.remove, {id:id})
  }
  function Edit(){
    closeModal();
    loadModal(api.edit, {id:id})
  }
  function Send(){
    closeModal();
    PostApi(api.send, {id:id});
  }
</script>
