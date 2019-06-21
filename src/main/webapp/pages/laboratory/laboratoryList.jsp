<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<div id="container-header" class="container-header">
    <link rel="stylesheet" href="${context}/css/drop-menu.css">
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="document.print"/>&nbsp;&#9660;</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${print}')">
                <fmt:message key="print.daily.report"/>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${print}')">
                <fmt:message key="print.monthly.report"/>
            </div>
        </div>
    </div>
</div>
<script>
    req_filter = {}
</script>
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.setUrls('${update}', '${edit}')
</script>
<div id="container">
    <div v-if="items.length == 0" style="color: darkgray; text-align: center; width: 100%">
        <fmt:message key="empty.list"/>
    </div>
    <transition-group  name="flip-list" tag="div" class="container">
        <div v-for="(value, key) in filteredItems()" :key="value.item.id" :id="value.item.id"
             class="container-item" :class="rowName(value.item.date)" v-on:click="show(value.item.id)">
            <div class="upper-row">
                {{new Date(value.item.date).toLocaleDateString()}}
            <span>
                <fmt:message key="deal.organisation"/>:
                <b>
                    {{value.item.organisation.value}}
                </b>
            </span>
            <span>
                <fmt:message key="deal.product"/>:
                <b>
                    {{value.item.product.name}},
                </b>
                <fmt:message key="deal.from"/>:
                <b>
                    {{value.item.realisation}}
                </b>
            </span>
            </div>
            <div class="lower-row">
            <span>
                <fmt:message key="transportation.automobile"/>:
                <span v-if="value.item.transportation.vehicle.id">
                    {{value.item.transportation.vehicle.model}}
                    '{{value.item.transportation.vehicle.number}}'
                    <template v-if="value.item.transportation.vehicle.trailer">
                        '{{value.item.transportation.vehicle.trailer}}'
                    </template>
                </span>
                <span v-else>
                    <fmt:message key="no.data"/>
                </span>
            </span>
            <span>
                <fmt:message key="transportation.driver"/>:
                <span v-if="value.item.transportation.driver.id">
                    {{value.item.transportation.driver.person.value}}
                </span>
                <span v-else>
                    <fmt:message key="no.data"/>
                </span>
            </span>
                <div style="display: inline-block; font-size: 10pt">
                    <div v-if="value.item.transportation.analyses.sun.id">
                        <fmt:message key="sun.humidity.1"/>:{{value.item.transportation.analyses.sun.humidity1}},
                        <fmt:message key="sun.humidity.2"/>:{{value.item.transportation.analyses.sun.humidity2}},
                        <fmt:message key="sun.soreness"/>:{{value.item.transportation.analyses.sun.soreness}},
                        <fmt:message key="sun.oiliness"/>:{{value.item.transportation.analyses.sun.oiliness}}
                    </div>
                    <div v-if="value.item.transportation.analyses.oil.id">
                        <fmt:message key="sun.acid.value"/>:{{value.item.transportation.analyses.oil.acid}},
                        <fmt:message key="oil.peroxide"/>:{{value.item.transportation.analyses.oil.peroxide}},
                        <fmt:message key="oil.phosphorus"/>:{{value.item.transportation.analyses.oil.phosphorus}}
                    </div>
                    <div v-if="value.item.transportation.analyses.cake.id">
                        <fmt:message key="sun.humidity"/>:{{value.item.transportation.analyses.cake.humidity}},
                        <fmt:message key="cake.protein"/>:{{value.item.transportation.analyses.cake.protein}},
                        <fmt:message key="cake.cellulose"/>:{{value.item.transportation.analyses.cake.cellulose}},
                        <fmt:message key="sun.oiliness"/>:{{value.item.transportation.analyses.cake.oiliness}}
                    </div>
                </div>
            </div>
        </div>
    </transition-group>
</div>

</html>