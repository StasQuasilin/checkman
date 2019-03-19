<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    function onClose(){

    }
</script>
<table>
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
                    ${plan.transportation.vehicle.number}
                    <c:if test="${not empty plan.transportation.vehicle.trailer}">
                        ${plan.transportation.vehicle.trailer}
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
            <c:choose>
                <c:when test="${not empty plan.transportation.timeIn}">
                    <fmt:formatDate value="plan.transportation.timeIn" pattern="HH:mm dd.MM"/>
                </c:when>
                <c:otherwise>
                    <button>
                        <fmt:message key="transportation.in"/>
                    </button>
                </c:otherwise>
            </c:choose>
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
            <c:choose>
                <c:when test="${not empty plan.transportation.timeOut}">
                    <fmt:formatDate value="plan.transportation.timeOut" pattern="HH:mm dd.MM"/>
                </c:when>
                <c:otherwise>
                    <button>
                        <fmt:message key="transportation.out"/>
                    </button>
                </c:otherwise>
            </c:choose>
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

        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button><fmt:message key="button.close"/> </button>
        </td>
    </tr>
</table>
</html>