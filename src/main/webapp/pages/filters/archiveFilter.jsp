<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/archiveFilter.vue"></script>
<script>
    <c:forEach items="${products}" var="p">
    filter_control.items.products.push({
        id:${p.id},
        value:'${p.name}'
    })
    </c:forEach>
</script>
<link rel="stylesheet" href="${context}/css/filter.css">

<div id="filter" class="filter">
    <c:set var="noData"><fmt:message key="no.data"/> </c:set>
    <table width="100%">
        <tr>
            <td>
                <label for="date">
                    <fmt:message key="date"/>:
                </label>
                <input id="date" placeholder="${noData}" style="width: 7em;" readonly
                       v-model="filterDate " v-on:click="pickDate" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td>
                <label for="product">
                    <fmt:message key="deal.product"/>:
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <select id="product" style="width: 100%" v-model="filter.product">
                    <option :value="-1"><fmt:message key="all"/> </option>
                    <option v-for="product in items.products" :value="product.id">{{product.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="organisation">
                    <fmt:message key="deal.organisation"/>:
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <div style="display: inline-block">
                    <input id="organisation" placeholder="${noData}" v-model="input.organisation" autocomplete="off">
                    <div class="custom-data-list">
                        <div v-for="organisation in items.organisations">
                            {{organisation.value}}
                        </div>
                    </div>
                </div>
                <span v-if="input.organisation" class="mini-close" style="float: right ;left: -22px"
                      v-on:click="closeOrganisation()">&times;</span>
            </td>
        </tr>
        <tr>
            <td>
                <label for="driver">
                    <fmt:message key="transportation.driver"/>:
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <input id="driver" placeholder="${noData}">
            </td>
        </tr>
        <tr>
            <td>
                <label for="vehicle">
                    <fmt:message key="transportation.automobile"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <input id="vehicle" placeholder="${noData}">
            </td>
        </tr>
        <tr>
            <td align="center">
                <button v-on:click="clear">
                    <fmt:message key="button.clear"/>
                </button>
            </td>
        </tr>

    </table>
</div>

</html>