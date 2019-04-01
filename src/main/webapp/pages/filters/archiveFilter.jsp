<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/archiveFilter.js"></script>
<link rel="stylesheet" href="${context}/css/filter.css">
<div id="filter" class="filter">
    <b v-show="products.length > 0">
        <fmt:message key="deal.product"/>
    </b>
    <div class="group">
        <div v-for="product in products">
            <input :id="'product_' + product.id" type="checkbox">
            <label :for="'product_' + product.id">
                {{product.value}}
            </label>
        </div>
    </div>
    <b>
        <fmt:message key="creator"/>
    </b>
    <div class="group">
        <select>
            <option value="" disabled><fmt:message key="unselect"/> </option>
        </select>
    </div>
    <b>
        <fmt:message key="date"/>
    </b>
    <input>
    <b>
        <fmt:message key="filter.pages"/>
    </b>
    <div class="group" style="max-height: 80px">
        <a style="display: flex" v-for="page in pages">{{page.name}}</a>
    </div>
    <button v-on:click="clear">
        <fmt:message key="buton.clear"/>
    </button>
</div>

</html>