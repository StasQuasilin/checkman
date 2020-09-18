<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 10.09.20
  Time: 14:56
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mft" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/dealShow.css">
<script src="${context}/vue/editor.vue"></script>
<script src="${context}/vue/dealShow.vue"></script>
<script>
    dealShow.api.dealDetails = '${dealDetails}';
    dealShow.api.saveTransportation = '${saveTransportation}';
    <c:forEach items="${customers}" var="customer">
    dealShow.customers.push('${customer}');
    dealShow.customerNames['${customer}'] = '<fmt:message key="customer.${customer}"/>';
    </c:forEach>
    dealShow.deal = ${deal}
    <c:forEach items="${deals}" var="deal">
    dealShow.deals.push(${deal.toJson()});
    </c:forEach>
    dealShow.checkDetails();

</script>
<div id="dealShow" class="deal-show">
    <div>
        <div>
            <select v-model="deal" v-on:change="checkDetails()">
                <option v-for="deal in deals" :value="deal.id">
                    {{new Date(deal.date).toLocaleDateString()}}
                    <template v-if="deal.number">
                        {{deal.number}}
                    </template>
                    {{deal.documents[0].counterparty.name}}
                </option>
            </select>
        </div>
        <div v-if="dealMap">
            <table>
                <tr>
                    <td>
                        <fmt:message key="deal.date"/>
                    </td>
                    <td>
                        {{new Date(dealMap[deal].date).toLocaleDateString()}}
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="deal.period"/>
                    </td>
                    <td>
                        <fmt:message key="deal.from"/>
                        {{new Date(dealMap[deal].from).toLocaleDateString()}}
                        <fmt:message key="deal.to"/>
                        {{new Date(dealMap[deal].to).toLocaleDateString()}}
                    </td>
                </tr>
                <template v-for="doc in dealMap[deal].documents">
                    <tr>
                       <td>
                           <fmt:message key="deal.counterparty"/>
                       </td>
                        <td>
                            {{doc.counterparty.name}}
                        </td>
                    </tr>
                    <template v-for="dp in doc.products">
                        <tr>
                            <td>
                                <fmt:message key="deal.product"/>
                            </td>
                            <td>
                                {{dp.product.name}} {{dp.amount}} {{dp.unit}}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fmt:message key="deal.price"/>
                            </td>
                            <td>
                                {{dp.price.toLocaleString()}}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fmt:message key="deal.shipper"/>
                            </td>
                            <td>
                                {{dp.shipper}}
                            </td>
                        </tr>
                    </template>
                </template>
            </table>
        </div>
    </div>
    <div>
        <div>
            <template v-for="t in tabs">
                <b v-if="t===tab">
                    {{t}}
                </b>
                <span v-else v-on:click="tab = t">
                    {{t}}
                </span>
            </template>
        </div>
        <div v-if="tab==='transportations'" style="display: flex; flex-direction: row">
            <div>
                <template v-for="date in transportationDates" style="font-size: 10pt">
                    <b v-if="transportationDate === date" v-on:click="transportationDate = ''">
                        - {{new Date(date).toLocaleDateString()}}: {{transportationsByDate[date].count}} / {{transportationsByDate[date].amount}}
                    </b>
                    <div v-else v-on:click="transportationDate = date">
                        &nbsp; {{new Date(date).toLocaleDateString()}}: {{transportationsByDate[date].count}} / {{transportationsByDate[date].amount}}
                    </div>
                </template>
            </div>
            <div>
                <div>
                    <button v-on:click="newTransportation()">
                        + <fmt:message key="deal.new.transportation"/>
                    </button>
                </div>
                <div class="transportation-list">
                    <div v-for="transportation in getTransportations()" class="transportation-list-item">
                        <div>
                            <div class="text-button" style="font-size: 10pt">
                                &times;
                            </div>
                            <div v-if="transportation.save === 1" style="color: green">
                                &check;
                            </div>
                            <div v-else-if="transportation.save === 2" style="color: orangered">
                                &times;
                            </div>
                        </div>
                        <div class="transportation-body">
                            <div>
                                <span>
                                    {{new Date(transportation.date).toLocaleDateString()}}
                                </span>
                                <input type="number" v-model="transportation.documents[0].products[0].amount" autocomplete="off" onfocus="this.select()">
                                {{dpMap[dealProduct].unit}}
                                <select v-model="transportation.customer">
                                    <option v-for="customer in customers" :value="customer">
                                        {{customerNames[customer]}}
                                    </option>
                                </select>
                            </div>
                            <div style="width: 100%">
                                <div style="width: 100%; display: flex; flex-direction: row">
                                <span style="flex: 1 0 auto">
                                    <fmt:message key="transportation.driver"/>
                                </span>
                                    <span style="flex: 1 0 auto">
                                    <fmt:message key="transportation.vehicle"/>/<fmt:message key="transportation.trailer"/>
                                </span>
                                </div>
                                <div>
                                    <fmt:message key="transportation.transporter"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div v-else-if="tab==='carriages'">
            <div>
                <button v-on:click="newCarriage()">
                    + <fmt:message key="deal.new.carriage"/>
                </button>
            </div>
            <div>
                <div v-for="carriage in carriages">
                    {{carriage}}
                </div>
            </div>
        </div>
        <div v-else>
            Some unknown tab
        </div>
    </div>
</div>
</html>