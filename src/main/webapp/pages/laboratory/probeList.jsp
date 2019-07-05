<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<div id="container-header" style="display: inline">
    <c:forEach items="${analysesTypes}" var="a">
        <button ondblclick="" onclick="loadModal('${edit}?type=${a}')">
            <fmt:message key="${a}"/>
        </button>
    </c:forEach>
</div>
<script src="${context}/vue/archive/archiveList.vue"></script>
<script>
    archive.api.update = '${update}';
    archive.api.show = '${show}';
    archive.updReq();
</script>
<div id="archive" class="container">
    <div v-for="item in items" class="container-item" :class="'container-item-' + new Date(item.item.date).getDay()">
        <div class="upper-row">
            <span v-if="item.item.turn">
                {{new Date(item.item.turn.date).toLocaleDateString()}}
                <fmt:message key="turn"/> <span>#</span>{{item.item.turn.number}}
            </span>
            <span v-else>
                {{new Date(item.item.date).toLocaleDateString()}}
            </span>
            <span>
                <fmt:message key="deal.manager"/>:
                <b v-if="item.item.manager">
                    {{item.item.manager}}
                </b>
                <b v-else>
                    <fmt:message key="no.data"/>
                </b>
            </span>
            <span>
                <fmt:message key="deal.organisation"/>:
                <b>
                    {{item.item.organisation}}
                </b>
            </span>
        </div>
        <div class="lower-row" style="font-size: 10pt">
            <template v-if="item.item.type == 'sun'">
                <b style="color: #535353"><fmt:message key="sun"/></b>
                <fmt:message key="sun.humidity"/>:
                {{item.item.analyses.humidity}},
                <fmt:message key="sun.soreness"/>:
                {{item.item.analyses.soreness}},
                <fmt:message key="sun.oiliness"/>:
                {{item.item.analyses.oiliness}},
                <fmt:message key="sun.oil.impurity"/>:
                {{item.item.analyses.oilImpurity}},
                <fmt:message key="sun.acid.value"/>:
                {{item.item.analyses.acidValue}}
                <b v-if="item.item.analyses.contamination" style="color: #ff4a00">
                    <fmt:message key="sun.contamination"/>
                </b>
            </template>
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