<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 20.01.2020
  Time: 14:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="addTransportHeader.jsp"/>
<script src="${context}/vue/templates/retailProductView.vue"></script>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>

<jsp:include page="../retailTransportList.jsp"/>

</html>