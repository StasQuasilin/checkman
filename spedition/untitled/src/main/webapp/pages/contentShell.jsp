<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 01.06.20
  Time: 16:58
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="uk"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${context}/css/main.css"/>
    <link rel="stylesheet" href="${context}/css/modalLayer.css">
    <script>
        if(typeof context == "undefined"){
            context = '${context}';
        }
    </script>
    <script src="${context}/external/jquery.min.js"></script>
    <script src="${context}/js/utils.js"></script>
    <script src="${context}/external/vue.js"></script>
    <script src="${context}/js/socket.js"></script>
    <script src="${context}/js/modal.js"></script>
</head>
    <body>
        <div id="modalLayer" class="modal-layer">
            <div class="modal-body">
                <div class="model-content">
                    <div class="modal-header">
                        &nbsp;
                        <div id="modalTitle" class="modal-title"></div>
                        <div class="modal-close-button" onclick="closeModal()">
                            &times;
                        </div>
                    </div>
                    <div id="modalContent" class="modal-data"></div>
                </div>
            </div>
        </div>
        <div style="height: 100%; background-color: palegoldenrod">
            <jsp:include page="${content}"/>
        </div>

    </body>
</html>
