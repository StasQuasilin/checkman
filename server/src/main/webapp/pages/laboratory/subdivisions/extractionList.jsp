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
        <a class="drop-btn"><fmt:message key="analyses"/>&nbsp;+</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${crudeEdit}')">
                <fmt:message key="extraction.crude"/>
            </div>
            <div class="drop-menu drop-menu-item">
                <span>
                    <fmt:message key="laboratory.protein"/>&nbsp;>
                </span>
                <div class="drop-menu-content" style="top: 0; left: 100%; width: 10em">
                    <div class="drop-menu-item" onclick="loadModal('${storageProtein}')">
                        <fmt:message key="menu.extraction.storage.protein"/>
                    </div>
                    <div class="drop-menu-item" onclick="loadModal('${turnProtein}')">
                        <fmt:message key="menu.extraction.turn.protein"/>
                    </div>
                </div>
            </div>
            <div class="drop-menu drop-menu-item">
                <span>
                    <fmt:message key="extraction.raw.grease"/>&nbsp;>
                </span>
                <div class="drop-menu-content" style="top: 0; left: 100%; width: 10em">
                    <div class="drop-menu-item" onclick="loadModal('${storageGrease}')">
                        <fmt:message key="menu.extraction.storage.raw.grease"/>
                    </div>
                    <div class="drop-menu-item" onclick="loadModal('${turnGrease}')">
                        <fmt:message key="menu.extraction.turn.raw.grease"/>
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
    list.limit = 14;
    list.api.crudeEdit ='${crudeEdit}';
    list.api.proteinStorageEdit = '${storageProtein}';
    list.api.greaseStorageEdit = '${storageGrease}';
    list.api.proteinTurnEdit = '${turnProtein}';
    list.api.greaseTurnEdit = '${turnGrease}';
    list.api.oilEdit = '${oilEdit}';
    list.crudeEdit = function(id){
        this.edit(this.api.crudeEdit, id);
    };
    list.proteinStorageEdit = function(id){
        this.edit(this.api.proteinStorageEdit, id);
    };
    list.greaseStorageEdit = function(id){
        this.edit(this.api.greaseStorageEdit, id);
    };
    list.proteinTurnEdit = function(id){
        console.log('Edit turn protein ' + id);
        this.edit(this.api.proteinTurnEdit, id);
    };
    list.greaseTurnEdit = function(id){
        this.edit(this.api.greaseTurnEdit, id);
    };
    list.oilEdit = function(id){
        this.edit(this.api.oilEdit, id);
    };
    list.edit = function(api, id){
        loadModal(api + '?id=' + id, {id :id});
    };
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        list.handler(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(a){
            unSubscribe('${s}');
        });
        </c:forEach>
    }
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
                    <td colspan="4" align="center">
                        <fmt:message key="extraction.crude"/>
                    </td>
                    <td colspan="4" align="center">
                        <fmt:message key="extraction.meal"/>
                    </td>
                    <td rowspan="2" align="center">
                        <fmt:message key="extraction.crude.oil.humidity.short"/>
                    </td>
                    <td colspan="4" align="center">
                        <fmt:message key="storage"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="extraction.crude.humidity.income.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="extraction.crude.oilines.income.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="extraction.crude.small.fraction.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="extraction.crude.miscellas.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 5em;">
                            <fmt:message key="extraction.crude.humidity.short"/>
                        </span>
                    </th>
                    <th>
                        <span style="display: inline-block; width: 6em;">
                            <fmt:message key="extraction.crude.explosion.short"/>
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
                            <fmt:message key="nmr"/>
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
                <tr v-for="crude in value.item.crude" class="selectable" :item="crude.id">
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{new Date(crude.time).toLocaleTimeString().substring(0,5)}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.humidityIncome).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.oilinessIncome).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.fraction).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.miscellas).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.humidity).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.explosionTemperature).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.dissolvent).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.grease).toLocaleString()}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.oilHumidity).toLocaleString()}}
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageProtein[crude.time]" style="width: 100%; display: inline-block"
                              v-on:click="proteinStorageEdit(value.item.storageProtein[crude.time].id)">
                            {{value.item.storageProtein[crude.time].protein}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageProtein[crude.time]" style="width: 100% display: inline-block"
                              v-on:click="proteinStorageEdit(value.item.storageProtein[crude.time].id)">
                            {{value.item.storageProtein[crude.time].nuclear}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageGrease[crude.time]" style="width: 100% display: inline-block"
                              v-on:click="greaseStorageEdit(value.item.storageGrease[crude.time].id)">
                            {{value.item.storageGrease[crude.time].grease}}
                        </span>
                        <span v-else>
                            --
                        </span>
                    </td>
                    <td align="center">
                        <span v-if="value.item.storageGrease[crude.time]" style="width: 100% display: inline-block"
                              v-on:click="greaseStorageEdit(value.item.storageGrease[crude.time].id)">
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
            <div v-for="protein in value.item.turnProtein" v-on:click="proteinTurnEdit(protein.id)"
                 style="font-size: 10pt; display: inline-block" class="selectable round">
                <b>
                    <fmt:message key="title.extraction.turn.protein"/>:
                </b>
                <fmt:message key="cake.protein"/>:
                {{protein.protein}},
                <fmt:message key="sun.humidity"/>:
                {{protein.humidity}},
                <fmt:message key="oil.nmr.grease"/>:
                {{protein.nuclear}}
            </div>
            <div v-for="grease in value.item.turnGrease" v-on:click="greaseTurnEdit(grease.id)"
                 style="font-size: 10pt; display: inline-block" class="selectable round">
                <b>
                    <fmt:message key="title.extraction.turn.grease"/>:
                </b>
                <fmt:message key="extraction.raw.grease"/>:
                {{grease.grease}},
                <fmt:message key="sun.humidity"/>:
                {{grease.humidity}}
            </div>
        </div>
        <div v-for="oil in value.item.oil" style="font-size: 10pt" class="selectable round"
            v-on:click="oilEdit(oil.id)">
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