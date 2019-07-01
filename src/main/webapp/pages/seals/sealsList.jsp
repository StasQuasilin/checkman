<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" class="container-header" style="display: inline">
  <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
</div>
<script src="${context}/vue/dataList.js"></script>
<script>
  list.api.update = '${update}';
  list.doRequest();
</script>
<div id="container">
  <div v-for="(value, key) in getItems()">
    {{value}}
  </div>
</div>
</html>
