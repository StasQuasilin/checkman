<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<div id="container-header" style="display: inline">
    <c:if test="${view eq 'analyser'}">
        <button onclick="loadModal('${edit}')" style="background-color: white">
            + <fmt:message key="vro.part"/>
        </button>
    </c:if>
</div>