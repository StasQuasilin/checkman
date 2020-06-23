<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <div class="menu">
        <div class="menu-item">
            ${user.person.surname} ${user.person.forename}
        </div>
        <c:if test="${role ne 'user'}">
            <div class="menu-item" onclick="loadModal('${registration}')">
                <fmt:message key="registration"/>
            </div>
        </c:if>
        <div class="menu-item" onclick="logout()">
            <fmt:message key="logout"/>
        </div>
    </div>
</html>