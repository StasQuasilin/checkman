<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<jsp:include page="vroHeader.jsp"/>
<style>
    .selector tr:hover{
        font-weight: bold;
        text-decoration: underline;
    }
</style>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/templates/list.vue"></script>
<script src="${context}/vue/templates/vroList.vue"></script>
<script>

    vroList.limit = 14;
    vroList.api.editDaily = '${dailyEdit}';
    vroList.api.editGranules = '${granules}';

    <c:forEach items="${forpress}" var="fp">
    vroList.forpress.push({
        value:'${fp.name}'
    });
    </c:forEach>
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        vroList.handler(a);
    });
    </c:forEach>
</script>

<div id="container" style="height: 100%; overflow-y: scroll">
    <div v-for="(value, key) in getItems()" class="container-item"
         :class="'container-item-' + new Date(value.item.date).getDay()" style="padding: 4pt; display: flex; flex-direction: column">
        <div class="turn-date" :class="'t-' + value.item.number">
            <span>
            {{new Date(value.item.date).toLocaleDateString()}}
            </span>
            <span>
                <fmt:message key="turn"/><span>&nbsp;#</span>{{value.item.number}}
            </span>
        </div>
        <div style="padding-left: 12pt">
            <table style="font-size: 10pt;">
                <tr>
                    <th rowspan="2" style="width: 4em">
                        <fmt:message key="time"/>
                    </th>
                    <th rowspan="2">
                        <span style="width: 4em; font-size: 8pt; word-wrap: break-spaces">
                            <fmt:message key="dry.oiliness"/>
                        </span>
                    </th>
                    <td colspan="2" align="center">
                        <fmt:message key="vro.sun.before"/>
                    </td>
                    <td colspan="2" align="center">
                        <fmt:message key="vro.sun.after"/>
                    </td>
                    <th rowspan="2">
                        <span style="width: 7em">
                            <fmt:message key="vro.huskiness"/>
                        </span>
                    </th>
                    <th rowspan="2">
                        <span style="width: 7em; display: inline-block">
                            <fmt:message key="vro.kernel.offset"/>
                        </span>
                    </th>
                    <th rowspan="2">
                        <span style="width: 7em; display: inline-block">
                            <fmt:message key="vro.pulp.humidity.1"/>
                        </span>
                    </th>
                    <th rowspan="2">
                        <span style="width: 7em; display: inline-block">
                            <fmt:message key="vro.pulp.humidity.2"/>
                        </span>
                    </th>
                    <td v-for="fp in forpress" colspan="2" align="center">
                        {{fp.value}}
                    </td>
                </tr>
                <tr>
                    <th>
                        <span style="width: 6em; display: inline-block">
                            <fmt:message key="sun.humidity"/>
                        </span>
                    </th>
                    <th>
                        <span style="width: 6em; display: inline-block">
                            <fmt:message key="sun.soreness"/>
                        </span>
                    </th>
                    <th>
                        <span style="width: 6em; display: inline-block">
                            <fmt:message key="sun.humidity"/>
                        </span>
                    </th>
                    <th>
                        <span style="width: 6em; display: inline-block">
                            <fmt:message key="sun.soreness"/>
                        </span>
                    </th>
                    <template v-for="fp in forpress">
                        <th>
                        <span style="width: 4em; display: inline-block">
                            <fmt:message key="sun.humidity.short"/>
                        </span>
                        </th>
                        <th>
                        <span style="width: 4em; display: inline-block">
                            <fmt:message key="sun.oiliness.short"/>
                        </span>
                        </th>
                    </template>
                </tr>
                <tr class="selectable" v-for="crude in sortedCrudes(value.item.crudes)"
                    :id="crude.id" onclick="editableModal('${crudeEdit}')">
                    <td align="center" :id="crude.id" >
                        {{new Date(crude.time).toLocaleTimeString().substring(0, 5)}}
                    </td>
                    <td style="text-align: center">
                        <template v-if="crude.dry > 0">
                            {{crude.dry.toLocaleString()}}
                        </template>
                        <template v-else>
                            -
                        </template>
                    </td>
                    <td align="center" :id="crude.id" >
                        {{(crude.humidityBefore).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id" >
                        {{(crude.sorenessBefore).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id" >
                        {{(crude.humidityAfter).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id">
                        {{(crude.sorenessAfter).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id">
                        {{(crude.huskiness).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id">
                        {{(crude.kernelOffset).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id">
                        {{(crude.pulpHumidity1).toLocaleString()}}
                    </td>
                    <td align="center" :id="crude.id">
                        {{(crude.pulpHumidity2).toLocaleString()}}
                    </td>
                    <template v-for="fp in forpress">
                        <td align="center">
                            <span v-if="crude.cakes[fp.value]">
                                {{crude.cakes[fp.value].humidity}}
                            </span>
                            <span v-else>
                                --
                            </span>
                        </td>
                        <td align="center">
                            <span v-if="crude.cakes[fp.value]">
                                {{crude.cakes[fp.value].oiliness}}
                            </span>
                            <span v-else>
                                --
                            </span>
                        </td>
                    </template>
                </tr>
            </table>
        </div>
        <div style="padding-left: 8pt; font-size: 10pt" v-for="oil in value.item.oil"
             class="selectable" :id="oil.id" onclick="editableModal('${oilEdit}')">
            <b>
                <fmt:message key="vro.press.oil"/>
            </b>
            <fmt:message key="sun.acid.value"/>:
            <b>
                {{(oil.acid).toLocaleString()}},
            </b>
            <fmt:message key="oil.peroxide"/>:
            <b>
                {{(oil.peroxide).toLocaleString()}},
            </b>
            <fmt:message key="oil.phosphorus"/>:
            <b>
                {{(oil.phosphorus).toLocaleString()}},
            </b>
            <fmt:message key="oil.degrease.impurity"/>:
            <b>
                {{(oil.impurity).toLocaleString()}},
            </b>
            <fmt:message key="oil.humidity"/>
            <b>
                {{(oil.humidity).toLocaleString()}},
            </b>
            <fmt:message key="oil.color.value"/>:
            <b>
                {{oil.color}}
            </b>
        </div>
        <table v-if="value.item.granulas.length > 0">
            <caption style="text-align: left">
                <b>
                    <fmt:message key="granules"/>
                </b>
            </caption>
            <tr>
                <th>
                   <fmt:message key="date_time"/>
                </th>
                <th>
                    <fmt:message key="vro.volume.density"/>
                </th>
                <th>
                    <fmt:message key="sun.humidity"/>
                </th>
                <th>
                    <fmt:message key="dust"/>
                </th>
                <th>
                    &nbsp;
                </th>
            </tr>
            <tr class="selectable" v-for="g in sortedGranulas(value.item.granulas)" :id="g.id" v-on:click="editGranules(g.id)">
                <td>
                    <span style="font-size: 8pt">
                        {{new Date(g.time).toLocaleDateString().substring(0, 5)}}
                    </span>
                    <b>
                        {{new Date(g.time).toLocaleTimeString().substring(0, 5)}}
                    </b>
                </td>
                <td>
                    <b>
                        {{(g.density).toLocaleString()}}
                    </b>
                </td>
                <td>
                    <b>
                        {{(g.humidity).toLocaleString()}}
                    </b>
                </td>
                <td>
                    <b>
                        {{(g.dust).toLocaleString()}}
                    </b>
                </td>
                <td>
                    <b v-if="g.match" style="color: green">
                        &check; <fmt:message key="match.dstu"/>
                    </b>
                    <span v-else style="color: orangered">
                        &times; <fmt:message key="dsnt.match.dstu"/>
                    </span>
                </td>
            </tr>
        </table>
        <div style="padding-left: 8pt; font-size: 10pt">
            <table style="font-size: 10pt">
                <tr>
                    <td colspan="3">
                        <b>
                            <fmt:message key="vro.turn.analyses"/>
                        </b>
                    </td>
                    <td colspan="2" align="center">
                        <fmt:message key="vro.sun.before"/>
                    </td>
                    <td colspan="2" align="center">
                        <fmt:message key="vro.sun.after"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="dry.oiliness"/>:
                        {{middle(value.item).dry.toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="vro.huskiness"/>:
                        {{(middle(value.item).huskiness).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="vro.kernel.offset"/>:
                        {{(middle(value.item).kernelOffset).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="sun.humidity"/>:
                        {{(middle(value.item).humidityBefore).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="sun.soreness"/>:
                        {{(middle(value.item).sorenessBefore).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="sun.humidity"/>:
                        {{(middle(value.item).humidityAfter).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="sun.soreness"/>:
                        {{(middle(value.item).sorenessAfter).toLocaleString()}}
                    </td>
                </tr>
                <tr v-for="daily in value.item.dailies" class="selectable"
                    :id="daily.id" v-on:click="editDaily(daily.id)">
                    <td>
                        <fmt:message key="dry.oiliness"/>
                        <template v-if="daily.dry > 0">
                            {{daily.dry.toLocaleString()}}
                        </template>
                        <template v-else>
                            -
                        </template>
                    </td>
                    <td>
                        <fmt:message key="kernel.humidity"/>:
                        {{(daily.kernelHumidity).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="husk.humidity"/>:
                        {{(daily.huskHumidity).toLocaleString()}}
                    </td>
                    <td colspan="2">
                        <fmt:message key="husk.soreness"/>:
                        {{(daily.huskSoreness).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="kernel.percent"/>:
                        {{(daily.kernelPercent).toLocaleString()}}
                    </td>
                    <td>
                        <fmt:message key="husk.percent"/>:
                        {{(daily.huskPercent).toLocaleString()}}
                    </td>
                </tr>

            </table>
        </div>
        <div style="padding-left: 8pt; font-size: 10pt" v-for="oilMass in value.item.oilMass" class="selectable">
            <table style="font-size: 9pt">
                <tr>
                    <th colspan="4">
                        <fmt:message key="vro.daily.oil.mass"/>
                    </th>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        <fmt:message key="sun.humidity"/>
                    </td>
                    <td>
                        <fmt:message key="wet.indicator"/>
                    </td>
                    <td>
                        <fmt:message key="dry.indicator"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="oil.mass.fraction.seed"/>
                    </td>
                    <td>
                        {{oilMass.seedHumidity}}
                    </td>
                    <td>
                        {{oilMass.seedWet}}
                    </td>
                    <td>
                        {{oilMass.seedDry}}
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="oil.mass.fraction.husk"/>
                    </td>
                    <td>
                        {{oilMass.huskHumidity}}
                    </td>
                    <td>
                        {{oilMass.huskWet}}
                    </td>
                    <td>
                        {{oilMass.huskDry}}
                    </td>
                </tr>
                <tr v-for="fp in oilMass.forpress">
                    <td>
                        {{fp.forpress}}
                    </td>
                    <td>
                        {{fp.humidity}}
                    </td>
                    <td>
                        {{fp.wet}}
                    </td>
                    <td>
                        {{fp.dry}}
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
</html>