<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">

<script src="${context}/vue/templates/list.vue"></script>
<script src="${context}/vue/templates/laboratoryList.vue"></script>
<script src="${context}/vue/extractionList.vue"></script>
<c:if test="${role eq 'analyser' or role eq 'admin'}">
    <jsp:include page="extractionHeader.jsp"/>
    <script>
        extractionList.api.crudeEdit ='${crudeEdit}';
        extractionList.api.proteinStorageEdit = '${storageProtein}';
        extractionList.api.greaseStorageEdit = '${storageGrease}';
        extractionList.api.proteinTurnEdit = '${turnProtein}';
        extractionList.api.greaseTurnEdit = '${turnGrease}';
        extractionList.api.celluloseEdit = '${turnCellulose}';
        extractionList.api.oilEdit = '${oilEdit}';
    </script>
</c:if>
<script>
    extractionList.limit = 14;

    extractionList.turns.push( {
        number:1,
        begin:8,
        end:20
    });
    extractionList.turns.push( {
        number:2,
        begin:20,
        end:8
    })
    extractionList.offsets = [
        0, 2, 4, 6, 8, 10
    ]
    extractionList.checkTurns();
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        extractionList.handler(a);
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
            <table style="font-size: 10pt; border: 1pt">
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
                <template  v-for="offset in offsets">
                    <tr class="selectable" v-for="crude in getCrude(value.item, offset, 30)" :item="crude.id">
                        <td align="center">
                            {{new Date(crude.time).toLocaleTimeString().substring(0, 5)}}
                        </td>
                        <template v-if="!crude.empty">
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
                                <span v-if="crude.protein" style="width: 100%; display: inline-block"
                                      v-on:click="proteinStorageEdit(crude.protein.id)">
                                    {{crude.protein.protein}}
                                </span>
                                <span v-else>
                                    --
                                </span>
                            </td>
                            <td align="center">
                                <span v-if="crude.protein" style="width: 100%; display: inline-block"
                                      v-on:click="proteinStorageEdit(crude.protein.id)">
                                    {{crude.protein.nuclear}}
                                </span>
                                <span v-else>
                                    --
                                </span>
                            </td>
                            <td align="center">
                                <span v-if="crude.storageGrease" style="width: 100%; display: inline-block"
                                      v-on:click="greaseStorageEdit(crude.storageGrease.id)">
                                    {{crude.storageGrease.grease}}
                                </span>
                                <span v-else>
                                    --
                                </span>
                            </td>
                            <td align="center">
                                <span v-if="crude.storageGrease" style="width: 100%; display: inline-block"
                                      v-on:click="greaseStorageEdit(crude.storageGrease.id)">
                                    {{crude.storageGrease.humidity}}
                                </span>
                                <span v-else>
                                    --
                                </span>
                            </td>
                        </template>
                        <template v-else>
                            <td colspan="13" v-on:click="crudeEdit(crude.id, crude.time)" class="selectable">
                                <fmt:message key="press.here"/>
                            </td>
<%--                            <td colspan="2" v-on:click="proteinStorageEdit(-1)">--%>
<%--                                <fmt:message key="press.here"/>--%>
<%--                            </td>--%>
<%--                            <td colspan="2" v-on:click="greaseStorageEdit(-1)">--%>
<%--                                <fmt:message key="press.here"/>--%>
<%--                            </td>--%>
                        </template>
                    </tr>
                </template>
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
            <div v-for="granulas in sortedGranulas(value.item.granulas)" class="selectable round"
                 style="font-size: 10pt">
                <span>
                    {{new Date(granulas.time).toLocaleDateString().substring(0, 5)}}
                </span>
                <b>
                    {{new Date(granulas.time).toLocaleTimeString().substring(0, 5)}}
                </b>
                <fmt:message key="meal.scree"/>:{{granulas.scree}},
                <fmt:message key="meal.density"/>:{{granulas.density}},
                <fmt:message key="extraction.crude.humidity"/>:{{granulas.humidity}},
                <fmt:message key="meal.length"/>:{{granulas.length}},
                <fmt:message key="meal.diameter"/>:{{granulas.diameter}}
            </div>

            <div v-for="cellulose in value.item.cellulose" v-on:click="editCellulose(cellulose.id)"
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