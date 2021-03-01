<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/filter.css?v=${now}">
<script src="${context}/vue/filters/transportFilter.vue?v=${now}"></script>
    <div id="filter_view" style="width: 100%">
        <table style="height: 100%; width: 100%">
            <tr>
                <td colspan="2" style="text-align: center;">
                    <a v-on:click="clear"><fmt:message key="filter.clear"/> </a>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label for="organisation">
                        <fmt:message key="deal.organisation"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <select id="organisation" v-model="organisation" style="width: 100%">
                        <option value="-1"><fmt:message key="all"/></option>
                        <option v-for="o in organisations()" :value="o.id">
                            {{o.value}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <th colspan="2">
                    <fmt:message key="deal.product"/>
                </th>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="mini-close" v-on:click="putProduct(-1)" :class="{bold : product === -1}">
                        <fmt:message key="all"/>: {{filtered(null, organisation, date, driver).length}}
                    </div>
                    <div v-for="p in products()" v-on:click="putProduct(p.id)" class="mini-close" style="padding-top: 4pt; font-size: 11pt" :class="{bold : product === p.id}">
                        {{p.name}}:&nbsp;{{p.amount}}
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="date">
                        <fmt:message key="date"/>
                    </label>
                </td>
                <td>
                    <select id="date" v-model="date" style="width: 100%">
                        <option value="-1"><fmt:message key="all"/> </option>
                        <option v-for="(amount, date) in dates()" :value="date">
                            {{new Date(date).toLocaleDateString()}}: {{amount}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div style="display: flex; flex-direction: row">
                        <div style="">
                            <input id="onTerritory" type="checkbox" v-model="onTerritory">
                            <label for="onTerritory">
                                <span class="mini-close">
                                    <fmt:message key="on.territory"/>
                                </span>
                            </label><br>
                            <input id="onVoyage" type="checkbox" v-model="onVoyage">
                            <label for="onVoyage">
                                <span class="mini-close">
                                    <fmt:message key="on.voyage"/>
                                </span>
                            </label><br>
                            <input id="onWait" type="checkbox" v-model="onWait">
                            <label for="onWait">
                                <span class="mini-close">
                                    <fmt:message key="on.wait"/>
                                </span>
                            </label><br>
                            <input id="onDone" type="checkbox" v-model="onDone">
                            <label for="onDone">
                                <span class="mini-close">
                                    <fmt:message key="on.done"/>
                                </span>
                            </label>
                        </div>
                        <div v-if="anyTime" style="border-left: solid gray 1px; padding-top: 25%; padding-left: 4px">
                            <span class="mini-close" v-on:click="clearTimes()">
                                &times;
                            </span>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="driver">
                        <fmt:message key="transportation.driver"/>
                    </label>
                </td>
                <td>
                    <a v-if="driver.length > 0" v-on:click="driver = []" style="color: orangered; font-weight: bold">
                        <fmt:message key="button.clear"/> ( {{driver.length}} )
                    </a>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="height: 100%">
                    <select id="driver" multiple v-model="driver" style="height: 100%; width: 100%; outline: none">
                        <option v-for="driver in drivers()" :value="driver.id">
                            {{driver.person.value}}: ( {{driver.amount}} )
                        </option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
</html>