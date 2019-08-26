<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <table>
    <tr>
      <th colspan="2">
        <span style="">
          <fmt:message key="shure.remove.extraction.crude"/>
        </span>
      </th>
    </tr>
    <tr>
      <td>
        <fmt:message key="create.date"/>
      </td>
      <td>
        ~
      </td>
    </tr>
    <tr>
      <td>
        <fmt:message key="create.time"/>
      </td>
      <td>
        ~
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button onclick="closeModal()">
          <fmt:message key="button.no"/>
        </button>
        <button onclick="remove()">
          <fmt:message key="button.yes"/>
        </button>
      </td>
    </tr>
  </table>
</html>
<script>
  var removeApi = '${remove}';
  var id = '${crude.id}';
  function remove(){
    PostApi(removeApi, {id:id}, function(a){
      if (a.status === 'success'){
        closeModal();
      }
    })
  }
</script>