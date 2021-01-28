<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/referenceList.vue"></script>
<script>
    referenceList.api.update = '${update}';
    referenceList.api.edit = '${edit}';
    referenceList.update();
</script>
<div id="referencesList">
    <div v-for="item in items">
        <a v-on:click="edit(item.product)">{{item.product.name}}</a>
    </div>
</div>

</html>