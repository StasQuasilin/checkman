<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<c:if test="${edit ne null}">
    <div id="container-header" style="display: inline">
        <c:forEach items="${analysesTypes}" var="a">
            <button ondblclick="" onclick="loadModal('${edit}?type=${a}')">
                <fmt:message key="${a}"/>
            </button>
        </c:forEach>
    </div>
</c:if>
<script>
    transportView = {

    }
</script>
<script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
<script src="${context}/vue/templates/pricePlug.vue"></script>
<script src="${context}/vue/templates/commentatorPlug.vue"></script>
<script src="${context}/vue/dataList.vue"></script>
<script>
    list.limit = 14;
    list.getItems = function(){
        if (transportFilter.result.length > 0)
        return transportFilter.result;
        else
            return list.items;
    };

    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        list.handler(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(){
            unSubscribe('${s}');
        });
        </c:forEach>
    }
</script>
<div id="container" class="container">
    <div v-for="item in getItems()" class="container-item" style="display: inline-block"
         :class="'container-item-' + new Date(item.item.date).getDay()">
        <div class="upper-row">
            {{new Date(item.item.date).toLocaleDateString()}}
            {{new Date(item.item.date).toLocaleTimeString().substring(0, 5)}}
            <fmt:message key="turn"/> <span>#</span>{{item.item.number}}
        </div>
        <div class="lower-row" style="font-size: 10pt">
            <div v-if="item.item.sun.length > 0">
                <b style="color: #535353; font-size: 14pt"><fmt:message key="sun"/></b>
                <table class="probe-table">
                    <tr>
                        <th>
                            <fmt:message key="sun.humidity"/>
                        </th>
                        <th>
                            <fmt:message key="sun.soreness"/>
                        </th>
                        <th>
                            <fmt:message key="sun.oiliness"/>
                        </th>
                        <th>
                            <fmt:message key="sun.oil.impurity"/>
                        </th>
                        <th>
                            <fmt:message key="sun.acid.value"/>
                        </th>
                        <th>
                            <fmt:message key="deal.organisation"/>
                        </th>
                        <th>
                            <fmt:message key="deal.manager"/>
                        </th>
                    </tr>
                    <tr v-for="sun in item.item.sun">
                        <td>
                            {{sun.analyses.humidity1}}
                        </td>
                        <td>
                            {{sun.analyses.soreness}}
                        </td>
                        <td>
                            {{sun.analyses.oiliness}}
                        </td>
                        <td>
                            {{sun.analyses.oilImpurity}}
                        </td>
                        <td>
                            {{sun.analyses.acidValue}}
                        </td>
                        <td>
                            {{sun.organisation}}
                        </td>
                        <td>
                            {{sun.manager}}
                        </td>
                    </tr>
                </table>
            </div>
            <div v-if="item.item.oil.length > 0">
                <b style="color: #929b00; font-size: 14pt"><fmt:message key="oil"/></b>
                <table style="font-size: 10pt; margin-left: 18pt">
                    <tr>
                        <th>
                            <fmt:message key="oil.organoleptic"/>
                        </th>
                        <th>
                            <fmt:message key="oil.color.value"/>:
                        </th>
                        <th>
                            <fmt:message key="sun.acid.value"/>:
                        </th>
                        <th>
                            <fmt:message key="oil.peroxide"/>:
                        </th>
                        <th>
                            <fmt:message key="oil.phosphorus"/>:
                        </th>
                        <th>
                            <fmt:message key="oil.wax"/>
                        </th>
                        <th>
                            <fmt:message key="oil.soap"/>
                        </th>
                        <th>
                            <fmt:message key="deal.organisation"/>
                        </th>
                        <th>
                            <fmt:message key="deal.manager"/>
                        </th>
                    </tr>
                    <tr v-for="oil in item.item.oil">
                        <td>
                            <span v-if="oil.analyses.organoleptic">
                                <fmt:message key="oil.organoleptic.match"/>
                            </span>
                            <span v-else>
                                <fmt:message key="oil.organoleptic.doesn't.match"/>
                            </span>
                        </td>
                        <td>
                            {{oil.analyses.color}}
                        </td>
                        <td>
                            {{oil.analyses.color}}
                        </td>
                        <td>
                            {{oil.analyses.acid}}
                        </td>
                        <td>
                            {{oil.analyses.peroxide}}
                        </td>
                        <td>
                            {{oil.analyses.phosphorus}}
                        </td>
                        <td>
                            {{oil.analyses.wax}}
                        </td>
                        <td>
                            {{oil.organisation}}
                        </td>
                        <td>
                            {{oil.manager}}
                        </td>
                    </tr>
                </table>

            </div>
            <template v-if="item.item.type == 'oil'">
                <b style="color: #929b00"><fmt:message key="oil"/></b>
                <fmt:message key="oil.color.value"/>:
                {{item.item.analyses.color}},
                <fmt:message key="sun.acid.value"/>:
                {{item.item.analyses.acid}},
                <fmt:message key="oil.peroxide"/>:
                {{item.item.analyses.peroxide}},
                <fmt:message key="oil.phosphorus"/>:
                {{item.item.analyses.phosphorus}},
                <fmt:message key="oil.wax"/>
                {{item.item.analyses.wax}},
                <fmt:message key="oil.soap"/>
                <span v-if="item.item.analyses.soap">
                    +
                </span>
                <span v-else>
                    -
                </span>
            </template>
        </div>
    </div>
</div>
</html>