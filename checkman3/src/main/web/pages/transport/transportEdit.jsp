<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 16.09.20
  Time: 10:21
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script type="application/javascript" src="${context}/vue/editor.vue"></script>
    <script type="application/javascript" src="${context}/vue/transportEdit.vue"></script>
    <script type="application/javascript">
<%--        transportEdit.api.save = '${save}';--%>
        transportEdit.object = ${transportation.toJson()}
    </script>
    <div id="transportEdit">
        <table>
            <tr>
                <td>
                    <fmt:message key="transportation.date"/>
                </td>
                <td>
                    {{new Date(object.date).toLocaleDateString()}}
                </td>
            </tr>
            <template v-for="doc in object.documents">

            </template>
        </table>
        {{object}}
    </div>
</html>
