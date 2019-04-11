<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<div id="container-header" style="display: inline">
    <c:forEach items="${analysesTypes}" var="a">
        <button onclick="loadModal('${editURL}?type=${a}')">
            <fmt:message key="button.do"/>
            <fmt:message key="${a}"/>
        </button>
    </c:forEach>
</div>
<script src="${context}/vue/archiveList.js"></script>
<script>
    archive.api.update = '${updateURL}';
    archive.api.show = '${showUrl}';
    archive.updReq();
</script>
<div id="archive" class="container">
    <div v-for="item in items" class="container-item">
        <div class="upper-row">
            {{new Date(item.item.date).toLocaleDateString()}}
            <span>
                <fmt:message key="deal.manager"/>:<b>
                {{item.item.manager.person.value}}
            </b>
            </span>
            <span>
                <fmt:message key="deal.organisation"/>:<b>
                {{item.item.organisation}}
            </b>
            </span>
            <span>
                <fmt:message key="laboratory.creator"/>:<b>
                {{item.item.analyses.create.creator.person.value}}
            </b>
            </span>
        </div>
        <div class="lower-row" style="font-size: 10pt">
            <template v-if="item.item.type == 'sun'">
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
                <fmt:message key="oil.color.value"/>:
                {{item.item.analyses.color}},
                <fmt:message key="sun.acid.value"/>:
                {{item.item.analyses.acid}},
                <fmt:message key="oil.peroxide"/>:
                {{item.item.analyses.peroxide}},
                <fmt:message key="oil.phosphorus"/>:
                {{item.item.analyses.phosphorus}},
                <fmt:message key="oil.wax"/>
                {{item.item.analyses.wax}}
            </template>
        </div>
    </div>
</div>
</html>