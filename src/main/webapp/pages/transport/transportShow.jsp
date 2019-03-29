<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var show = new Vue({
        el: '#show',
        data:{
            api:{
                timeInApi:'',
                timeOutApi:''
            },
            id:-1,
            timeIn:'',
            timeOut:''
        },
        methods:{
            setTimeIn:function(){
                const self = this;
                this.setTime(this.api.timeInApi, self.timeIn);
            },
            setTimeOut:function(){
                this.setTime(this.api.timeOutApi, this.timeOut);
            },
            setTime:function(api, header){
                var parameters = {};
                parameters.id = this.id;
                PostApi(api, parameters, function(a){
                    console.log(a)
                    header = new Date(a.time);
                })
            }
        }
    });
    show.api.timeInApi = '${timeInLink}';
    show.api.timeOutApi = '${timeOutLink}';
    show.id = ${plan.transportation.id};
    <c:if test="${not empty plan.transportation.timeIn}">
    show.timeIn = new Date('${plan.transportation.timeIn.time}');</c:if>
    <c:if test="${not empty plan.transportation.timeOut}">
    show.timeOut = new Date('${plan.transportation.timeOut.time}');</c:if>
</script>
<table id="show">
    <tr>
        <td>
            <fmt:message key="date"/>
        </td>
        <td>
            :
        </td>
        <td>
            <fmt:formatDate value="${plan.date}" pattern="dd.MM.yyyy"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>:</td>
        <td>
            ${plan.deal.organisation.value}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.product"/>
        </td>
        <td>:</td>
        <td>
            ${plan.deal.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.from"/>
        </td>
        <td>:</td>
        <td>
            ${plan.deal.documentOrganisation.value}
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
            <c:choose>
                <c:when test="${not empty plan.transportation.vehicle.id }">
                    ${plan.transportation.vehicle.model}
                    '${plan.transportation.vehicle.number}'
                    <c:if test="${not empty plan.transportation.vehicle.trailer}">
                        '${plan.transportation.vehicle.trailer}'
                    </c:if>
                </c:when>
                <c:otherwise>
                    <fmt:message key="no.data"/>
                </c:otherwise>
            </c:choose>
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
                <c:when test="${not empty plan.transportation.driver.id}">
                    ${plan.transportation.driver.person.value}
                </c:when>
                <c:otherwise>
                    <fmt:message key="no.data"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.time.in"/>
        </td>
        <td>
            :
        </td>
        <td>
            <button v-if="timeIn">
                {{(timeIn).toLocaleTimeString() + ', ' +
                (timeIn).toLocaleDateString()}}
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
                {{(timeOut).toLocaleTimeString() + ', ' +
                (timeOut).toLocaleDateString()}}
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
        <td colspan="3" align="center">
            <button onclick="closeModal()"><fmt:message key="button.close"/> </button>
        </td>
    </tr>
</table>
</html>