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
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.api.edit = '${edit}';
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
    <div v-if="items.length == 0" style="color: darkgray; text-align: center; width: 100%">
        <fmt:message key="empty.list"/>
    </div>
    <transition-group  name="flip-list" tag="div" class="container">
        <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
             class="container-item" :class="'container-item-' + new Date(value.item.date).getDay()" v-on:click="edit(value.item.id)">
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
                    {{value.item.shipper}}
                </b>
            </span>
            </div>
            <div class="lower-row">
            <span  style="font-size: 10pt;">
                <fmt:message key="transportation.time.in"/>
                <span v-if="value.item.timeIn.id" style="font-weight: bold">
                    {{new Date(value.item.timeIn.time).toLocaleDateString().substring(0, 5)}}
                    {{new Date(value.item.timeIn.time).toLocaleTimeString().substring(0, 5)}}
                </span>
                <span v-else="">
                    --.-- --:--
                </span>

            </span>
            <span>
                <fmt:message key="transportation.automobile"/>:
                <span v-if="value.item.vehicle.id">
                    {{value.item.vehicle.model}}
                    '{{value.item.vehicle.number}}'
                    <template v-if="value.item.vehicle.trailer">
                        '{{value.item.vehicle.trailer}}'
                    </template>
                </span>
                <span v-else>
                    <fmt:message key="no.data"/>
                </span>
            </span>
            <span>
                <fmt:message key="transportation.driver"/>:
                <span v-if="value.item.driver.id">
                    {{value.item.driver.person.value}}
                </span>
                <span v-else>
                    <fmt:message key="no.data"/>
                </span>
            </span>

                <div style="display: block; font-size: 10pt">
                    <div v-if="value.item.analyses.sun.id">
                        <fmt:message key="sun.humidity.1"/>:{{value.item.analyses.sun.humidity1}},
                        <fmt:message key="sun.humidity.2"/>:{{value.item.analyses.sun.humidity2}},
                        <fmt:message key="sun.soreness"/>:{{value.item.analyses.sun.soreness}},
                        <fmt:message key="sun.oiliness"/>:{{value.item.analyses.sun.oiliness}}
                    </div>
                    <div v-if="value.item.analyses.oil.id">
                        <fmt:message key="sun.acid.value"/>:{{value.item.analyses.oil.acid}},
                        <fmt:message key="oil.peroxide"/>:{{value.item.analyses.oil.peroxide}},
                        <fmt:message key="oil.phosphorus"/>:{{value.item.analyses.oil.phosphorus}}
                    </div>
                    <div v-if="value.item.analyses.cake.id">
                        <fmt:message key="sun.humidity"/>:{{value.item.analyses.cake.humidity}},
                        <fmt:message key="cake.protein"/>:{{value.item.analyses.cake.protein}},
                        <fmt:message key="cake.cellulose"/>:{{value.item.analyses.cake.cellulose}},
                        <fmt:message key="sun.oiliness"/>:{{value.item.analyses.cake.oiliness}}
                    </div>
                </div>
            </div>
        </div>
    </transition-group>
</div>

</html>