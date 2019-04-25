<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table>
    <tr>
        <td colspan="3">
            <p>
                <fmt:message key="weight.cancel.info"/>
            </p>
        </td>
    </tr>
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
        <td>
            :
        </td>
        <td>
            ${plan.deal.organisation.value}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.product"/>
        </td>
        <td>
            :
        </td>
        <td>
            ${plan.deal.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="load.plan"/>
        </td>
        <td>
            :
        </td>
        <td>
            <fmt:formatNumber value="${plan.plan}"/>&nbsp;${plan.deal.unit.name}
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
                <c:when test="${not empty plan.transportation.vehicle}">
                    ${plan.transportation.vehicle.value}
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
                <c:when test="${not empty plan.transportation.driver}">
                    ${plan.transportation.driver.person.value}
                </c:when>
                <c:otherwise>
                    <fmt:message key="no.data"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button onclick="cancelIt()">
                <fmt:message key="button.continue"/>
            </button>
        </td>
    </tr>
</table>
</html>
<script>
    function cancelIt(){
        var cancel = '${cancel}';
        var id = ${plan.id};
        PostApi(cancel, {id: id}, function(a){
            if (a.status == 'success'){
                closeModal();
            }
        })
    }
</script>