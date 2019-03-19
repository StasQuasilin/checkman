<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/ModalView.css">
    <div class="modal-body">
        <div class="modal-header">
            <fmt:message key="${title}"/>
        </div>
        <div class="modal-content" id="modal-content">
            <jsp:include page="${modalContent}"/>
        </div>
    </div>
</html>