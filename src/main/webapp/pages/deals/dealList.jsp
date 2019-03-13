<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="${context}/vue/dataList.js"></script>
<script>
  deamon.setUpdateUrl('${update_url}')
</script>
<div class="container" id="container" >
  <div v-for="(value, key) in items" v-bind:class="[day(value.date)]">
    {{key}}: {{value.date}}
  </div>
</div>
</html>
