<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <table>
        <c:if test="${deal.complete == 0}">
            <tr>
                <th colspan="3">
                    <fmt:message key="deal.delete.?sure"/>
                </th>
            </tr>
        </c:if>
        <tr>
            <td>
                <fmt:message key="period"/>
            </td>
            <td>
                :
            </td>
            <td>
                <fmt:formatDate value="${deal.date}" pattern="dd.MM.yyyy"/>
                <c:if test="${deal.date ne deal.dateTo}">
                    -<fmt:formatDate value="${deal.dateTo}" pattern="dd.MM.yyyy"/>
                </c:if>
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
                ${deal.organisation.value}
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
<%--                ${deal.product.name}--%>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.quantity"/>
            </td>
            <td>
                :
            </td>
            <td>
<%--                <fmt:formatNumber value="${deal.quantity}"/>&nbsp;${deal.unit.name}--%>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.done"/>
            </td>
            <td>
                :
            </td>
            <td>
<%--                <fmt:formatNumber value="${deal.complete}"/>&nbsp;${deal.unit.name}--%>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="right">
                <fmt:message key="deal.loads.done"/>:&nbsp;${fn:length(done)}
            </td>
        </tr>
        <tr>
            <td colspan="3" align="right">
                <span style="text-decoration: underline">
                    <fmt:message key="deal.loads"/>:&nbsp;${fn:length(loads)}
                </span>
            </td>
        </tr>
        <c:forEach items="${loads}" var="load">
            <tr>
                <td>
                    <%--<fmt:formatDate value="${load.date}" pattern="dd.MM"/>--%>
                    <%--<b><fmt:formatNumber value="${load.plan}"/>&nbsp;${deal.unit.name}</b>--%>
                </td>
                <td colspan="2">
                    <c:if test="${empty load.vehicle or empty load.driver}">
                        <fmt:message key="no.data"/>
                    </c:if>
                    <div>
                        ${load.vehicle.value}
                    </div>
                    <div>
                        ${load.driver.person.value}
                    </div>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="center">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button onclick="remove()">
                    <c:choose>
                        <c:when test="${deal.complete gt 0}">
                            <fmt:message key="menu.archive"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="button.delete"/>
                        </c:otherwise>
                    </c:choose>
                </button>
            </td>
        </tr>
    </table>
</html>
<script>
    var del = '${delete}';
    var id = ${deal.id};
    function remove(){
        PostApi(del, {id : id}, function(a){
            console.log(a);
            if (a.status === 'success'){
                closeModal();
            }
        })
    }
</script>