<%--
  Created by Kvasik
  Date: 25.12.2019
  Time: 8:55
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  let id = ${transportation.id};
  let api = {
    remove:'${remove}',
    archive:'${archive}'
  };
  function remove(){
    PostApi(api.remove, {id:id}, function(a){
      if (a.status=== 'success'){
        closeModal();
      }
    })
  }
  function archive(){
    PostApi(api.archive, {id:id}, function(a){
      if (a.status=== 'success'){
        closeModal();
      }
    })
  }
</script>
<table>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        Close
      </button>
      <button onclick="archive()">
        Archive
      </button>
      <button onclick="remove()">
        Remove
      </button>
    </td>
  </tr>
</table>
</html>
