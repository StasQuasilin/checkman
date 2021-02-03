<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<link rel="stylesheet" href="${context}/css/filter.css?v=${now}">
<script src="${context}/vue/dealFilter.vue?v=${now}"></script>
<div id="filterView">
    <div class="filter">
        <table width="100%">
            <tr>
                <td align="center" colspan="3">
                    <fmt:message key="filter"/>
                    <a class="mini-close" v-on:click="clear"><fmt:message key="button.clear"/> </a>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="date">
                        <fmt:message key="date"/>
                    </label>
                </td>
                <td>
                    <select id="date" v-model="filter.date" style="width: 100%">
                        <option :value="defaultId"><fmt:message key="all"/> </option>
                        <option v-for="date in dates()" :value="date">{{new Date(date).toLocaleDateString()}}</option>
                    </select>
                </td>
                <td>
                    <span v-if="filter.date !== -1" class="mini-close" v-on:click="clearFilterField('date')">
                        &times;
                    </span>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label for="organisation">
                        <fmt:message key="deal.organisation"/>
                    </label>
                    <span v-if="filter.organisation !== -1" class="mini-close" v-on:click="clearFilterField('organisation')">
                        &times;
                    </span>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <select id="organisation" v-model="filter.organisation" style="width: 100%">
                        <option :value="defaultId"><fmt:message key="all"/></option>
                        <option v-for="organisation in organisations()"
                                :value="organisation.id">{{organisation.value}}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label for="product">
                        <fmt:message key="deal.product"/>
                    </label>
                    <span v-if="filter.product !== -1" class="mini-close" v-on:click="clearFilterField('product')">
                        &times;
                    </span>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <select id="product" v-model="filter.product" style="width: 100%">
                        <option :value="defaultId"><fmt:message key="all"/> </option>
                        <option v-for="product in products()" :value="product.id">
                            {{product.name}}
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <label for="manager">
                        <fmt:message key="deal.manager"/>
                    </label>
                    <span v-if="filter.creator !== -1" class="mini-close" v-on:click="clearFilterField('creator')">
                        &times;
                    </span>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <select id="manager" v-model="filter.creator" style="width: 100%">
                        <option :value="defaultId"><fmt:message key="all"/> </option>
                        <option v-for="creator in creators()" :value="creator.id">
                            {{creator.person.value}}
                        </option>
                    </select>
                </td>
            </tr>
        </table>
    </div>
</div>
</html>