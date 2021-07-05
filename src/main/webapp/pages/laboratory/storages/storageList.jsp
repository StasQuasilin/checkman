<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" style="display: inline">
  <button ondblclick="" onclick="loadModal('${edit}?type=${type}')">
    <fmt:message key="analyses"/>
  </button>
</div>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.limit= 14;
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        list.handler(a);
    });
    </c:forEach>
</script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<div id="container" style="overflow-y: scroll; height: 100%;">
  <div v-for="(value, key) in items" class="container-item"
       :class="'container-item-' + new Date(value.item.turn.date).getDay()" style="padding: 4pt; display: inline-block">
    <div class="turn-date" :class="'t-' + value.item.turn.number">
            <span>
                {{new Date(value.item.turn.date).toLocaleDateString()}}
            </span>
            <span>
                <fmt:message key="turn"/><span>&nbsp;#</span>{{value.item.turn.number}}
            </span>
    </div>
    <div style="padding-left: 12pt">
        <table>
            <tr>
                <th style="width: 4em">
                    <fmt:message key="time"/>
                </th>
                <th style="width: 12em">
                    <fmt:message key="storage.oil"/>
                </th>
                <th style="width: 4em">
                    <fmt:message key="oil.phosphorus.mass.fraction"/>
                </th>
                <th style="width: 4em">
                    <fmt:message key="sun.acid.value"/>
                </th>
                <th style="width: 4em">
                    <fmt:message key="oil.phosphorus"/>
                </th>
                <th>
                    <fmt:message key="oil.color.value"/>
                </th>
            </tr>
            <tr v-for="analyse in value.item.analyses">
                <td>
                    {{new Date(analyse.date).toLocaleTimeString().substring(0, 5)}}
                </td>
                <td>
                    {{analyse.storage}}
                </td>
                <td align="center">
                    {{analyse.oil.phosphorus}}
                </td>
                <td align="center">
                    {{analyse.oil.acid}}
                </td>
                <td align="center">
                    {{analyse.oil.phosphorus}}
                </td>
                <td align="center">
                    {{analyse.oil.color}}
                </td>
            </tr>
        </table>

    </div>
  </div>
</div>
</html>
