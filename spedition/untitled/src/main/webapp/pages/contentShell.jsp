<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 01.06.20
  Time: 16:58
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${context}/css/main.css"/>
    <script>
        if(typeof context == "undefined"){
            context = '${context}';
        }
    </script>
    <script src="${context}/js/utils.js"></script>
    <script src="${context}/external/vue.js"></script>
    <script src="${context}/js/socket.js"></script>
</head>
    <body>
        <jsp:include page="${content}"/>
    </body>
</html>
