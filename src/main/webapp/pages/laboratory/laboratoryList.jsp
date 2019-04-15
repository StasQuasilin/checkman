<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var filter_control = {}
</script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/dataList.js"></script>
<script>

    deamon.setUrls('${updateLink}', '${editLink}')
</script>
<transition-group  name="flip-list" tag="div" class="container" id="container">
    <div v-for="(value, key) in items" v-bind:key="value.id" v-bind:id="value.id"
         class="container-item" v-bind:class="rowName(value.date)" v-on:click="show(value.id)">
        <div class="upper-row">
            {{new Date(value.date).toLocaleDateString()}}
            <span>
                <fmt:message key="deal.organisation"/>:
                <b>
                    {{value.organisation.value}}
                </b>
            </span>
            <span>
                <fmt:message key="deal.product"/>:
                <b>
                    {{value.product.name}},
                </b>
                <fmt:message key="deal.from"/>:
                <b>
                    {{value.realisation}}
                </b>
            </span>
            <span>

            </span>
        </div>
        <div class="lower-row">
            <span>
                <fmt:message key="transportation.automobile"/>:
                <span v-if="value.transportation.vehicle.id">
                    {{value.transportation.vehicle.model}}
                    '{{value.transportation.vehicle.number}}'
                    <template v-if="value.transportation.vehicle.trailer">
                        '{{value.transportation.vehicle.trailer}}'
                    </template>
                </span>
                <span v-else>
                    <fmt:message key="no.data"/>
                </span>
            </span>
            <span>
                <fmt:message key="transportation.driver"/>:
                <span v-if="value.transportation.driver.id">
                    {{value.transportation.driver.person.value}}
                </span>
                <span v-else>
                    <fmt:message key="no.data"/>
                </span>
            </span>
            <div style="display: inline-block; font-size: 10pt">
                <div v-for="sun in value.transportation.analyses.sun">
                    <fmt:message key="sun.humidity"/>:{{sun.humidity}},
                    <fmt:message key="sun.soreness"/>:{{sun.soreness}},
                    <fmt:message key="sun.oiliness"/>:{{sun.oiliness}}
                </div>
                <div v-for="oil in value.transportation.analyses.oil">
                    <fmt:message key="sun.acid.value"/>:{{oil.acid}},
                    <fmt:message key="oil.peroxide"/>:{{oil.peroxide}},
                    <fmt:message key="oil.phosphorus"/>:{{oil.phosphorus}}
                </div>
                <div v-for="cake in value.transportation.analyses.cake">
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