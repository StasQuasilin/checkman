<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 04.12.2019
  Time: 14:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="retailHeader.jsp"/>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<div id="container">
  <div v-for="(value, idx) in getItems()">
    {{value.item}}
  </div>
</div>
</html>
