<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    list.fields.license = '<fmt:message key="driver.license"/>';
    list.fields.customer = '<fmt:message key="transport.customer"/>';
    list.fields.transporter = '<fmt:message key="transportation.transporter"/>';
    list.fields.noData='<fmt:message key="no.data"/>';
    list.commentFields = {
        header:'<fmt:message key="note.add"/>',
        save:'${saveNote}'
    };
    list.editComment = function(comment){
        console.log(commentator.editTarget);
        if (typeof commentator.editTarget !== 'undefined'){
            commentator.editTarget(comment);
        }
    };
    list.priceFields = {
        title:'<fmt:message key="deal.price"/>'
    };
    list.analysesFields = {
        sun:{
            humidityA:'<fmt:message key="sun.humidity.1.short"/>',
            humidityB:'<fmt:message key="sun.humidity.2.short"/>',
            soreness:'<fmt:message key="sun.soreness"/>',
            impurity:'<fmt:message key="sun.oil.impurity"/>',
            oiliness:'<fmt:message key="sun.oiliness"/>'
        },
        oil:{
            acid:'<fmt:message key="sun.acid.value"/>',
            peroxide:'<fmt:message key="oil.peroxide"/>',
            phosphorus:'<fmt:message key="oil.phosphorus.mass.fraction"/>'
        },
        meal:{
            humidity:'<fmt:message key="sun.humidity"/>',
            protein:'<fmt:message key="cake.protein"/>',
            cellulose:'<fmt:message key="cake.cellulose"/>',
            oiliness:'<fmt:message key="sun.oiliness"/>'
        }
    };
    <c:if test="${not empty limit}">
    list.limit = ${limit};
    </c:if>
    <%--<c:forEach items="${types}" var="t">--%>
    <%--list.types['${t}'] = '<fmt:message key="_${t}"/> ';--%>
    <%--</c:forEach>--%>
    list.types['buy']='Розв.';
    list.types['sell']='Зав.';
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
                let ai = false;
                if (a.item.weight) {
                    if (a.item.weight.brutto > 0 && a.item.weight.tara > 0) {
                        aN = a.item.weight.brutto - a.item.weight.tara;
                    }
                    ai = !aN && (a.item.weight.tara > 0 || a.item.weight.brutto > 0);
                }

                let bN = 0;
                let bi = false;
                if (b.item.weight) {
                    if (b.item.weight.brutto > 0 && b.item.weight.tara > 0) {
                        bN = b.item.weight.brutto - b.item.weight.tara;
                    }
                    bi = !bN && (b.item.weight.tara > 0 || b.item.weight.brutto > 0);
                }

                if (aN === 0 && bN > 0 || ai && !bi) { //(aN == 0 && bN > 0) ||
                    return -1;
                }
                if (aN > 0 && bN === 0 || !ai && bi) { //
                    return 1;
                }
            }

            return new Date(a.item.date) - new Date(b.item.date);
        })
    };
    s = 0;
    <c:forEach items="${subscribe}" var="s">
    s++;
    subscribe('${s}', function(a){
        list.loading = false;
        list.handler(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        unSubscribe('${s}');
        </c:forEach>
    };
    if (s > 2) {
        list.loading = true;
    }
</script>
<style>
    .label{
        font-size: 8pt;
    }
</style>
<c:set var="plan"><fmt:message key="load.plan"/></c:set>
    <div id="container">
        <div v-if="loading" style="color: darkgray; text-align: center; width: 100%">
            Loading...
        </div>
        <transition-group name="flip-list" tag="div" class="container" >
            <div v-for="(value, key) in getItems()"
                 :key="value.item.id" :id="value.item.id"
                 class="container-item"
                 :class="['container-item-' + new Date(value.item.date).getDay(),
                 { done : value.item.weight && (value.item.weight.brutto > 0 && value.item.weight.tara > 0) },
                 { loading: value.item.weight && (value.item.weight.tara > 0 && value.item.weight.brutto == 0 ||
                 value.item.weight.brutto > 0 && value.item.weight.tara == 0)}]"
                 v-on:click="edit(value.item.id)"
                 v-on:click.right="contextMenu(value.item)"
                    >
                        <div style="display: inline-block; max-width: 98%; width: 94%">
                            <div class="upper-row" style="font-size: 11pt">
                                <div style="display: inline-block">
                                    <div style="font-size: 6pt; color: gray" :title="'ID: ' + value.item.id">
                                        {{value.item.id}}
                                    </div>
                                    <div :title="new Date(value.item.date).toLocaleDateString()">
                                        {{new Date(value.item.date).toLocaleDateString().substring(0, 5)}}
                                    </div>
                                </div>
                                <div style="display: inline-block; width: 92%">
                                    <div>
                                        <span class="label">
                                            <fmt:message key="deal.organisation"/>:
                                        </span>
                                        <span v-if="value.item.counterparty.code" class="counterparty-code code-valid">
                                            &check;
                                            <span class="code-text">
                                                <template v-if="value.item.counterparty.code.length == 8">
                                                    <fmt:message key="counterparty.code.1"/>
                                                </template>
                                                <template v-else>
                                                    <fmt:message key="counterparty.code.2"/>
                                                </template>
                                                 {{value.item.counterparty.code}}
                                            </span>
                                        </span>
                                        <b>
                                            {{value.item.counterparty.value}}
                                        </b>
                                        <span v-if="value.item.contractNumber">
                                            <span class="label">
                                                <fmt:message key="deal.num"/>:
                                            </span>
                                            <b>
                                                {{value.item.contractNumber}}
                                            </b>
                                        </span>
                                        <span class="product-line" style="float: right;">
                                            <span v-if="types[value.item.type]" class="label">
                                                {{(types[value.item.type]).toLowerCase()}}
                                            </span>
                                            <b>
                                                {{value.item.product.name}}
                                                <span v-if="value.item.plan" >
                                                    {{value.item.plan}} {{value.item.unit}}
                                                </span>
                                            </b>
                                        <price-view :props="priceFields" :item="value.item"></price-view>
                                        <span class="label">
                                            <fmt:message key="deal.from"/>
                                        </span>
                                            {{value.item.shipper}}
                                        </span>
                                    </div>
                                    <b v-if="value.item.address" style="font-size: 9pt; display: inline-flex" >
                                        <fmt:message key="load.address"/>:
                                        <template v-if="value.item.address.index">
                                            {{value.item.address.index}},
                                        </template>
                                        <template v-if="value.item.address.region">
                                            {{value.item.address.region}}
                                            <fmt:message key="address.region.short"/>,
                                        </template>
                                        <template v-if="value.item.address.district">
                                            {{value.item.address.district}}
                                            <fmt:message key="address.district.short"/>,
                                        </template>
                                        <template v-if="value.item.address.city">
                                            {{value.item.address.city}}
                                        </template>
                                        <template v-if="value.item.address.street">
                                            , {{value.item.address.street}},
                                        </template>

                                        {{value.item.address.build}}
                                    </b>
                                </div>
                            </div>
                    <%--</div>--%>
                    <div class="middle-row">
                        <div style="display: inline-block; width: 8em">
                            <div style="font-size: 9pt">
                                <fmt:message key="transportation.time.in"/>:
                                <template v-if="value.item.timeIn">
                                    {{new Date(value.item.timeIn.time).toLocaleDateString().substring(0, 5)}}
                                    {{new Date(value.item.timeIn.time).toLocaleTimeString().substring(0, 5)}}
                                </template>
                                <template v-else>
                                    --.-- --:--
                                </template>
                            </div>
                            <div style="font-size: 9pt">
                                <fmt:message key="transportation.time.out"/>:
                                  <template v-if="value.item.timeOut">
                                    {{new Date(value.item.timeOut.time).toLocaleDateString().substring(0, 5)}}
                                    {{new Date(value.item.timeOut.time).toLocaleTimeString().substring(0, 5)}}
                                  </template>
                                    <template v-else>
                                        --.-- --:--
                                    </template>
                            </div>
                        </div>
                        <transport-view :item="value.item" :fields="fields" :customers="customers"></transport-view>
                    </div>
                    <div style="font-size: 8pt; padding: 0 4px">
                        &nbsp;
                        <span v-if="value.item.driver && value.item.driver.person.phones.length > 0">
                            <img style="width: 10pt" src="${context}/images/phone.svg">
                            <span v-for="phone in value.item.driver.person.phones" style="padding: 0 2pt">
                                {{phone.number}}
                            </span>
                        </span>
                        <span style="float: right;">
                            <fmt:message key="deal.manager"/>:
                            {{value.item.manager.person.value}},
                            <fmt:message key="created"/>:
                            <template v-if="value.item.manager.id != value.item.create.creator.id">
                                {{value.item.create.creator.person.value}}
                            </template>
                            {{new Date(value.item.create.time).toLocaleDateString().substring(0, 5)}}
                            {{new Date(value.item.create.time).toLocaleTimeString().substring(0, 5)}}
                        </span>
                    </div>
                    <div class="lower-row" v-if="value.item.notes.length > 0">
                        <a v-for="note in value.item.notes" v-on:click="editComment(note)" style="display: inline-block; padding-left: 4pt">
                        <span v-if="note.creator">
                            {{note.creator}}:
                        </span>
                            <b>
                                {{note.note}}
                            </b>
                        </a>
                    </div>
                </div>
                <div class="right-field">
                    <div class="right-field-content">
                        <div v-if="value.item.weight" style="width: 54pt; padding-left: 4pt">
                            <div>
                                <div>
                                    Б: <b>{{value.item.weight.brutto}}</b>
                                </div>
                                <div>
                                    Т: <b>{{value.item.weight.tara}}</b>
                                </div>
                                <div>
                                    Н: <b>{{(value.item.weight.netto).toLocaleString()}}</b>
                                </div>
                            </div>
                            <template v-if="value.item.weight.correction > 0">
                                <div v-if="value.item.weight.netto > 0">
                                    ({{(value.item.weight.netto * (1 - value.item.weight.correction / 100)).toLocaleString()}})
                                </div>
                                <div>
                                    -{{value.item.weight.correction.toLocaleString()}}%
                                </div>
                            </template>
                        </div>
                    </div>
                    <laboratory-view :item="value.item" :fields="analysesFields"></laboratory-view>
                </div>
            </div>
        </transition-group>
        <c:if test="${(menu eq null) || (menu) || not empty add || not empty copy || not empty cancel}">
            <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
                <div ref="contextMenu" :style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
                    <c:if test="${not empty add}">
                        <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${add}')"><fmt:message key="menu.edit"/> </div>
                        <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${add}')"><fmt:message key="menu.copy"/></div>
                    </c:if>
                    <c:if test="${not empty copy}">
                        <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${copy}')"><fmt:message key="menu.copy"/></div>
                    </c:if>
                    <c:if test="${not empty cancel}">
                        <div class="custom-data-list-item" v-if="menu.item.any" style="color: #bababa">
                            <div>
                                <fmt:message key="menu.archive"/>
                            </div>
                            <div style="font-size: 8pt">
                                <fmt:message key="not.supported.already"/>
                            </div>
                        </div>
                        <div class="custom-data-list-item" :id="menu.id" v-else
                             onclick="editableModal('${cancel}')"><fmt:message key="menu.delete"/></div>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
</html>