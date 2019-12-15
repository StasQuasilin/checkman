<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/DataContainer.css">
    <link rel="stylesheet" href="${context}/css/TransportList.css">
    <link rel="stylesheet" href="${context}/css/RetailList.css">
    <script>
        list.api.edit = '${edit}';
        if (typeof list.fields === 'undefined'){
            list.fields = {};
        }
        list.fields.counterparty = '<fmt:message key="deal.organisation"/>';
        list.fields.address = '<fmt:message key="address"/>';
        list.fields.product = '<fmt:message key="deal.product"/>';
        list.fields.price = '<fmt:message key="deal.price"/>';
        list.fields.from = '<fmt:message key="deal.from"/>';
        list.fields.vehicle = '<fmt:message key="transportation.automobile"/>';
        list.fields.driver = '<fmt:message key="transportation.driver"/>';
        list.fields.customer = '<fmt:message key="transport.customer"/>';
        list.fields.transporter = '<fmt:message key="transportation.transporter"/>';
        list.fields.noData='<fmt:message key="no.data"/>';
        list.customers=[];
        list.showPrice = true;
        <c:forEach items="${customers}" var="customer">
        list.customers['${customer}'] = '<fmt:message key="${customer}"/>';
        </c:forEach>
        subscribe('${subscribe}', function(a){
            list.handler(a);
        })
    </script>
    <div id="container" class="container">
        <div v-for="(value, idx) in getItems()" class="container-item" v-on:click="edit(value.item.id)"
             :class="'container-item-' + new Date(value.item.date).getDay()">
            <%--OLD VIEW--%>
            <div v-if="value.item.old">
                OLD
            </div>
            <%--NEW VIEW--%>
            <div v-else style="display: inline-block; max-width: 98%; width: 94%">
                <div class="upper-row" style="font-size: 11pt">
                    <div style="display: inline-block">
                        <div style="width: 100%; text-align: center; font-size: 6pt; color: gray">
                            ID:{{value.item.id}}
                        </div>
                        <div>
                            {{new Date(value.item.date).toLocaleDateString()}}
                        </div>
                    </div>
                    <product-view :fields="fields" :show="showPrice" :products="value.item.products"></product-view>
                </div>
                <div class="middle-row">
                    <div style="display: inline-block; font-size: 10pt; width: 8em">
                        <div>
                            <fmt:message key="transportation.time.in"/>:
                                <span v-if="value.item.timeIn && value.item.timeIn.time">
                                    {{new Date(value.item.timeIn.time).toLocaleTimeString().substring(0, 5)}}
                                </span>
                                <span v-else>
                                    --:--
                                </span>
                        </div>
                        <div>
                            <fmt:message key="transportation.time.out"/>:
                                  <span v-if="value.item.timeOut && value.item.timeOut.time">
                                    {{new Date(value.item.timeOut.time).toLocaleTimeString().substring(0, 5)}}
                                  </span>
                                    <span v-else>
                                        --:--
                                    </span>
                        </div>
                    </div>
                    <transport-view :item="value.item" :fields="fields" :customers="customers"></transport-view>
                    <div style="display: inline-block; float: right; font-size: 10pt">
                        <div style="width: 100%; text-align: center">
                            <fmt:message key="deal.manager"/>
                        </div>
                        <div>
                            {{value.item.manager.person.value}}
                        </div>
                        <div v-if="value.item.manager.person.id != value.item.creator.id"
                            style="font-size: 6pt; color: gray">
                            {{value.item.creator}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</html>