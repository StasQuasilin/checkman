<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<script>
    list.api.edit = '${edit}';
    list.api.archive = '${archive}';
    <c:if test="${not empty limit}">
    list.limit = ${limit};
    </c:if>
    <c:forEach items="${types}" var="t">
    list.types['${t}'] = '<fmt:message key="_${t}"/> ';
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
<div id="container-header" class="container-header">
    <c:if test="${not empty add}">
        <button onclick="loadModal('${add}')"><fmt:message key="button.add"/> </button>
    </c:if>
</div>
    <div id="container">
        <div v-if="items.length == 0" style="color: darkgray; text-align: center; width: 100%">
            <fmt:message key="empty.list"/>
        </div>
        <transition-group name="flip-list" tag="div" class="container" >
            <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
                 class="container-item" :class="'container-item-' + new Date(value.item.date).getDay()"
                 v-on:click="edit(value.item.id)"
                 v-on:click.right="contextMenu(value.item)">
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
                    <b v-if="types[value.item.type]">
                        {{(types[value.item.type]).toLowerCase()}}
                    </b>
                    <b>
                        {{value.item.product.name}}
                    </b>
                </span>
                <span>
                    <fmt:message key="deal.quantity"/>:
                    <b>
                        {{(value.item.plan).toLocaleString()}}
                        {{value.item.unit}}
                    </b>
                </span>
                <span>
                    <fmt:message key="deal.from"/>:
                    <b>
                        {{value.item.shipper}}
                    </b>
                </span>
                </div>
                <div class="middle-row">
                    <div style="display: inline-block; font-size: 10pt; width: 12em">
                        <div>
                            <fmt:message key="transportation.time.in"/>:
                        <span v-if="value.item.timeIn.time">
                            {{new Date(value.item.timeIn.time).toLocaleTimeString().substring(0, 5)}}
                        </span>
                        <span v-else>
                            --:--
                        </span>
                        </div>
                        <div>
                            <fmt:message key="transportation.time.out"/>:
                      <span v-if="value.item.timeOut.time">
                        {{new Date(value.item.timeOut.time).toLocaleTimeString().substring(0, 5)}}
                      </span>
                        <span v-else>
                            --:--
                        </span>
                        </div>
                    </div>
                    <div style="display: inline-block; font-size: 10pt; width: 28em">
                        <div>
                            <fmt:message key="transportation.automobile"/>:
                        <span>
                            <template v-if="value.item.vehicle.id">
                                {{value.item.vehicle.model}}
                            <span class="vehicle-number">
                            {{value.item.vehicle.number}}
                            </span>
                            <span v-if="value.item.vehicle.trailer" class="vehicle-number">
                            {{value.item.vehicle.trailer}}
                            </span>
                            </template>
                            <span v-else>
                                <fmt:message key="no.data"/>
                            </span>
                        </span>
                        </div>
                        <div>
                            <fmt:message key="transportation.driver"/>:
                            <template v-if="value.item.driver.id">
                                {{value.item.driver.person.value}}
                            </template>
                            <template v-else>
                                <fmt:message key="no.data"/>
                            </template>
                        </div>
                    </div>
                    <div style="display: inline-block; font-size: 10pt">
                        <div v-if="value.item.weight.id">
                            <fmt:message key="weight.brutto"/>:{{value.item.weight.brutto}},
                            <fmt:message key="weight.tara"/>:{{value.item.weight.tara}},
                            <fmt:message key="weight.netto"/>:{{value.item.weight.brutto > 0 &&
                            value.item.weight.tara > 0 ?
                            (value.item.weight.brutto -
                            value.item.weight.tara).toLocaleString() : 0}}
                            <span v-if="value.item.weight.correction">
                                ({{(value.item.weight.netto).toLocaleString()}},
                                -{{(value.item.weight.correction).toLocaleString()}}%)
                            </span>
                        </div>
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
                <div class="lower-row" v-if="value.item.notes.length > 0">
                    <div v-for="note in value.item.notes" style="display: inline-block; padding-left: 4pt">
                        {{note.creator.person.value}}:
                        <b>
                            {{note.note}}
                        </b>
                    </div>
                </div>
            </div>
        </transition-group>

        <c:if test="${(haveMenu eq null) || (haveMenu)}">
            <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
                <div ref="contextMenu" :style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
                    <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${add}')"><fmt:message key="menu.edit"/> </div>
                    <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${add}')"><fmt:message key="menu.copy"/></div>
                    <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${cancel}')"><fmt:message key="menu.cancel"/></div>
                    <div class="custom-data-list-item" v-if="menu.item.done && !menu.item.archive"
                         v-on:click="archive(menu.item.id)"><fmt:message key="menu.archive"/></div>
                </div>
            </div>
        </c:if>
    </div>
</html>