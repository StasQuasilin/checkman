
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 11.12.20
  Time: 14:58
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    seals = ${seals.id}
    removeApi = '${context}${remove}';
    function remove() {
        loadModal(removeApi, {id:seals}, function (a) {
            if (a.status === 'success') {
                closeModal();
            }
        })
    }
</script>
<table>
    <tr>
        <td colspan="2">
            ${seals.title}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="created"/>
        </td>
        <td>
            <fmt:formatDate value="${seals.created.time}" pattern="dd.MM.yyyy"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.leave"/>
        </td>
        <td>
            ${seals.free} / ${seals.total}
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <span class="mini-close" onclick="remove()">
                <fmt:message key="button.delete"/>
            </span>
            <button onclick="closeModal()">
                OK
            </button>
        </td>
    </tr>
</table>
</html>
