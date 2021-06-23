
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-06-08
  Time: 09:56
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/changeView.vue?v=${now}1"></script>
<script>
    changeView.api.save = '${save}';
    changeView.current = '${view}';
    <c:forEach items="${roles}" var="r">
        changeView.items.push('${r}');
        changeView.itemNames['${r}'] = '<fmt:message key="role.${r}"/>';
    </c:forEach>
</script>
<div id="changeView">
    <div v-for="item in items">
        <span style="width: 10pt; display: inline-block">
            <template v-if="item === current">
                -
            </template>
        </span>
        <span class="mini-close" v-on:click="changeView(item)">
            {{itemNames[item]}}
        </span>
    </div>
    <div style="width: 100%; text-align: center">
        <button onclick="closeModal()">
            <fmt:message key="button.cancel"/>
        </button>
        <button v-on:click="saveView()">
            <fmt:message key="button.save"/>
        </button>
    </div>
</div>

</html>
