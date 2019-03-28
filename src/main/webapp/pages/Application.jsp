<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

    <link rel="stylesheet" href="${context}/css/Application.css">
    <link rel="stylesheet" href="${context}/css/Coverlet.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${context}/js/Logic.js"></script>
    <script>
        context = '${context}';
    </script>
    <script src="${context}/js/Application.js"></script>
</head>
<body style="margin: 0">
<div class="coverlet" id="coverlet"></div>
<div class="modal-layer" style="display: none" id="modal">
</div>
<table border="1" style="width: 100%; height: 100%">
    <tr>
        <td rowspan="2" valign="top">
            <jsp:include page="NavigationMenu.jsp"/>

        </td>
        <td width="80%">
            <div class="header" id="header"></div>
        </td>
        <td>
          <div class="header">
            ${worker.value}
          </div>
        </td>
        <td rowspan="3" >

        </td>
    </tr>
    <tr>

        <td rowspan="2" colspan="2" height="100%" style="max-width: 1266px; width: 100%">
            <div class="content" id="content"></div>
        </td>
    </tr>
    <tr>
        <td height="100%">
            <div id="filter" class="filter" ></div>
        </td>
    </tr>
</table>
</body>
</html>
