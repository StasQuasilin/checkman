<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 10.12.2019
  Time: 12:02
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<jsp:include page="stopReportListHeader.jsp"/>
<script src="${context}/vue/dataList.vue"></script>
<script>

  <c:forEach items="${subscribe}" var="s">
  subscribe('${s}', function(a){
    list.handler(a);
  });
  </c:forEach>
</script>

</html>
