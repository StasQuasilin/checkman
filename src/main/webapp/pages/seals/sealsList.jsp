<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" class="container-header" style="display: inline">
  <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/></button>
  <button onclick="loadModal('${find}')"><fmt:message key="seal.find"/></button>
</div>
<%--<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>--%>
<%--<script src="${context}/vue/templates/transportationDataView.vue"></script>--%>
<%--<script src="${context}/vue/templates/pricePlug.vue"></script>--%>
<%--<script src="${context}/vue/templates/commentatorPlug.vue"></script>--%>
<%--<script src="${context}/vue/dataList.vue?v=${now}"></script>--%>
<script src="${context}/vue2/baseList.vue"></script>
<script src="${context}/vue2/simpleList.vue"></script>
<script>
  list.api.show = '${show}';
  list.sort = function(a, b){
    return new Date(b.time.time) - new Date(a.time.time);
  };
  list.show = function(id){
    loadModal(list.api.show, {id:id});
  };
  <c:forEach items="${subscribe}" var="s">
  subscribe('${s}', function(a){
    list.loading = false;
    list.handle(a);
  });
  </c:forEach>
  stopContent = function(){
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(){
      unSubscribe('${s}');
    });
    </c:forEach>
  };
</script>
<div id="list" style="display: flex; flex-wrap: wrap;">
  <div v-for="(value, key) in getItems()" style="border: solid 1pt; margin: 4pt 8pt; padding: 4pt;" v-on:click="show(value.id)">
    <div style="text-align: center">
      {{value.title}}
    </div>
    <div>
      {{new Date(value.time.time).toLocaleDateString()}}
    </div>
    <div style="text-align: center">
      {{value.free}}/{{value.total}}
    </div>
  </div>
</div>
</html>
