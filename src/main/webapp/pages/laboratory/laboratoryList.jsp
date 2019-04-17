<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.setUrls('${update}', '${edit}')
</script>
<transition-group  name="flip-list" tag="div" class="container" id="container">
    <div v-for="(value, key) in items" v-bind:key="value.item.id" v-bind:id="value.item.id"
         class="container-item" v-bind:class="rowName(value.item.date)" v-on:click="show(value.item.id)">
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
                <div v-for="sun in value.item.transportation.analyses.sun">
                    <fmt:message key="sun.humidity"/>:{{sun.humidity}},
                    <fmt:message key="sun.soreness"/>:{{sun.soreness}},
                    <fmt:message key="sun.oiliness"/>:{{sun.oiliness}}
                </div>
                <div v-for="oil in value.item.transportation.analyses.oil">
                    <fmt:message key="sun.acid.value"/>:{{oil.acid}},
                    <fmt:message key="oil.peroxide"/>:{{oil.peroxide}},
                    <fmt:message key="oil.phosphorus"/>:{{oil.phosphorus}}
                </div>
                <div v-for="cake in value.item.transportation.analyses.cake">
                    <fmt:message key="sun.humidity"/>:{{cake.humidity}},
                    <fmt:message key="cake.protein"/>:{{cake.protein}},
                    <fmt:message key="cake.cellulose"/>:{{cake.cellulose}},
                    <fmt:message key="sun.oiliness"/>:{{cake.oiliness}}
                </div>
            </div>
        </div>
    </div>
</transition-group>
</html>