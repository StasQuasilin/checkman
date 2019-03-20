<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                <fmt:message key="deal.quantity"/>
            </td>
            <td>
                :
            </td>
            <td>
                <fmt:formatNumber value="${plan.plan}"/>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.from"/>
            </td>
            <td>
                :
            </td>
            <td>
                ${plan.deal.documentOrganisation.value}
            </td>
        </tr>
        <c:choose>
            <c:when test="${fn:length(plan.transportation.weights) eq 0}">
                <tr>
                    <td>
                        <label for="brutto">
                            <fmt:message key="weight.brutto"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="brutto">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="tara">
                            <fmt:message key="weight.tara"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="tara">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="netto">
                            <fmt:message key="weight.netto"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="netto" readonly>
                    </td>

                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${plan.transportation.weights}" var="weight">
                    <tr>
                        <td>
                            <label for="brutto">
                                <fmt:message key="weight.brutto"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="brutto" value="${weight.brutto}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="tara">
                                <fmt:message key="weight.tara"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="tara" value="${weight.tara}">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="netto">
                                <fmt:message key="weight.netto"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="netto" readonly value="${weight.netto}">
                        </td>

                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <tr>
            <td colspan="3" align="center">
                <button><fmt:message key="button.cancel"/> </button>
                <button><fmt:message key="button.save"/> </button>
            </td>
        </tr>
    </table>
</html>