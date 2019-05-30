<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" class="container-header">
  <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/></button>
</div>
<script>
  filter_control = {};
  req_filter = {};
</script>
<script src="${context}/vue/dataList.js"></script>
<script>
  deamon.setUrls('${update}', '${show}')
</script>

<div id="container">
  <div v-for="(value, key) in items">
    {{value.item.train.deal.organisation.name}}
    {{value.item.train.deal.product.name}}
    {{value.item.number}}
  </div>
</div>
</html>
