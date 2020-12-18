<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 22.11.2019
  Time: 13:45
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <script src="${context}/vue/templates/laboratoryDataView.vue"></script>
  <script src="${context}/vue/templates/transportationDataView.vue"></script>
  <script src="${context}/vue/templates/priceView.vue"></script>
  <script src="${context}/vue/templates/commentator.vue"></script>
  <script src="${context}/vue/dataList.vue?v=${now}"></script>

  <jsp:include page="../summary/summaryHeader.jsp"/>
  <jsp:include page="../transportListTemplate.jsp"/>
</html>
