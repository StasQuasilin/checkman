<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
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
            <fmt:formatNumber value="${plan.amount}"/>&nbsp;${plan.deal.unit.name}
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
                <c:when test="${not empty plan.vehicle}">
                    ${plan.vehicle.value}
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
                <c:when test="${not empty plan.driver}">
                    ${plan.driver.person.value}
                </c:when>
                <c:otherwise>
                    <fmt:message key="no.data"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()" class="close-button">
                <fmt:message key="button.cancel"/>
            </button>
            <button onclick="cancelIt()" class="save-button">
                <c:if test="${plan.any()}">
                    <fmt:message key="menu.archive"/>
                </c:if>
                <c:if test="${plan.any() ne true}">
                    <fmt:message key="button.delete"/>
                </c:if>
            </button>
        </td>
    </tr>
</table>
</html>
<script>
    function cancelIt(){
        let cancel = '${cancel}';
        let id = ${plan.id};
        PostApi(cancel, {id: id}, function(a){
            if (a.status === 'success'){
                closeModal();
            }
        })
    }
</script>