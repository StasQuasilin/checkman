<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<jsp:include page="extractionHeader.jsp"/>

<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
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
        if (this.api.proteinStorageEdit) {
            this.edit(this.api.proteinStorageEdit, id);
        }
    };
    list.greaseStorageEdit = function(id){
        if (this.api.greaseStorageEdit) {
            this.edit(this.api.greaseStorageEdit, id);
        }
    };
    list.proteinTurnEdit = function(id){
        if (this.api.proteinTurnEdit) {
            this.edit(this.api.proteinTurnEdit, id);
        }
    };
    list.greaseTurnEdit = function(id){
        if (this.api.greaseTurnEdit) {
            this.edit(this.api.greaseTurnEdit, id);
        }
    };
    list.oilEdit = function(id){
        if (this.api.oilEdit) {
            this.edit(this.api.oilEdit, id);
        }
    };
    list.edit = function(api, id){
        loadModal(api + '?id=' + id, {id :id});
    };
    list.sort = function(){
        list.items.sort(function(a, b){
            return new Date(b.item.date) - new Date(a.item.date);
        })
    };
    list.middle = function(item){
        if (!item.middle){
            let middle = {
                humidityIncome:0,
                oilinessIncome:0,
                fraction:0,
                miscellas:0,
                humidity:0,
                explosionTemperature:0,
                dissolvent:0,
                grease:0,
                oilHumidity:0
            };
            for (var j in item.crude){
                if (item.crude.hasOwnProperty(j)){
                    let crude = item.crude[j];
                    middle.humidityIncome += crude.humidityIncome;
                    middle.oilinessIncome += crude.oilinessIncome;
                    middle.fraction += crude.fraction;
                    middle.miscellas += crude.miscellas;
                    middle.humidity += crude.humidity;
                    middle.explosionTemperature += crude.explosionTemperature;
                    middle.dissolvent += crude.dissolvent;
                    middle.grease += crude.grease;
                    middle.oilHumidity += crude.oilHumidity;
                }
            }
            let count = item.crude.length;
            if (count > 0) {
                middle.humidityIncome /= count;
                middle.oilinessIncome /= count;
                middle.fraction /= count;
                middle.miscellas /= count;
                middle.humidity /= count;
                middle.explosionTemperature /= count;
                middle.explosionTemperature = Math.round(middle.explosionTemperature);
                middle.dissolvent /= count;
                middle.dissolvent = Math.round(middle.dissolvent * 10000) / 10000;
                middle.grease /= count;
                middle.oilHumidity /= count;
            }
            item.middle = middle;

        }
        return item.middle;
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
    <div v-for="(value, key) in getItems()" class="container-item"
         :class="'container-item-' + new Date(value.item.date).getDay()" style="padding: 4pt; display: inline-block">
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
                        {{crude.dissolvent}}
                    </td>
                    <td align="center" v-on:click="crudeEdit(crude.id)">
                        {{(crude.grease)}}
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
                <tr>
                    <th>
                        <fmt:message key="middle"/>
                    </th>
                    <th>
                        {{(middle(value.item).humidityIncome).toLocaleString()}}
                    </th>
                    <th>
                        {{(middle(value.item).oilinessIncome).toLocaleString()}}
                    </th>
                    <th>
                        {{(middle(value.item).fraction).toLocaleString()}}
                    </th>
                    <th>
                        {{(middle(value.item).miscellas).toLocaleString()}}
                    </th>
                    <th>
                        {{(middle(value.item).humidity).toLocaleString()}}
                    </th>
                    <th>
                        {{(middle(value.item).explosionTemperature).toLocaleString()}}
                    </th>
                    <th>
                        {{middle(value.item).dissolvent}}
                    </th>
                    <th>
                        {{(middle(value.item).grease).toLocaleString()}}
                    </th>
                    <th>
                        {{(middle(value.item).oilHumidity).toLocaleString()}}
                    </th>
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
            <div v-if="value.item.granulas.length > 0" style="font-size: 10pt">
                <b>
                    <fmt:message key="title.extraction.turn.granulas"/>:
                </b>
            </div>
            <div v-for="granulas in value.item.granulas" class="selectable round"
                 style="font-size: 10pt">
                <b>
                    {{new Date(granulas.time).toLocaleTimeString().substring(0, 5)}}
                </b>
                <fmt:message key="meal.scree"/>:{{granulas.scree}},
                <fmt:message key="meal.density"/>:{{granulas.density}},
                <fmt:message key="extraction.crude.humidity"/>:{{granulas.humidity}},
                <fmt:message key="meal.length"/>:{{granulas.length}},
                <fmt:message key="meal.diameter"/>:{{granulas.diameter}}
            </div>

            <div v-for="cellulose in value.item.cellulose"
                 style="font-size: 10pt; display: inline-block" class="selectable round">
                <b>
                    <fmt:message key="menu.extraction.turn.cellulose"/>:
                </b>
                <fmt:message key="cake.cellulose"/>:{{cellulose.cellulose.toLocaleString()}},
                <fmt:message key="extraction.crude.humidity"/>:{{cellulose.humidity.toLocaleString()}},
                <fmt:message key="dry.indicator"/>:{{cellulose.dry.toLocaleString()}}
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
            {{(oil.explosionTemperature).toLocaleString()}}
        </div>

    </div>
</div>
</html>