<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<div id="container-header" style="display: inline">
    <link rel="stylesheet" href="${context}/css/drop-menu.css">
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="analyses"/> &#9660;</a>
        <ul class="drop-menu-content">
            <li class="drop-menu-item" onclick="loadModal('${crudeEdit}')">
                <fmt:message key="extraction.crude"/>
            </li>
            <li class="drop-menu-item" onclick="loadModal('${turnCrudeEdit}')" >
                <fmt:message key="extraction.turn.crude"/>
            </li>
            <li class="drop-menu-item" onclick="loadModal('${rawEdit}')">
                <fmt:message key="extraction.raw"/>
            </li>
            <li class="drop-menu-item"  onclick="loadModal('${oilEdit}')">
                <fmt:message key="extraction.oil"/>
            </li>
        </ul>
    </div>
</div>
<script>
    var filter_control={};
</script>
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.url = '${update}';
    deamon.doRequest();
</script>
<div id="container">
    <div v-for="(value, key) in items" class="container-item" :class="rowName(value.item.date)" style="padding: 4pt">
        <div class="t1">
            <span>
                {{new Date(value.item.date).toLocaleDateString()}}
            </span>
            <span>
                <fmt:message key="turn"/><span>&nbsp;#</span>{{value.item.number}}
            </span>
        </div>
        <div style="padding-left: 12pt">
            <table style="font-size: 10pt; border: none" border="1">
                <tr>
                    <th>
                        <span style="display: inline-block; width: 4em;">
                            <fmt:message key="time"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="extraction.crude.humidity.income.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="extraction.crude.small.fraction.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="extraction.crude.miscellas.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="extraction.crude.humidity.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="extraction.crude.dissolvent.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="extraction.crude.grease"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="cake.protein"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 8em;">
                            <fmt:message key="cake.cellulose"/>
                        </span>
                    </th>
                </tr>
                <tr v-for="crude in (value.item.crudes)">
                    <td align="center">
                        {{new Date(crude.time).getHours()}}:{{new Date(crude.time).getMinutes()}}
                    </td>
                    <td align="center">
                        {{(crude.humidityIncome).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.fraction).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.miscellas).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.humidity).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.dissolvent).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.grease).toLocaleString()}}
                    </td>
                    <td align="center">
                        <span v-if="value.item.raws[crude.time]">
                            {{value.item.raws[crude.time].protein}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.raws[crude.time]">
                            {{value.item.raws[crude.time].cellulose}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                </tr>
            </table>
        </div>
        <div v-for="oil in value.item.oil" style="font-size: 10pt">
            <fmt:message key="extraction.oil"/>:
            <fmt:message key="sun.humidity"/>:
            {{(oil.humidity).toLocaleString()}},
            <fmt:message key="sun.acid.value"/>:
            {{(oil.acid).toLocaleString()}},
            <fmt:message key="oil.peroxide"/>:
            {{(oil.peroxide).toLocaleString()}},
            <fmt:message key="oil.phosphorus"/>:
            {{(oil.phosphorus).toLocaleString()}},
            <fmt:message key="extraction.oil.explosion"/>:
            {{(oil.explosionT).toLocaleString()}}
        </div>
    </div>
</div>
</html>