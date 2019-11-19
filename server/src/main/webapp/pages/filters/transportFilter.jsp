<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/filter.css">
<script src="${context}/vue/filters/transportFilter.vue"></script>
<script>
    filter_control.filters.types.push({
        id:'buy',
        value:'<fmt:message key="buy"/>'
    });
    filter_control.filters.types.push({
        id:'sell',
        value:'<fmt:message key="sell"/>'
    });
</script>
<div id="filter_view">
    <div style="text-align: center; font-size: 10pt">
        <a v-on:click="clear"><fmt:message key="button.clear"/> </a>
    </div>
    <table >
        <tr v-if="filters.types.length">
            <td>
                <label for="type">
                    <fmt:message key="deal.type"/>
                </label>
            </td>
            <td>
                <select id="type" v-model="type" style="width: 100%">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="(value, key) in filters.types" :value="value.id">{{value.value}}</option>
                </select>
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
                    <option value="-1"><fmt:message key="all"/> </option>
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
                <div>
                    <input type="radio" v-on:change="putProduct()" name="product_radio" id="product_" value="-1" v-model="product">
                    <label for="product_">
                        <fmt:message key="all"/>
                    </label>
                </div>
                <div v-for="p in products()" >
                    <input type="radio" v-on:change="putProduct()" name="product_radio" :id="'product_'+p.id" :value="p.id" v-model="product">
                    <label :for="'product_' + p.id">
                        {{p.name}}
                    </label>
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
                    <option v-for="date in dates()" :value="date">{{new Date(date).toLocaleDateString()}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input id="on" type="checkbox" v-model="on">
                <label for="on">
                    <fmt:message key="on.territory"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label for="driver">
                    <fmt:message key="transportation.driver"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <select id="driver" v-model="driver" style="width: 100%">
                    <option value="-1"><fmt:message key="all"/></option>
                    <option value="0"><fmt:message key="no.data"/> </option>
                    <option v-for="driver in drivers()" :value="driver.id">{{driver.person.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="font-size: 10pt">
                <fmt:message key="items.count"/>: {{filteredItems().length}}
            </td>
        </tr>
    </table>
</div>

</html>