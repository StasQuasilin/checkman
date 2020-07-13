<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title><fmt:message key="password.restore"/> </title>
    <link rel="stylesheet" href="${context}/css/login.css">
    <script src="${context}/js/Core.js"></script>
    <script>
      const context = '${context}';
    </script>
  </head>
  <body>
    <div class="wrapper" id="restorator">
      <div class="content">
        <table width="100%">
          <tr>
            <td>
              <div style="font-size: 22px">
                <fmt:message key="if.forgot"/>
              </div>
              <ul>
                <c:forEach items="${admins}" var="admin">
                  <li>
                    ${admin.person.value}
                    <c:forEach items="${admin.person.phones}" var="phone">
                      ${phone}
                    </c:forEach>
                  </li>
                </c:forEach>
              </ul>
            </td>
          </tr>
          <tr>
            <td align="center">
              <button onclick="location.href='${context}${signIn}'">
                <fmt:message key="button.back"/>
              </button>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </body>
</html>
