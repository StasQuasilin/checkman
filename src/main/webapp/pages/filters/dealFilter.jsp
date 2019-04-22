<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dealFilter.js"></script>
<link rel="stylesheet" href="${context}/css/filter.css">
<div id="filter">
    <table>
        <tr>
            <th>
                <fmt:message key="filter"/>
            </th>
            <td>
                <a v-on:click="clear"><fmt:message key="button.clear"/> </a>
            </td>
        </tr>
        <tr>
            <td>
                <label for="date">
                    <fmt:message key="date"/>
                </label>
            </td>
            <td>
                <select id="date" v-model="filter.date">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="date in dates()" :value="date">{{new Date(date).toLocaleDateString()}}</option>
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
                <select id="organisation" v-model="filter.organisation">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="organisation in organisations()"
                            :value="organisation.id">{{organisation.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <select id="product" v-model="filter.product">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="product in products()" :value="product.id">
                        {{product.name}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label for="manager">
                    <fmt:message key="deal.manager"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <select id="manager" v-model="filter.creator">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="creator in creators()" :value="creator.id">
                        {{creator.person.value}}
                    </option>
                </select>
            </td>
        </tr>
    </table>
</div>

</html>