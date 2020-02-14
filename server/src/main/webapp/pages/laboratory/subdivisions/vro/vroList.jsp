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
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.middle = function(item){
        if (!item.middle){
            let middle = {
                humidityBefore:0,
                sorenessBefore:0,
                humidityAfter:0,
                sorenessAfter:0,
                kernelOffset:0,
                huskiness:0
            };
            for (var j in item.crudes){
                if (item.crudes.hasOwnProperty(j)){
                    let crude = item.crudes[j];
                    middle.humidityBefore += crude.humidityBefore;
                    middle.sorenessBefore += crude.sorenessBefore;
                    middle.humidityAfter += crude.humidityAfter;
                    middle.sorenessAfter += crude.sorenessAfter;
                    middle.kernelOffset += crude.kernelOffset;
                    middle.huskiness += crude.huskiness;
                }
            }
            let count = item.crudes.length;
            if (count > 0) {
                middle.humidityBefore /= count;
                middle.sorenessBefore /= count;
                middle.humidityAfter /= count;
                middle.sorenessAfter /= count;
                middle.kernelOffset /= count;
                middle.huskiness /= count;
            }
            item.middle = middle;

        }
        return item.middle;
    };
    list.limit = 14;
    list.api.editDaily = '${dailyEdit}';
    list.api.editGranules = '${granules}';
    list.editGranules = function(id){
        loadModal(this.api.editGranules, {id: id});
    };
    list.forpress = [];
    <c:forEach items="${forpress}" var="fp">
    list.forpress.push({
        value:'${fp.name}'
    });
    list.sort = function(){
        list.items.sort(function(a, b){
            return new Date(b.item.date) - new Date(a.item.date);
        })
    };
    list.editDaily = function(id){
        loadModal(this.api.editDaily, {id: id});
    };
    </c:forEach>
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
            <table style="font-size: 10pt;">
                <tr>
                    <th rowspan="2" style="width: 4em">
                        <fmt:message key="time"/>
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
                <tr class="selectable" v-for="crude in value.item.crudes"
                    :id="crude.id" onclick="editableModal('${crudeEdit}')">
                    <td align="center" :id="crude.id" >
                        {{new Date(crude.time).toLocaleTimeString().substring(0, 5)}}
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
        <div style="padding-left: 8pt; font-size: 10pt" v-for="g in value.item.granulas"
             class="selectable" :id="g.id" v-on:click="editGranules(g.id)">
            <b>
                <fmt:message key="granules"/>
            </b>
            <fmt:message key="vro.volume.density"/>:
            <b>
                {{(g.density).toLocaleString()}},
            </b>
            <fmt:message key="sun.humidity"/>:
            <b>
                {{(g.humidity).toLocaleString()}},
            </b>
            <fmt:message key="dust"/>:
            <b>
                {{(g.dust).toLocaleString()}},
            </b>
            <b v-if="g.match">
                <fmt:message key="match.dstu"/>
            </b>
            <b v-else>
                <fmt:message key="dsnt.match.dstu"/>
            </b>
        </div>
        <div style="padding-left: 8pt; font-size: 10pt">
            <table style="font-size: 10pt">
                <tr>
                    <td colspan="2">
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