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
        <a class="drop-btn"><fmt:message key="analyses"/>&nbsp;&#9660;</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${crudeEdit}')">
                <fmt:message key="extraction.crude"/>
            </div>
            <div class="drop-menu drop-menu-item">
                <span>
                    <fmt:message key="laboratory.protein"/>&nbsp;&#9205;
                </span>
                <div class="drop-menu-content" style="top: 0; left: 100%">
                    <div class="drop-menu-item" onclick="loadModal('${turnProtein}')">
                        <fmt:message key="menu.extraction.turn.protein"/>
                    </div>
                    <div class="drop-menu-item" onclick="loadModal('${storageProtein}')">
                        <fmt:message key="menu.extraction.storage.protein"/>
                    </div>
                </div>
            </div>
            <div class="drop-menu drop-menu-item">
                <span>
                    Сирий жир&nbsp;&#9205;
                </span>
                <div class="drop-menu-content" style="top: 0; left: 100%">
                    <div class="drop-menu-item" onclick="loadModal('${turnGrease}')">
                        <fmt:message key="menu.extraction.turn.raw.grease"/>
                    </div>
                    <div class="drop-menu-item" onclick="loadModal('${storageGrease}')">
                        <fmt:message key="menu.extraction.storage.raw.grease"/>
                    </div>
                </div>
            </div>
            <div class="drop-menu-item"  onclick="loadModal('${oilEdit}')">
                <fmt:message key="extraction.oil"/>
            </div>
        </div>
    </div>
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="document.print"/>&nbsp;&#9660;</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${dailyPrint}')">
                <fmt:message key="print.daily.report"/>
            </div>
        </div>
    </div>
</div>
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.api.update = '${update}';
    list.doRequest();
</script>
<div id="container">
    <div v-for="(value, key) in items" class="container-item"
         :class="'container-item-' + new Date(value.item.date).getDay()" style="padding: 4pt">
        <div class="turn-date" :class="'t-' + value.item.number">
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
                    <th rowspan="2">
                        <span style="display: inline-block; width: 4em;">
                            <fmt:message key="time"/>
                        </span>
                    </th>
                    <td colspan="3" align="center">
                        <fmt:message key="extraction.crude"/>
                    </td>
                    <td colspan="3" align="center">
                        <fmt:message key="extraction.meal"/>
                    </td>
                    <td colspan="4" align="center">
                        <fmt:message key="storage"/>
                    </td>
                </tr>
                <tr>
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
                        <span style="display: inline-block; width: 7em;">
                            <fmt:message key="extraction.crude.humidity.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 7em;">
                            <fmt:message key="extraction.crude.dissolvent.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 7em;">
                            <fmt:message key="extraction.crude.grease"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="cake.protein"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="sun.humidity"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="sun.oiliness.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="sun.humidity"/>
                        </span>
                    </th>
                </tr>
                <tr v-for="crude in (value.item.crudes)" class="selectable" :item="crude.id" v-on:click="edit(crude.id)">
                    <td align="center" :item="crude.id">
                        {{new Date(crude.time).getHours()}}:{{new Date(crude.time).getMinutes()}}
                    </td>
                    <td align="center" :item="crude.id">
                        {{(crude.humidityIncome).toLocaleString()}}
                    </td>
                    <td align="center" :item="crude.id">
                        {{(crude.fraction).toLocaleString()}}
                    </td>
                    <td align="center" :item="crude.id">
                        {{(crude.miscellas).toLocaleString()}}
                    </td>
                    <td align="center" :item="crude.id">
                        {{(crude.humidity).toLocaleString()}}
                    </td>
                    <td align="center" :item="crude.id">
                        {{(crude.dissolvent).toLocaleString()}}
                    </td>
                    <td align="center" :item="crude.id">
                        {{(crude.grease).toLocaleString()}}
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageProtein[crude.time]">
                            {{value.item.storageProtein[crude.time].protein}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageProtein[crude.time]">
                            {{value.item.storageProtein[crude.time].humidity}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageGrease[crude.time]">
                            {{value.item.storageGrease[crude.time].grease}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageGrease[crude.time]">
                            {{value.item.storageGrease[crude.time].humidity}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <div v-for="protein in value.item.turnProtein"
                 style="font-size: 10pt; display: inline-block" class="selectable">
                <b>
                    <fmt:message key="title.extraction.turn.protein"/>:
                </b>
                <fmt:message key="cake.protein"/>:
                {{protein.protein}},
                <fmt:message key="sun.humidity"/>:
                {{protein.humidity}}
            </div>
            <div v-for="grease in value.item.turnGrease"
                 style="font-size: 10pt; display: inline-block" class="selectable">
                <b>
                    <fmt:message key="title.extraction.turn.grease"/>:
                </b>
                <fmt:message key="extraction.raw.grease"/>:
                {{grease.grease}},
                <fmt:message key="sun.humidity"/>:
                {{grease.humidity}}
            </div>
        </div>
        <div v-for="oil in value.item.oil" style="font-size: 10pt" class="selectable">
            <b>
                <fmt:message key="extraction.oil"/>:
            </b>
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