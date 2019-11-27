<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>


<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">
<script>
    list.api.edit = '${edit}';
    list.api.archive = '${archive}';
    if (typeof list.fields === 'undefined'){
        list.fields = {};
    }
    list.fields.vehicle = '<fmt:message key="transportation.automobile"/>';
    list.fields.driver = '<fmt:message key="transportation.driver"/>';
    list.fields.customer = '<fmt:message key="transport.customer"/>';
    list.fields.transporter = '<fmt:message key="transportation.transporter"/>';
    list.fields.noData='<fmt:message key="no.data"/>';
    list.analysesFields = {
        sun:{
            humidityA:'<fmt:message key="sun.humidity.1"/>',
            humidityB:'<fmt:message key="sun.humidity.2"/>',
            soreness:'<fmt:message key="sun.soreness"/>',
            impurity:'<fmt:message key="sun.oil.impurity"/>',
            oiliness:'<fmt:message key="sun.oiliness"/>'
        }
    };
    <c:if test="${not empty limit}">
    list.limit = ${limit};
    </c:if>
    <c:forEach items="${types}" var="t">
    list.types['${t}'] = '<fmt:message key="_${t}"/> ';
    </c:forEach>
    list.customers=[];
    <c:forEach items="${customers}" var="customer">
    list.customers['${customer}'] = '<fmt:message key="${customer}"/>';
    </c:forEach>
    list.styler = function(item){
        return item.weight.tara > 0
    };
    list.sort = function(){
        list.items.sort(function(a, b){
            if (a.item.date === b.item.date) {
                let aN = 0;
                if (a.item.weight.brutto > 0 && a.item.weight.tara > 0) {
                    aN = a.item.weight.brutto - a.item.weight.tara;
                }

                let bN = 0;
                if (b.item.weight.brutto > 0 && b.item.weight.tara > 0) {
                    bN = b.item.weight.brutto - b.item.weight.tara;
                }
                let ai = !aN && (a.item.weight.tara > 0 || a.item.weight.brutto > 0);
                let bi = !bN && (b.item.weight.tara > 0 || b.item.weight.brutto > 0);
                if (aN == 0 && bN > 0 || ai && !bi) { //(aN == 0 && bN > 0) ||
                    return -1;
                }
                if (aN > 0 && bN == 0 || !ai && bi) { //
                    return 1;
                }
            }

            return new Date(a.item.date) - new Date(b.item.date);
        })
    };
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
<c:set var="plan"><fmt:message key="load.plan"/></c:set>
    <div id="container">

        <div v-if="items.length == 0" style="color: darkgray; text-align: center; width: 100%">
            <fmt:message key="empty.list"/>
        </div>
        <transition-group name="flip-list" tag="div" class="container" >
            <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
                 class="container-item"
                 :class="['container-item-' + new Date(value.item.date).getDay(),
                 { done : value.item.weight.brutto > 0 && value.item.weight.tara > 0 },
                 { loading: value.item.weight.tara > 0 && value.item.weight.brutto == 0 ||
                 value.item.weight.brutto > 0 && value.item.weight.tara == 0}]"

                 v-on:click="edit(value.item.id)"
                 v-on:click.right="contextMenu(value.item)">
                    <div style="display: inline-block; max-width: 98%; width: 94%">
                        <div class="upper-row" style="font-size: 11pt">
                        <span>
                            {{new Date(value.item.date).toLocaleDateString()}}
                        </span>
                        <span style="width: 20em">
                            <fmt:message key="deal.organisation"/>:
                            <b>
                                {{value.item.organisation.value}}
                            </b>
                        </span>
                        <span style="float: right;">
                            <b v-if="types[value.item.type]">
                                {{(types[value.item.type]).toLowerCase()}}
                            </b>
                            <b>
                                {{value.item.product.name}}
                                <span v-if="value.item.plan">
                                    {{value.item.plan}} {{value.item.unit}}
                                </span>

                            </b>
                        </span>
                    </div>
                    <div class="middle-row">
                        <div style="display: inline-block; font-size: 10pt; width: 8em">
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
                        <transport-view :item="value.item" :fields="fields" :customers="customers"></transport-view>
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
                <div class="right-field">
                    <div v-if="value.item.weight.id" class="right-field-content" style="width: 84pt">
                        <div>
                            Б: {{value.item.weight.brutto}}
                        </div>
                        <div>
                            Т: {{value.item.weight.tara}}
                        </div>
                        <div>
                            Н: {{(value.item.weight.netto).toLocaleString()}}
                        </div>
                        <div v-if="value.item.weight.correction > 0">
                            ({{(value.item.weight.netto * (1 - value.item.weight.correction / 100)).toLocaleString()}})
                        </div>
                    </div>
                    <laboratory-view :item="value.item" :fields="analysesFields"></laboratory-view>
                </div>
            </div>
        </transition-group>

        <c:if test="${(menu eq null) || (menu)}">
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
        <c:if test="${not empty copy}">
            <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
                <div ref="contextMenu" :style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
                    <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${copy}')"><fmt:message key="menu.copy"/></div>
                </div>
            </div>
        </c:if>
    </div>
</html>