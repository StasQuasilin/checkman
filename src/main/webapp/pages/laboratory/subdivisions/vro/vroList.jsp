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
        <ul class="drop-menu-content">
            <li class="drop-menu-item" onclick="loadModal('${crudeEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.crude"/>
                </span>
            </li>
            <li class="drop-menu-item"  onclick="loadModal('${oilEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.oil"/>
                </span>
            </li>
            <li class="drop-menu-item"  onclick="loadModal('${dailyEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.daily"/>
                </span>
            </li>
        </ul>
    </div>
</div>
<style>
    .selector tr:hover{
        font-weight: bold;
        text-decoration: underline;
    }
</style>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script>
    var filter_control={};
</script>
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.url = '${update}';
    deamon.forpress=[];
    <c:forEach items="${forpress}" var="fp">
    deamon.forpress.push({
        value:'${fp.name}'
    });
    </c:forEach>
    deamon.doRequest();
</script>

<div id="container">
    <div v-for="(value, key) in items" class="container-item" :class="rowName(value.item.date)" style="padding: 4pt">
        <div>
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
                        <span style="width: 5em">
                            <fmt:message key="time"/>
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
                        <span style="width: 7em">
                            <fmt:message key="vro.kernel.offset"/>
                        </span>
                    </th>
                    <th rowspan="2">
                        <span style="width: 7em">
                            <fmt:message key="vro.pulp.humidity"/>
                        </span>
                    </th>
                    <td v-for="fp in forpress" colspan="2" align="center">
                        {{fp.value}}
                    </td>
                </tr>
                <tr>
                    <th>
                        <span style="width: 6em">
                            <fmt:message key="sun.humidity"/>
                        </span>
                    </th>
                    <th>
                        <span style="width: 6em">
                            <fmt:message key="sun.soreness"/>
                        </span>
                    </th>
                    <th>
                        <span style="width: 6em">
                            <fmt:message key="sun.humidity"/>
                        </span>
                    </th>
                    <th>
                        <span style="width: 6em">
                            <fmt:message key="sun.soreness"/>
                        </span>
                    </th>
                    <template v-for="fp in forpress">
                        <th>
                        <span style="width: 5em">
                            <fmt:message key="sun.humidity.short"/>
                        </span>
                        </th>
                        <th>
                        <span style="width: 5em">
                            <fmt:message key="sun.oiliness.short"/>
                        </span>
                        </th>
                    </template>
                </tr>
                <tr class="selector" v-for="crude in value.item.crudes">
                    <td align="center">
                        {{new Date(crude.time).toLocaleTimeString().substring(0, 5)}}
                    </td>
                    <td align="center">
                        {{(crude.humidityBefore).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.sorenessBefore).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.humidityAfter).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.sorenessAfter).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.huskiness).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.kernelOffset).toLocaleString()}}
                    </td>
                    <td align="center">
                        {{(crude.pulpHumidity).toLocaleString()}}
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
        <div style="padding-left: 8pt; font-size: 10pt" v-for="oil in value.item.oil">
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
        <div style="padding-left: 8pt; font-size: 10pt" v-for="daily in value.item.dailies">
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
    </div>
</div>
</html>