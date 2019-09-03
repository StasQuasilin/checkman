<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/transportationShow.vue"></script>
<script>
    show.api.timeInApi = '${timeInLink}';
    show.api.timeOutApi = '${timeOutLink}';
    show.api.registration = '${registration}';
    show.api.findSeals = '${findSeals}';
    show.api.saveSeal = '${saveSeal}';
    show.api.removeSeal = '${removeSeal}';
    show.id = ${transportation.id};
    <c:if test="${not empty transportation.timeRegistration}">
    show.registrationTime = new Date('${transportation.timeRegistration.time}');
    </c:if>
    <c:if test="${not empty transportation.timeIn}">
    show.timeIn = new Date('${transportation.timeIn.time}');</c:if>
    <c:if test="${not empty transportation.timeOut}">
    show.timeOut = new Date('${transportation.timeOut.time}');</c:if>
    <c:forEach items="${transportation.seals}" var="seal">
    show.seals.push({
        id:${seal.id},
        number:'${seal.number}'
    });
    </c:forEach>
</script>
<table id="show" border="0">
    <tr>
        <td>
            <fmt:message key="date"/>
        </td>
        <td>
            :
        </td>
        <td>
            <fmt:formatDate value="${transportation.date}" pattern="dd.MM.yyyy"/>
        </td>
        <th>
            <fmt:message key="menu.seals"/>:{{seals.length}}
        </th>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>:</td>
        <td>
            ${transportation.counterparty.value}
        </td>
        <td rowspan="10" valign="top">
            <div style="padding: 2px; width: 160px">
                <div v-for="(value, key) in seals">
                    <span class="mini-close" style="left: 0" v-on:click="removeSeal(key)">&times;</span>
                    <span>
                        {{value.number}}
                    </span>
                </div>
                <div>
                    <input style="width: 100%" v-model="sealInput" v-on:keyup="findSeals()">
                    <div class="custom-data-list" v-if="foundSeals.length > 0">
                        <div v-for="seal in foundSeals" v-on:click="addSeal(seal)" class="custom-data-list-item">
                            {{seal.number}}
                        </div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.product"/>
        </td>
        <td>:</td>
        <td>
            ${transportation.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.automobile"/>
        </td>
        <td>
            :
        </td>
        <td>
            <div>
                <c:choose>
                    <c:when test="${not empty transportation.vehicle.id }">
                        <span>
                            ${transportation.vehicle.model}
                        </span>

                        <div style="display: inline-block; font-size: 10pt">
                            <div>
                                '${transportation.vehicle.number}'
                            </div>
                            <c:if test="${not empty transportation.vehicle.trailer}">
                                <div>
                                    '${transportation.vehicle.trailer}'
                                </div>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="no.data"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.driver"/>
        </td>
        <td>
            :
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty transportation.driver.id}">
                    ${transportation.driver.person.value}
                </c:when>
                <c:otherwise>
                    <fmt:message key="no.data"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <span v-if="registrationTime">
                <fmt:message key="transportation.registered.at"/>
            </span>
        </td>
        <td>
            <button v-if="registrationTime">
                {{(registrationTime).toLocaleString()}}
            </button>
            <button v-else v-on:click="registration()">
                <fmt:message key="transportation.registrate"/>
            </button>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.time.in"/>
        </td>
        <td>
            :
        </td>
        <td style="width: 180px">
            <button v-if="timeIn">
                {{(timeIn).toLocaleString()}}
            </button>
            <button v-else v-on:click="setTimeIn">
                <fmt:message key="transportation.in"/>
            </button>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.time.out"/>
        </td>
        <td>
            :
        </td>
        <td>
            <button v-if="timeOut">
                {{(timeOut).toLocaleString()}}
            </button>
            <button v-else v-on:click="setTimeOut">
                <fmt:message key="transportation.out"/>
            </button>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="weight.brutto"/>
        </td>
        <td>
            :
        </td>
        <td>
            <fmt:formatNumber value="${brutto}"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="weight.tara"/>
        </td>
        <td>
            :
        </td>
        <td>
            <fmt:formatNumber value="${tara}"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="weight.netto"/>
        </td>
        <td>
            :
        </td>
        <td>
            <fmt:formatNumber value="${netto}"/>
        </td>
    </tr>

    <tr>
        <td colspan="4" align="center">
            <button onclick="closeModal()"><fmt:message key="button.close"/> </button>
        </td>
    </tr>
</table>
</html>