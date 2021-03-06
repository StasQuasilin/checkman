<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/archive/archiveFilter.vue?v=${now}"></script>
<script>
    transportFilter.api.find = '${find}';
    transportFilter.api.findOrganisations = '${findOrganisations}';
    transportFilter.api.findDrivers = '${findDrivers}';
    <c:forEach items="${products}" var="p">
    transportFilter.productList.push({
        id:${p.id},
        value:'${p.name}'
    });
    </c:forEach>
</script>
<link rel="stylesheet" href="${context}/css/filter.css">
    <div id="filter_view" class="filter">
        <c:set var="noData"><fmt:message key="no.data"/> </c:set>
        <table width="100%">
            <tr>
                <td style="text-align: center">
                    <label for="date">
                        <span class="mini-close" v-on:click="period=!period">
                            <template v-if="period">
                                <fmt:message key="period"/>
                            </template>
                            <template v-else>
                                <fmt:message key="date"/>:
                            </template>
                        </span>
                    </label>
                    <input v-if="!period" id="date" placeholder="${noData}" style="width: 7em;" readonly
                           v-model="filterDate " v-on:click="pickDate" autocomplete="off">
                </td>
            </tr>
            <template v-if="period">
                <tr >
                    <td>
                        <fmt:message key="date.from"/>
                        <span class="mini-close" v-on:click="pickDate">
                            <template v-if="filterDate.length > 0">
                                <span>
                                    {{filterDate}}
                                </span>
                                <span class="mini-close" v-on:click="filter.date=''">
                                    &times;
                                </span>
                            </template>
                            <template v-else>
                                ???
                            </template>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="date.to"/>
                        <span class="mini-close" v-on:click="pickDateTo">
                            <template v-if="filterDateTo.length > 0">
                                <span>
                                    {{filterDateTo}}
                                </span>
                                <span class="mini-close" v-on:click="filter.dateTo=''">
                                    &times;
                                </span>
                            </template>
                            <template v-else>
                                ???
                            </template>
                        </span>
                    </td>
                </tr>
            </template>
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
                        <option value="-1"><fmt:message key="all"/></option>
                        <option v-for="product in productList" :value="product.id">{{product.value}}</option>
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
                        <input id="organisation" placeholder="${noData}" v-model="input.organisation"
                               v-on:keyup="findOrganisation()" autocomplete="off">
                        <div class="custom-data-list">
                            <div class="custom-data-list-item" v-for="organisation in foundOrganisations"
                                 v-on:click="putOrganisation(organisation)">
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
                    <input id="driver" placeholder="${noData}" v-model="input.driver"
                           v-on:keyup="findDriver" autocomplete="off">
                    <div class="custom-data-list">
                        <div class="custom-data-list-item" v-for="driver in foundDrivers"
                            v-on:click="putDriver(driver)">
                            {{driver.person.value}}
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <button v-on:click="find">
                        <fmt:message key="find"/>
                    </button>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <a v-on:click="clear">
                        <fmt:message key="button.clear"/>
                    </a>
                </td>
            </tr>
            <tr v-if="tooFewParam">
                <td>
                    <div style="color: orangered; font-weight: bold; width: 100%; text-align: center">
                        <fmt:message key="too.few.params"/>
                    </div>
                </td>
            </tr>
            <tr v-if="dateLimit && result.length > 0">
                <td>
                    <div class="error">
                        <fmt:message key="filter.date.limit"/>
                    </div>
                </td>
            </tr>
            <tr v-if="countLimit > 0 && result.length > 0">
                <td>
                    <div class="error">
                        <fmt:message key="filter.count.limit"/> {{countLimit.toLocaleString()}}
                        <fmt:message key="filter.count.limit.continue"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <span style="font-size: 10pt">
                        <fmt:message key="items.count"/>: {{result.length}}
                    </span>
                </td>
            </tr>
        </table>
    </div>
</html>