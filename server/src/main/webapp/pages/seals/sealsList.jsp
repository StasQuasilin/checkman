<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" class="container-header" style="display: inline">
  <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/></button>
</div>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/transportationDataView.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<script>
  <c:forEach items="${subscribe}" var="s">
  subscribe('${s}', function(a){
    list.loading = false;
    list.handler(a);
  });
  </c:forEach>
  stopContent = function(){
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
      unSubscribe('${s}');
    });
    </c:forEach>
  };
</script>
<div id="container" style="display: flex; flex-wrap: wrap;">
  <div v-for="(value, key) in getItems()" style="border: solid 1pt; margin: 4pt 8pt; padding: 4pt;">
    <div style="text-align: center">
      {{value.item.title}}
    </div>
    <div>
      {{new Date(value.item.time.time).toLocaleDateString()}}
    </div>
    <div style="text-align: center">
      {{value.item.free}}/{{value.item.total}}
    </div>

  </div>
</div>
</html>
