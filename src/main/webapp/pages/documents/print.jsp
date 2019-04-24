<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title>Printing</title>
    <style type="text/css" media="print">
        @page {
            size: landscape;
        }
    </style>
</head>
    <div id="content">
        <jsp:include page="waybill.jsp"/>
    </div>
</html>
