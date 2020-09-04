
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 04.09.20
  Time: 14:04
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script type="application/javascript" src="${context}/vue/dealList.vue"></script>
<script>
    dealList.api.edit = '${edit}';
    subscriber.subscribe('${subscribes}', dealList.handler);

</script>
<div id="header-buttons">
    <button onclick="dealList.edit()">
        <fmt:message key="deal.add"/>
    </button>
</div>
<div id="deals">

</div>
</html>
