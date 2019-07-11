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
                timeOutApi:'',
                findSeals:'',
                saveSeal:'',
                removeSeal:''
            },
            id:-1,
            timeIn:'',
            timeOut:'',
            seals:[],
            sealInput:'',
            foundSeals:[],
            fnd:-1
        },
        methods: {
            setTimeIn: function () {
                const self = this;
                var parameters = {};
                parameters.id = this.id;
                PostApi(this.api.timeInApi, parameters, function (a) {
                    console.log(a)
                    self.timeIn = new Date(a.time);
                })
            },
            setTimeOut: function () {
                const self = this;
                var parameters = {};
                parameters.id = this.id;
                PostApi(this.api.timeOutApi, parameters, function (a) {
                    console.log(a)
                    self.timeOut = new Date(a.time);
                })
            },
            findSeals:function(){
                clearTimeout(this.fnd)
                if (this.sealInput) {
                    const self = this;
                    this.fnd = setTimeout(function () {
                        var param = {};
                        param.key = self.sealInput;
                        PostApi(self.api.findSeals, param, function (a) {
                            self.foundSeals = a;
                        })
                    }, 200)
                }
            },
            addSeal:function(seal){
                var parameters = {};
                parameters.seal = seal.id;
                parameters.transportation = this.id;
                var self = this;
                PostApi(this.api.saveSeal, parameters, function(a){
                    if (a.status = 'success'){
                        self.seals.push(seal);
                        self.sealInput = '';
                        self.foundSeals = [];
                    }
                })
                
            },
            removeSeal:function(key){
                var seal = this.seals[key];
                var parameters = {};
                parameters.seal = seal.id;
                var self = this;
                PostApi(this.api.removeSeal, parameters, function(a){
                    if (a.status = 'success'){
                        self.seals.splice(key, 1)
                        console.log('remove seal \'' + key + '\'')
                    }
                })

            }
        }
    });
    show.api.timeInApi = '${timeInLink}';
    show.api.timeOutApi = '${timeOutLink}';
    show.api.findSeals = '${findSeals}';
    show.api.saveSeal = '${saveSeal}';
    show.api.removeSeal = '${removeSeal}';
    show.id = ${plan.transportation.id};
    <c:if test="${not empty plan.transportation.timeIn}">
    show.timeIn = new Date('${plan.transportation.timeIn.time}');</c:if>
    <c:if test="${not empty plan.transportation.timeOut}">
    show.timeOut = new Date('${plan.transportation.timeOut.time}');</c:if>
    <c:forEach items="${plan.transportation.seals}" var="seal">
    show.seals.push({
        id:${seal.id},
        number:'${seal.number}'
    })
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
            <fmt:formatDate value="${plan.date}" pattern="dd.MM.yyyy"/>
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
            ${plan.deal.organisation.value}
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
            ${plan.deal.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.from"/>
        </td>
        <td>:</td>
        <td>
            ${plan.deal.shipper.value}
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
                    <c:when test="${not empty plan.transportation.vehicle.id }">
                        <span>
                            ${plan.transportation.vehicle.model}
                        </span>

                        <div style="display: inline-block; font-size: 10pt">
                            <div>
                                '${plan.transportation.vehicle.number}'
                            </div>
                            <c:if test="${not empty plan.transportation.vehicle.trailer}">
                                <div>
                                    '${plan.transportation.vehicle.trailer}'
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
        <td style="width: 180px">
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
        <td colspan="4" align="center">
            <button onclick="closeModal()"><fmt:message key="button.close"/> </button>
        </td>
    </tr>
</table>
</html>