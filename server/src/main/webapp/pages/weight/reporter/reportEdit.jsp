<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 11.04.2020
  Time: 19:53
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/weight/reporter.vue"></script>
    <script>
        reporter.api.save = '${save}';
        <c:forEach items="${subdivisions}" var="subdivision">
        reporter.addSubdivision(${subdivision.toJson()});
        </c:forEach>
        <c:if test="${not empty report}">
        reporter.report = ${report.toJons()}
        </c:if>
    </script>
    <table id="reporter">
        <tr>
            <td>
                <fmt:message key="date"/>
            </td>
            <td>
                <span>
                    {{new Date(report.date).toLocaleDateString()}}
                </span>
            </td>
        </tr>
        <template v-for="f in report.reports">
            <tr>
                <td style="font-size: 12pt">
                    {{f.subdivision.name}}
                </td>
                <td>
                    <input :title="f.subdivision.name" type="checkbox" v-model="f.good">
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea :v-model="f.note" style="width: 100%; height: 60px"></textarea>
                </td>
            </tr>
        </template>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="button.close"/>
                </button>
                <button v-on:click="save">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
