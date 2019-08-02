<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 26.06.2019
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<script>
  console.log('11');
  var xhr = new XMLHttpRequest();
  xhr.open('POST', 'http://localhost:3331/test?i=1', true);
  xhr.onload = function(){
    console.log(xhr.responseText);
  };
  xhr.send('{}')
</script>
</body>
</html>
