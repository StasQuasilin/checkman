<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/shortcuts.css">

<div id="cuts" class="shortCuts">
    <span class="title">
        <fmt:message key="on.territory"/>:
        {{onTerritory.length}}
    </span>
    <div class="block">
        <div v-for="plan in onTerritory">
            <div>
                {{new Date(plan.transportation.timeIn).toLocaleTimeString()}}
            </div>
            <div>
                <span>
                {{plan.transportation.vehicle.model}}
                '{{plan.transportation.vehicle.number}}'
                '{{plan.transportation.vehicle.trailer}}'
                </span>::
                <span>
                    {{plan.transportation.driver.person.value}}
                </span>
            </div>
            <div>
                <span>
                    {{plan.organisation.value}}
                </span>::
                <span>
                    {{plan.product.name}}, {{(types[plan.type]).toLowerCase()}}
                </span>
            </div>
        </div>
    </div>
    <span class="title">
        <fmt:message key="on.cruise"/>:
        {{onCruise.length}}
    </span>
    <div class="block">

    </div>

</div>
<script src="${context}/vue/shortCuts.js"></script>
<script>
    short.api.update = '${shortCutUpdate}';
    short.updReq();
</script>
</html>