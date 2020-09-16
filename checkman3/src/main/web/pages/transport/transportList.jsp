<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 16.09.20
  Time: 09:33
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <script type="application/javascript" src="${context}/vue/list.vue"></script>
<%--    <script type="application/javascript" src="${context}/vue/templates/transportationsView/"></script>--%>
    <script type="application/javascript" src="${context}/vue/transportations.vue"></script>
    <script>
        transportations.api.edit = '${edit}';
        subscriber.subscribe('${subscribe}', transportations.handler);
    </script>
<div id="transportations">
    <div v-for="item in items" v-on:click="edit(item.id)">
        {{item}}
    </div>
</div>
</html>
