<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/personal/languageChange.vue?v=${now}"></script>
<script>
    language.api.save='${changeLanguage}';
    <c:forEach items="${languages}" var="language">
    language.languages.push({
        id:'${language}',
        value:'<fmt:message key="${language}"/>'
    });
    </c:forEach>
    language.language = '${lang}';
    language.lang = '${lang}'

</script>
<div style="max-height: 100%; padding: 0">
    <div style="padding: 0; margin: 0; max-height: 100%">
        <table border="1" style="margin: 0; height: 100%; width: 0">
            <tr>
                <td id="languageBase">
                    <label for="language">
                        <fmt:message key="language"/>:
                    </label>
                    <select id="language" v-model="language">
                        <option v-for="l in languages" :value="l.id">
                            {{l.value}}
                        </option>
                    </select>
                    <button v-on:click="save" v-show="lang !== language">
                        <fmt:message key="button.save"/>
                    </button>
                </td>
                <td>
                    <fmt:message key="interests"/>
                </td>
                <td rowspan="2">
                    <img src="${context}/qr?t=${uid}" alt="">
                </td>
            </tr>
            <tr>
                <td valign="top">
                    <jsp:include page="personalData.jsp"/>
                </td>
                <td valign="top" rowspan="4">
                    <jsp:include page="interest.jsp"/>
                </td>

            </tr>
            <tr>
                <td>
                    <jsp:include page="workerOffice.jsp"/>
                    <jsp:include page="contacts.jsp"/>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <button onclick="loadModal('${changePassword}')">
                        <fmt:message key="password.change"/>
                    </button>
                </td>
            </tr>
            <tr>
                <td style="height: 100%">
                    &nbsp;
                </td>
            </tr>
        </table>
    </div>
</div>


</html>