<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/dataList.js"></script>
<script>
    deamon.setUrls('${updateLink}', '${editLink}')
</script>
<transition-group  name="flip-list" tag="div" class="container" id="container">
    <div v-for="(value, key) in items" v-bind:key="value.item.id" v-bind:id="value.item.id"
         class="container-item" v-bind:class="value.className" v-on:click="show(value.item.id)">
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
            <span>

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
        </div>
    </div>
</transition-group>
</html>