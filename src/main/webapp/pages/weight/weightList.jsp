<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 22.11.2019
  Time: 13:45
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <c:if test="${not empty 'headerContent'}">
  <div id="container-header" class="container-header">
      <jsp:include page="${headerContent}"/>
  </div>
  </c:if>
  <jsp:include page="../transportListTemplate2.jsp"/>
</html>
