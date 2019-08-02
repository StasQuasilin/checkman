<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" class="container-header">
  <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/></button>
</div>
<script src="${context}/vue/dataList.vue"></script>
<script>
  list.api.update = '${update}';
  list.api.edit = '${show}';
  list.doRequest();
</script>

<div id="container">
  <div v-for="(value, key) in items">
    {{value.item.train.deal.organisation.name}}
    {{value.item.train.deal.product.name}}
    {{value.item.number}}
  </div>
</div>
</html>
