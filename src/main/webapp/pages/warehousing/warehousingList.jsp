<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<script>
    list.api.edit = '${edit}';
    <c:if test="${not empty limit}">
    list.limit = ${limit};
    </c:if>
    <c:forEach items="${types}" var="t">
    list.types['${t}'] = '<fmt:message key="_${t}"/> ';
    </c:forEach>
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a, sub){
        list.handler(a);
    });
    </c:forEach>
</script>
    <jsp:include page="../summary/summaryHeader.jsp"/>
    <jsp:include page="warehousingStatic.jsp"/>
<c:set var="plan"><fmt:message key="load.plan"/></c:set>
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
                    <span style="font-size: 10pt">
                        <fmt:message key="deal.organisation"/>:
                    </span>
                    <b>
                        {{value.item.organisation.value}}
                    </b>
                </span>
                <span>
                    <span style="font-size: 10pt">
                        <fmt:message key="deal.product"/>:
                        <template v-if="types[value.item.type]">
                            {{(types[value.item.type]).toLowerCase()}}
                        </template>
                    </span>

                    <b>
                        {{value.item.product.name}}
                    </b>
                </span>
                <span>
                    <span style="font-size: 10pt;">
                        ${fn:substring(plan, 0, 4)}:
                    </span>
                    <b>
                        {{(value.item.plan).toLocaleString()}}
                    </b>
                </span>
                <span style="float: right">
                    {{value.item.shipper}}
                </span>
                </div>
                <div class="middle-row">
                    <div style="display: inline-block; font-size: 10pt; width: 10em">
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
                    <div v-if="value.item.weight.id" style="display: inline-block; font-size: 10pt">
                        <div>
                            Б:{{value.item.weight.brutto}},
                            Т:{{value.item.weight.tara}}
                        </div>
                        <div>
                            <span>
                                <fmt:message key="weight.net"/>:{{value.item.weight.netto.toLocaleString()}}
                            </span>
                            <span v-if="value.item.weight.correction">
                                ({{(value.item.weight.netto * (1 - value.item.weight.correction / 100)).toLocaleString()}})
                            </span>
                        </div>
                    </div>
                    <div style="display: inline-block; font-size: 10pt">
                        <div v-for="storage in value.item.storage" >
                            {{storage.storage}}: {{storage.amount}}
                        </div>
                    </div>
                </div>
                <div class="lower-row" v-if="value.item.notes.length > 0">
                    <div v-for="note in value.item.notes" style="display: inline-block; padding-left: 4pt">
                        <span v-if="note.creator.person">
                            {{note.creator.person.value}}:
                        </span>
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
                    <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${cancel}')"><fmt:message key="menu.delete"/></div>
                    <div class="custom-data-list-item" v-if="menu.item.done && !menu.item.archive"
                         v-on:click="archive(menu.item.id)"><fmt:message key="menu.archive"/></div>
                </div>
            </div>
        </c:if>
    </div>
</html>