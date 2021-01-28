<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 04.12.2019
  Time: 14:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="retailHeader.jsp"/>
<script src="${context}/vue/templates/retailProductView.vue"></script>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>

<jsp:include page="../retailTransportList.jsp"/>

</html>
