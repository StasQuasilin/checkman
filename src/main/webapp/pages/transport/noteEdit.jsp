<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-07-02
  Time: 10:35
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/transport/noteEdit.vue?v=${now}"></script>
<script>
    noteEdit.api.save = '${save}';
    <c:if test="${not empty note}">
    noteEdit.note = ${note.toJson()};
    </c:if>
    noteEdit.transportation = ${transportation.id};
</script>
    <table id="noteEdit">
        <c:if test="${not empty transportation.driver}">
            <tr>
                <td>
                    <fmt:message key="transportation.driver"/>
                </td>
                <td>
                    ${transportation.driver.person.value}
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty transportation.vehicle}">
            <tr>
                <td>
                    <fmt:message key="transportation.automobile"/>
                </td>
                <td>
                    ${transportation.vehicle}
                </td>
            </tr>
        </c:if>
        <c:forEach items="${transportation.products}" var="product">
            <tr>
                <td colspan="2" style="border-bottom: solid gray 1pt"></td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="deal.organisation"/>
                </td>
                <td>
                    ${product.dealProduct.deal.organisation.value}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="deal.product"/>
                </td>
                <td>
                    ${product.dealProduct.product.name}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="2">
                <label for="note">
                    <fmt:message key="note"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <textarea id="note" v-model="note.note" style="resize: none; width: 100%"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button v-on:click="save">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
