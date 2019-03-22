<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dataList.js"></script>
<script src="${context}/vue/weightCalculator.js"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<script>
    deamon.setUrls('${updateLink}', '${showLink}')
</script>
<div class="container" id="container" >
    <div v-for="(value, key) in items" v-bind:key="key" v-bind:id="value.item.id"
     class="container-item" v-bind:class="value.className" v-on:click="show(value.item.id)">
        <div class="upper-row">
            <span>
                {{new Date(value.item.date).toLocaleDateString()}}
            </span>
            <span>
                <fmt:message key="deal.organisation"/>:
                <b>
                    {{value.item.organisation.value}}
                </b>
            </span>
            <span>
                <fmt:message key="deal.product"/>:
                <b>
                    {{value.item.product.name}}
                </b>
            </span>
            <span>
                <fmt:message key="deal.quantity"/>:
                <b>
                    {{(value.item.quantity).toLocaleString()}}
                </b>
            </span>
            <span>
                <fmt:message key="deal.from"/>:
                <b>
                    {{value.item.realisation}}
                </b>
            </span>
        </div>
        <div class="lower-row">
            <fmt:message key="transportation.automobile"/>:
            <div style="display: inline-block; width: 28%; border-bottom: dotted 1pt">
                <template v-if="value.item.transportation.vehicle.id">
                    {{value.item.transportation.vehicle.model}}
              <span class="vehicle-number">
                {{value.item.transportation.vehicle.number}}
              </span>
              <span v-if="value.item.transportation.vehicle.trailer" class="vehicle-number">
                {{value.item.transportation.vehicle.trailer}}
              </span>
                </template>
                <template v-else="value.item.transportation.vehicle.id">
                    <fmt:message key="no.data"/>
                </template>
            </div>
            <fmt:message key="transportation.driver"/>:
            <div style="display: inline-block; width: 20%; text-decoration: underline">
                <template v-if="value.item.transportation.driver.id">
                    {{value.item.transportation.driver.person.value}}
                </template>
                <template v-else>
                    <fmt:message key="no.data"/>
                </template>
            </div>
            <%--{{value.item.transportation.weights}}--%>

            <span>
                <fmt:message key="weight.brutto"/>:
            </span>
            <span>
                <fmt:message key="weight.tara"/>:
            </span>
            <span>
                <fmt:message key="weight.netto"/>:
            </span>
        </div>
    </div>
</div>
</html>