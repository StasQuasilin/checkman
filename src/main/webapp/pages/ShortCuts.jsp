<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/shortcuts.css">
<div id="cuts" class="shortCuts"
     :class="{ dynamicShow : dynamic.show, dynamicCutter : isDynamic()}">
    <div class="shower" v-on:click="show" v-if="isDynamic()">
        <span class="direction-sign" v-if="dynamic.show">
            >
        </span>
        <span class="direction-sign" v-else>
            <
        </span>
    </div>
    <div class="cuts-wrapper" :style="{width : dynamicWidth()}">
        <div class="cuts">
            <span class="title">
                <fmt:message key="on.territory"/>:
                {{onTerritory.length}}
            </span>
            <div class="block">
                <div v-for="plan in onTerritory" style="border-bottom: solid white 1pt">
                    <div>
                        {{prettyDate(plan.transportation.timeIn.time)}}
                    </div>
                    <div>
                        <span>
                        {{plan.transportation.vehicle.model}}
                        '{{plan.transportation.vehicle.number}} {{plan.transportation.vehicle.trailer}}'
                        </span>
                    </div>
                    <div>
                        {{plan.transportation.driver.person.value}}
                    </div>
                    <div>
                        <span>
                            {{plan.organisation.value}}
                        </span>::
                        <span>
                            {{plan.product.name}}, {{(types[plan.type]).toLowerCase()}}
                        </span>
                    </div>
                    <div>
                        <div v-for="weight in plan.transportation.weights">
                            <fmt:message key="weight.gross"/>:
                            {{weight.brutto}},
                            <fmt:message key="weight.tare"/>:
                            {{weight.tara}},
                            <fmt:message key="weight.net"/>:
                            {{weight.brutto > 0 && weight.tara > 0 ? (weight.brutto - weight.tara).toLocaleString() : 0}}
                        </div>
                    </div>
                </div>
            </div>
            <span class="title">
                <fmt:message key="on.cruise"/>:
                {{onCruise.length}}
            </span>
            <div class="block">
                <div v-for="plan in onCruise" style="border-bottom: solid white 1pt">
                    <div>
                        {{prettyDate(plan.transportation.timeOut.time)}}
                    </div>
                    <div>
                        <span>
                        {{plan.transportation.vehicle.model}}
                        '{{plan.transportation.vehicle.number}} {{plan.transportation.vehicle.trailer}}'
                        </span>
                    </div>
                    <div>
                        {{plan.transportation.driver.person.value}}
                    </div>
                    <div>
                        <span>
                            {{plan.organisation.value}}
                        </span>::
                        <span>
                            {{plan.product.name}}, {{(types[plan.type]).toLowerCase()}}
                        </span>
                    </div>
                    <div>
                        <div v-if="plan.transportation.weight.id">
                            <fmt:message key="weight.gross"/>:
                            {{plan.transportation.weight.brutto}},
                            <fmt:message key="weight.tare"/>:
                            {{plan.transportation.weight.tara}},
                            <fmt:message key="weight.net"/>:
                            {{plan.transportation.weight.brutto > 0 && plan.transportation.weight.tara > 0 ?
                            (plan.transportation.weight.brutto - plan.transportation.weight.tara).toLocaleString() : 0}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${context}/vue/shortCuts.vue"></script>
<script>
    short.api.update = '${shortCutUpdate}';
    short.updReq();
</script>
</html>