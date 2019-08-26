<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div class="fast-container" id="fast" v-if="showIt()">
        <table border="0" style="width: 100%; height: 100%">
            <tr>
                <td>
                    <a>Messages</a>
                </td>
            </tr>
            <tr>
                <td height="100%">
                    <div id="fast-content"></div>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript" src="${context}/vue/fastContainer.vue"></script>
</html>
