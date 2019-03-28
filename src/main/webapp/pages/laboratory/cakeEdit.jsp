<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratoryEdit.js"></script>
<script>
    editor.api.save = '${saveUrl}';
    editor.plan = ${plan.id};
    editor.organisation = '${plan.deal.organisation.value}';
    <c:if test="${not empty plan.transportation.vehicle}">
    editor.vehicle.model = '${plan.transportation.vehicle.model}';
    editor.vehicle.number = '\'${plan.transportation.vehicle.number}\''
    editor.vehicle.trailer = ' \'${plan.transportation.vehicle.trailer}\''
    </c:if>
    <c:if test="${not empty plan.transportation.driver}">
    editor.driver = '${plan.transportation.driver.person.value}';
    </c:if>
    editor.empty={
        humidity:0,
        protein:0,
        cellulose:0,
        oiliness:0
    }
    <c:if test="${not empty plan.transportation.cakeAnalyses}">
    <c:forEach items="${plan.transportation.cakeAnalyses}" var="cake">
    editor.addAnalyses(
    {
        id:${cake.analyses.id},
        humidity:${cake.analyses.humidity},
        protein:${cake.analyses.protein},
        cellulose:${cake.analyses.cellulose},
        oiliness:${cake.analyses.oiliness}
    }
    );
    </c:forEach>
    </c:if>
    if (editor.analyses.length == 0){
        editor.newAnalyses();
    }
</script>
<table id="editor" class="editor">
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            :
        </td>
        <td>
            {{organisation}}
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
                <span>
                    {{vehicle.model}}
                </span>
                <div style="display: inline-block; font-size: 8pt">
                    <div>
                        {{vehicle.number}}
                    </div>
                    <div>
                        {{vehicle.trailer}}
                    </div>

                </div>
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
            {{driver}}
        </td>
    </tr>
    <template v-for="item in analyses">
        <tr>
            <td>
                <label for="humidity">
                    <fmt:message key="sun.humidity"/>
                </label>

            </td>
            <td>
                :
            </td>
            <td>
                <input id="humidity" type="number" step="0.01" v-model="item.humidity">
            </td>
        </tr>
        <tr>
            <td>
                <label for="protein">
                    <fmt:message key="cake.protein"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="protein" type="number" step="0.01" v-model="item.protein">
            </td>
        </tr>
        <tr>
            <td>
                <label for="cellulose">
                    <fmt:message key="cake.cellulose"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="cellulose" type="number" step="0.01" v-model="item.cellulose">
            </td>
        </tr>
        <tr>
            <td>
                <label for="oiliness">
                    <fmt:message key="sun.oiliness"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="oiliness" type="number" step="0.01" v-model="item.oiliness">
            </td>
        </tr>
    </template>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save" v-on:dblclick="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>

</table>
</html>