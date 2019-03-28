<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" style="display: inline">
    <button onclick="loadModal('${crudeEdit}')">
        <fmt:message key="extraction.crude"/>
    </button>
    <button onclick="loadModal('${rawEdit}')">
        <fmt:message key="extraction.raw"/>
    </button>
    <button onclick="loadModal('${oilEdit}')">
        <fmt:message key="extraction.oil"/>
    </button>
</div>
</html>