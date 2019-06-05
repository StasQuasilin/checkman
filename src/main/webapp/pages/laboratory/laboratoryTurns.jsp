<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<div id="container-header" class="container-header">
    <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
</div>
<script>
    filter_control = {}
    req_filter = {}
</script>
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.timer=1000*5;
    deamon.setUrls('${update}', '${edit}')
</script>
<div id="container">
    <div v-for="(value, key) in items" :id="value.item.id"
         class="container-item" :class="rowName(value.item.date)"
         v-on:click="edit(value.item.id)">
        <div class="upper-row">
            <div class="turn-date" :class="'t-' + value.item.number">
                <span>
                    {{new Date(value.item.date).toLocaleDateString()}}
                    {{new Date(value.item.date).toLocaleTimeString().substring(0, 5)}}
                </span>
                <span>
                    <fmt:message key="turn"/><span>&nbsp;#</span>{{value.item.number}}
                </span>
            </div>
        </div>
        <div class="lower-row">
            <span v-for="(lab, k) in value.item.laboratory">
            &#9686;{{k + 1}}.{{lab.worker.person.value}}&#9687;
        </span>
        </div>
    </div>
</div>
</html>
