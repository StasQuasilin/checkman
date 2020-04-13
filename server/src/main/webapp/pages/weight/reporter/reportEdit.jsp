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
        reporter.report = ${report.toJson()}
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
                <td colspan="2" style="font-size: 12pt">
                    {{f.subdivision.name}}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="equipment.health"/>:
                    <span v-on:click="f.serviceability=!f.serviceability">
                        <b style="color: green" v-if="f.serviceability">
                            <fmt:message key="norm"/>
                        </b>
                        <b style="color: orangered" v-else>
                            <fmt:message key="not.norm"/>
                        </b>
                    </span>
                </td>
            </tr>
            <tr v-if="f.subdivision.teh">
                <td>
                    <fmt:message key="process.adherence"/>
                    <span v-on:click="f.adherence=!f.adherence">
                        <b style="color: green" v-if="f.adherence">
                            <fmt:message key="norm"/>
                        </b>
                        <b style="color: orangered" v-else>
                            <fmt:message key="not.norm"/>
                        </b>
                    </span>
                </td>
            </tr>
            <tr v-if="!f.serviceability || !f.adherence || f.editNote">
                <td colspan="2">
                    <textarea v-model="f.note" style="width: 100%; height: 60px"></textarea>
                </td>
            </tr>

            <tr>
               <td colspan="2" style="border-bottom: solid gray 1pt"></td>
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
