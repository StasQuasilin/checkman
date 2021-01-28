<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 18.12.2019
  Time: 9:37
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .protocol{
    border: solid;
    border-radius: 8pt;
    padding: 4pt;
    width: 190pt;
    margin: 2pt;
  }
  .protocol-product{
    width: 100%;
    font-size: 18pt;
  }
</style>
<jsp:include page="standardHeader.jsp"/>
<div>
  <c:forEach items="${protocols}" var="protocol">
    <div class="protocol">
      <div class="protocol-product">
          ${protocol.product.name}
      </div>
      <div class="protocol-date">
          <fmt:message key="deal.from"/> <fmt:formatDate value="${protocol.date}" pattern="dd.MM.yyyy"/>
      </div>
      <div class="protocol-number">
          <fmt:message key="transportation.automobile.number"/>: ${protocol.number}
      </div>
    </div>
  </c:forEach>
</div>
</html>
