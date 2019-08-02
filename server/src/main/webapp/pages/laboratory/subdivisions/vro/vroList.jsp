<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" style="display: inline">
    <link rel="stylesheet" href="${context}/css/drop-menu.css">
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="analyses"/> &#9660;</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${crudeEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.crude"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${oilEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.oil"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${dailyEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.daily"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${oilMassFraction}')">
                <span>
                    <fmt:message key="oil.mass.fraction"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${oilMassFractionDry}')">
                <span>
                    <fmt:message key="oil.mass.fraction.dry"/>
                </span>
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
<style>
    .selector tr:hover{
        font-weight: bold;
        text-decoration: underline;
    }
</style>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.limit = 14;
    list.forpress = [];
    <c:forEach items="${forpress}" var="fp">
    list.forpress.push({
        value:'${fp.name}'
    });
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
            {{(oil.acid).toLocaleString()}},
            <fmt:message key="oil.peroxide"/>:
            {{(oil.peroxide).toLocaleString()}},
            <fmt:message key="oil.phosphorus"/>:
            {{(oil.phosphorus).toLocaleString()}},
            <fmt:message key="oil.color.value"/>:
            {{oil.color}}
        </div>
        <div style="padding-left: 8pt; font-size: 10pt" v-for="daily in value.item.dailies"
             class="selectable" :id="daily.id" onclick="editableModal('${dailyEdit}')">
            <b>
                <fmt:message key="vro.daily"/>
            </b>
            <fmt:message key="kernel.humidity"/>:
            {{(daily.kernelHumidity).toLocaleString()}},
            <fmt:message key="husk.humidity"/>:
            {{(daily.huskHumidity).toLocaleString()}},
            <fmt:message key="husk.soreness"/>:
            {{(daily.huskSoreness).toLocaleString()}},
            <fmt:message key="kernel.percent"/>:
            {{(daily.kernelPercent).toLocaleString()}},
            <fmt:message key="husk.percent"/>:
            {{(daily.huskPercent).toLocaleString()}}
        </div>
        <div style="padding-left: 8pt; font-size: 10pt" v-for="oilMass in value.item.oilMass" class="selectable">
            <b>
                <fmt:message key="vro.daily.oil.mass"/>
            </b>
            <fmt:message key="oil.mass.fraction.seed"/>:
            {{oilMass.seed}},
            <fmt:message key="oil.mass.fraction.seed.humidity"/>:
            {{oilMass.seedHumidity}},
            <fmt:message key="oil.mass.fraction.husk"/>:
            {{oilMass.husk}},
            <fmt:message key="oil.mass.fraction.husk.humidity"/>:
            {{oilMass.huskHumidity}}
            <div v-for="fp in oilMass.forpress">
                <b>
                    {{fp.forpress}}
                </b>
                <fmt:message key="oilcake"/>:
                {{fp.oilcake}},
                <fmt:message key="oilcake.humidity"/>:
                {{fp.oilcakeHumidity}}
            </div>
        </div>
        <div style="padding-left: 8pt; font-size: 10pt"
             v-for="oilMass in value.item.oilMassDry" :id="oilMass.id"
             onclick="editableModal('${oilMassFractionDry}')"
             class="selectable">
            <b>
                <fmt:message key="vro.daily.oil.mass.dry"/>
            </b>
            <fmt:message key="oil.mass.fraction.seed"/>:
            {{oilMass.seed}},
            <fmt:message key="oil.mass.fraction.husk"/>:
            {{oilMass.husk}}
            <template v-for="fp in oilMass.forpress">
                <b>
                    {{fp.forpress}}
                </b>
                <fmt:message key="oilcake"/>:
                {{fp.oilcake}}
            </template>
        </div>
    </div>
</div>
</html>