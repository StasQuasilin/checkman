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
                <fmt:message key="vro.crude"/>
            </li>
            <li class="drop-menu-item"  onclick="loadModal('${oilEdit}')">
                <fmt:message key="vro.oil"/>
            </li>
        </ul>
    </div>
</div>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script>
    var filter_control={};
</script>
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.url = '${update}';
    deamon.doRequest();
</script>

<div id="container">
    <div v-for="turn in items" class="container-item" :class="rowName(turn.item.date)" style="padding: 4pt">
        <div>
            <span>
            {{new Date(turn.item.date).toLocaleDateString()}}
            </span>
            <span>
                <fmt:message key="turn"/><span> #</span>{{turn.item.number}}
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
                    <td colspan="2" align="center">
                        <span style="width: 4em">
                            F-300
                        </span>
                    </td>
                    <td colspan="2" align="center">
                        <span style="width: 4em">
                            F-100
                        </span>
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
                </tr>
                <tr v-for="crude in turn.item.crudes">
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
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                    <td align="center">
                        --
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
</html>