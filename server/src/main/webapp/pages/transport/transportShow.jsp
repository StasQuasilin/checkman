<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/transportationShow.vue"></script>
<script>
    show.api.timeApi = '${saveTime}';
    show.api.removeTime = '${removeTime}';
    show.api.registration = '${registration}';
    show.api.findSeals = '${findSeals}';
    show.api.saveSeal = '${saveSeal}';
    show.api.removeSeal = '${removeSeals}';
    show.id = ${transportation.id};
    show.directionIn = '${in}';
    show.directionOut = '${out}';
    <c:if test="${not empty transportation.timeRegistration}">
    show.registrationTime = new Date('${transportation.timeRegistration.time}');
    </c:if>
    <c:if test="${not empty transportation.timeIn}">
    show.timeIn = new Date('${transportation.timeIn.time}');</c:if>
    <c:if test="${not empty transportation.timeOut}">
    show.timeOut = new Date('${transportation.timeOut.time}');</c:if>
    <c:forEach items="${seals}" var="seal">
    show.seals.push(${seal.toJson()});
    </c:forEach>
    function truckInfo(){
        <c:if test="${not empty transportation.vehicle.id }">
        loadModal('${truckInfo}', {id:${transportation.vehicle.id}})
        </c:if>
    }
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
                        {{value.value}}
                    </span>
                </div>
                <div style="font-size: 10pt">
                    <div v-if="offer.length > 0" style="font-weight: bold">
                        <fmt:message key="seals.add.question"/>
                        {{offer.length}}
                    </div>
                    <div v-for="seal in offer">
                        {{seal.value}}
                    </div>
                    <div v-if="offer.length > 0" style="text-align: center">
                        <span class="mini-close" v-on:click="addOffer()">
                            <fmt:message key="button.yes"/>
                        </span>
                        <span class="mini-close" v-on:click="offer = []">
                            <fmt:message key="button.no"/>
                        </span>
                    </div>
                </div>

                <div>
                    <input style="width: 100%" v-model="sealInput" v-on:keyup="findSeals()">
                    <div class="custom-data-list" v-if="foundSeals.length > 0">
                        <div v-for="seal in foundSeals" v-on:click="addSeal(seal, true)" class="custom-data-list-item">
                            {{seal.value}}
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
                        <div>
                            <span>
                                ${transportation.vehicle.model}
                            </span>
                            <div style="display: inline-block; font-size: 10pt">
                                <div>
                                    '${transportation.vehicle.number}'
                                </div>
                                <c:if test="${not empty transportation.trailer}">
                                    <div>
                                        '${transportation.trailer.number}'
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div>
                            <button onclick="truckInfo()">
                                <fmt:message key="vehicle.info"/>
                            </button>
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
            <div v-else-if="!timeIn" v-on:click="registration()">
                <button v-if="already">
                    ...
                </button>
                <button v-else>
                    <fmt:message key="transportation.registrate"/>
                </button>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.time.in"/>
        </td>
        <td>
            :
        </td>
        <td style="width: 200px">
            <div v-if="timeIn">
                <button>
                    {{(timeIn).toLocaleString()}}
                </button>
                <span class="mini-close" v-on:click="removeTimeIn()">
                    &times;
                </span>
            </div>
            <div v-else>
                <button v-if="already">
                    ...
                </button>
                <button v-else v-on:click="setTimeIn">
                    <fmt:message key="transportation.in"/>
                </button>
            </div>
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
            <div v-if="timeOut">
                <button>
                    {{(timeOut).toLocaleString()}}
                </button>
                <span class="mini-close" v-on:click="removeTimeOut()">
                    &times;
                </span>
            </div>
            <div v-else>
                <button v-if="already">
                    ...
                </button>
                <button v-else v-on:click="setTimeOut">
                    <fmt:message key="transportation.out"/>
                </button>
            </div>
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