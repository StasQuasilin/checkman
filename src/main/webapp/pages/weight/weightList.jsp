
<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 22.11.2019
  Time: 13:45
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <script src="${context}/vue2/transportationDataView.vue"></script>
  <jsp:include page="../summary/summaryHeader.jsp"/>
  <jsp:include page="../transportListTemplate2.jsp"/>
<script>
  transportList.f.p = true;
  transportList.f.a = true;
</script>
</html>
