<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
    .content table{
        border-collapse: collapse;
    }
    .content table, .content th, .content td {
        border: 1px solid black;
    }
</style>
<div style="padding-left: 24pt">
    <table width="100%" style="font-size: 14pt">
        <c:forEach items="${turns}" var="turn">
            <tr>
                <td align="center">
                    <b>
                        <fmt:message key="turn"/>&nbsp;${turn.turn.number},
                        <fmt:formatDate value="${turn.turn.date}" pattern="dd.MM.yyyy"/>
                    </b>
                    <div style="float: right">
                        <c:forEach items="${laboratory}" var="l">
                            <c:if test="${l.key eq turn.turn.id}">
                                <c:forEach items="${l.value}" var="w">
                                    <div>
                                        ${w.worker.person.value}
                                    </div>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="content">
                    <table width="100%" style="font-size: 12pt">
                        <tr>
                            <td rowspan="2" align="center">
                                <fmt:message key="time"/>
                            </td>
                            <td colspan="2" align="center">
                                <fmt:message key="vro.sun.before"/>
                            </td>
                            <td colspan="2" align="center">
                                <fmt:message key="vro.sun.after"/>
                            </td>
                            <th rowspan="2" style="width: 7em">
                                <fmt:message key="vro.huskiness"/>
                            </th>
                            <th rowspan="2" style="width: 5em">
                                <fmt:message key="vro.kernel.offset"/>
                            </th>
                            <th rowspan="2" style="width: 5em">
                                <fmt:message key="vro.pulp.humidity.1"/>
                            </th>
                            <th rowspan="2" style="width: 5em">
                                <fmt:message key="vro.pulp.humidity.2"/>
                            </th>
                            <c:forEach items="${forpress}" var="fp">
                                <td colspan="2" align="center">
                                    ${fp.name}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <th style="width: 5em">
                               <fmt:message key="sun.humidity"/>
                            </th>
                            <th style="width: 5em">
                                <fmt:message key="sun.soreness"/>
                            </th>
                            <th style="width: 5em">
                                <fmt:message key="sun.humidity"/>
                            </th>
                            <th style="width: 5em">
                                <fmt:message key="sun.soreness"/>
                            </th>
                            <c:forEach items="${forpress}" var="fp">
                                <th style="width: 4em">
                                    <fmt:message key="sun.humidity.short"/>
                                </th>
                                <th style="width: 4em">
                                    <fmt:message key="sun.oiliness.short"/>
                                </th>
                            </c:forEach>
                        </tr>
                        <c:forEach items="${turn.crudes}" var="crude">
                            <tr>
                                <td align="center">
                                    <fmt:formatDate value="${crude.time}" pattern="HH:mm"/>
                                </td>
                                <td align="center">
                                    ${crude.humidityBefore}
                                </td>
                                <td align="center">
                                    ${crude.sorenessBefore}
                                </td>
                                <td align="center">
                                    ${crude.humidityAfter}
                                </td>
                                <td align="center">
                                    ${crude.sorenessAfter}
                                </td>
                                <td align="center">
                                    ${crude.huskiness}
                                </td>
                                <td align="center">
                                    ${crude.kernelOffset}
                                </td>
                                <td align="center">
                                    ${crude.pulpHumidity1}
                                </td>
                                <td align="center">
                                    ${crude.pulpHumidity2}
                                </td>
                                <c:forEach items="${forpress}" var="fp">
                                    <c:set var="found">false</c:set>
                                    <c:forEach items="${crude.forpressCakes}" var="fpc">
                                        <c:if test="${fp.id eq fpc.forpress.id}">
                                            <c:set var="found">true</c:set>
                                            <td align="center">
                                                ${fpc.humidity}
                                            </td>
                                            <td align="center">
                                                ${fpc.oiliness}
                                            </td>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${not found}">
                                        <td align="center">
                                            --
                                        </td>
                                        <td align="center">
                                            --
                                        </td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                    <c:if test="${fn:length(turn.oils) > 0}">
                        <div>
                            <b>
                                <fmt:message key="vro.press.oil"/>
                            </b>
                            <c:forEach items="${turn.oils}" var="oil">
                                <fmt:message key="sun.acid.value"/>:&nbsp;${oil.acid},
                                <fmt:message key="oil.peroxide"/>:&nbsp;${oil.peroxide},
                                <fmt:message key="oil.phosphorus"/>:&nbsp;${oil.phosphorus},
                                <fmt:message key="oil.color.value"/>:&nbsp;${oil.color}
                            </c:forEach>
                        </div>
                    </c:if>
                    <div style="padding-bottom: 48pt"></div>
                    <c:if test="${fn:length(turn.dailies) > 0}">
                        <div>
                            <b>
                                <fmt:message key="title.daily.analyses"/>
                            </b>
                            <c:forEach items="${turn.dailies}" var="daily">
                                <fmt:message key="kernel.humidity"/>:&nbsp;${daily.kernelHumidity},
                                <fmt:message key="husk.humidity"/>:&nbsp;${daily.huskHumidity},
                                <fmt:message key="husk.soreness"/>:&nbsp;${daily.huskSoreness},
                                <fmt:message key="kernel.percent"/>:&nbsp;${daily.kernelPercent},
                                <fmt:message key="husk.percent"/>:&nbsp;${daily.huskPercent}
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(turn.oilMassFractions) > 0}">
                        <div>
                            <b>
                                <fmt:message key="oil.mass.fraction"/>
                            </b>
                            <c:forEach items="${turn.oilMassFractions}" var="o">
                                <fmt:message key="oil.mass.fraction.seed"/>:&nbsp;${o.seed},
                                <fmt:message key="oil.mass.fraction.seed.humidity"/>:&nbsp;${o.seedHumidity},
                                <fmt:message key="oil.mass.fraction.husk"/>:&nbsp;${o.husk},
                                <fmt:message key="oil.mass.fraction.husk.humidity"/>:&nbsp;${o.huskHumidity},
                                <c:forEach items="${o.forpressCakes}" var="f">
                                    <div style="width: 100%; padding-left: 24pt">
                                        <b>
                                            ${f.forpress.name}
                                        </b>
                                        <fmt:message key="oilcake"/>:&nbsp;${f.oiliness},
                                        <fmt:message key="oilcake.humidity"/>:&nbsp;${f.humidity}
                                    </div>
                                </c:forEach>
                            </c:forEach>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(turn.oilMassFractionDries) > 0}">
                        <div>
                            <b>
                                <fmt:message key="vro.daily.oil.mass.dry"/>
                            </b>
                            <c:forEach items="${turn.oilMassFractionDries}" var="d">
                                <fmt:message key="oil.mass.fraction.seed"/>:&nbsp;${d.seed},
                                <fmt:message key="oil.mass.fraction.husk"/>:&nbsp;${d.husk}
                                <c:forEach items="${d.forpressCakes}" var="f">
                                    <div style="width: 100%; padding-left: 24pt">
                                        <b>
                                            ${f.forpress.name}
                                        </b>
                                        <fmt:message key="oilcake"/>:&nbsp;${f.oilcake}
                                    </div>
                                </c:forEach>
                            </c:forEach>
                        </div>
                    </c:if>

                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</html>
