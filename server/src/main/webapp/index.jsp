<%@ page import="constants.Branches" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
  <%response.sendRedirect(request.getContextPath()+ Branches.UI.HOME);%>
  </head>
  <body>
    <h1 onclick="redirect()">Index.jsp</h1>
  </body>
</html>
<script>
  function redirect(){
    location.href= '/1';
  }
</script>
<%---server -XX:+DoEscapeAnalysis -XX:+OptimizeStringConcat--%>