<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dataList.js"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<script>
    deamon.setUrls('${update}', '${put}')
    <c:forEach items="${types}" var="t">
    deamon.types['${t}'] = '<fmt:message key="_${t}"/> '
    </c:forEach>
</script>
<div id="container-header" class="container-header">
    <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
</div>
    <div id="container">
        <transition-group name="flip-list" tag="div" class="container" >
            <div v-for="(value, key) in filteredItems()" v-bind:key="value.item.id" v-bind:id="value.item.id"
                 class="container-item" v-bind:class="rowName(value.item.date)" v-on:click="show(value.item.id)"
                 v-on:click.right="contextMenu(value.item.id)">
                <div class="upper-row">
                <span>
                    {{new Date(value.item.date).toLocaleDateString()}}
                </span>
                <span style="width: 20em">
                    <fmt:message key="deal.organisation"/>:
                    <b>
                        {{value.item.organisation.value}}
                    </b>
                </span>
                <span>
                    <fmt:message key="deal.product"/>:
                    <b>
                        {{(types[value.item.type]).toLowerCase(),}}
                    </b>
                    <b>
                        {{value.item.product.name}}
                    </b>
                </span>
                <span>
                    <fmt:message key="deal.quantity"/>:
                    <b>
                        {{(value.item.quantity).toLocaleString()}}
                        {{value.item.unit}}
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
                    <div style="display: inline-block; font-size: 10pt; width: 14em">
                        <div>
                            <fmt:message key="transportation.time.in"/>:
                        <span v-if="value.item.transportation.timeIn.time">
                            {{new Date(value.item.transportation.timeIn.time).toLocaleTimeString()}}
                        </span>
                        <span v-else>
                            --:--:--
                        </span>
                        </div>
                        <div>
                            <fmt:message key="transportation.time.out"/>:
                      <span v-if="value.item.transportation.timeOut.time">
                        {{new Date(value.item.transportation.timeOut.time).toLocaleTimeString()}}
                      </span>
                        <span v-else>
                            --:--:--
                        </span>
                        </div>
                    </div>
                    <div style="display: inline-block; font-size: 10pt; width: 28em">
                        <div>
                            <fmt:message key="transportation.automobile"/>:
                        <span>
                            <template v-if="value.item.transportation.vehicle.id">
                                {{value.item.transportation.vehicle.model}}
                            <span class="vehicle-number">
                            {{value.item.transportation.vehicle.number}}
                            </span>
                            <span v-if="value.item.transportation.vehicle.trailer" class="vehicle-number">
                            {{value.item.transportation.vehicle.trailer}}
                            </span>
                            </template>
                            <span v-else="value.item.transportation.vehicle.id">
                            <fmt:message key="no.data"/>
                            </span>
                        </span>
                        </div>
                        <div>
                            <fmt:message key="transportation.driver"/>:
                            <template v-if="value.item.transportation.driver.id">
                                {{value.item.transportation.driver.person.value}}
                            </template>
                            <template v-else>
                                <fmt:message key="no.data"/>
                            </template>
                        </div>
                    </div>
                    <div style="display: inline-block; font-size: 10pt">
                        <div v-for="weight in value.item.transportation.weights">
                            <fmt:message key="weight.brutto"/>:{{weight.brutto}},
                            <fmt:message key="weight.tara"/>:{{weight.tara}},
                            <fmt:message key="weight.netto"/>:{{weight.brutto > 0 && weight.tara > 0 ? (weight.brutto - weight.tara).toLocaleString() : 0}}
                        </div>
                        <div v-for="sun in value.item.transportation.analyses.sun">
                            <fmt:message key="sun.humidity"/>:
                            {{(sun.humidity).toLocaleString()}},
                            <fmt:message key="sun.soreness"/>:
                            {{(sun.soreness).toLocaleString()}},
                            <fmt:message key="sun.oiliness"/>:
                            {{(sun.oiliness).toLocaleString()}}
                        </div>
                        <div v-for="oil in value.item.transportation.analyses.oil">
                            <fmt:message key="sun.acid.value"/>:
                            {{(oil.acid).toLocaleString(),}}
                            <fmt:message key="oil.peroxide"/>:
                            {{(oil.peroxide).toLocaleString()}}
                        </div>
                        <div v-for="cake in value.item.transportation.analyses.cake">
                            <fmt:message key="sun.humidity"/>:
                            {{cake.humidity}},
                            <fmt:message key="cake.protein"/>:
                            {{cake.protein}},
                            <fmt:message key="cake.cellulose"/>:
                            {{cake.cellulose}},
                            <fmt:message key="sun.oiliness"/>:
                            {{cake.oiliness}}
                        </div>
                    </div>
                </div>
            </div>
        </transition-group>
        <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
            <div v-bind:style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
                <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.edit"/> </div>
                <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.copy"/></div>
                <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${cancel}')"><fmt:message key="menu.cancel"/></div>
            </div>
        </div>
    </div>
</html>