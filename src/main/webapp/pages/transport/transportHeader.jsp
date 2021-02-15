<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/drop-menu.css?v=${now}">
<div id="container-header" class="container-header">
    <button onclick="loadModal('${add}')">
        <fmt:message key="button.add"/>
    </button>
    <c:if test="${not empty check}">
        <button onclick="loadModal('${check}')"><fmt:message key="button.check"/></button>
    </c:if>
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="seals"/></a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${addSeals}')">
                <fmt:message key="seals.add"/>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${findSeals}')">
                <fmt:message key="seal.find"/>
            </div>
        </div>
    </div>
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="document.print"/></a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" style="width: 10em" onclick="loadModal('${printIncome}')">
                <fmt:message key="transport.income"/>
            </div>
            <div class="drop-menu-item" style="width: 10em" onclick="loadModal('${transportPerMonth}')">
                <fmt:message key="title.transport.per.month"/>
            </div>
        </div>
    </div>

</div>
</html>
