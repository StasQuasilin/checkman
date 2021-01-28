<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <div style="padding: 8pt">
    <h2>
      <fmt:message key="archive.type.err"/>
    </h2>
    <h3>
      <fmt:message key="archive.type"/> '${type}'
    </h3>
  </div>

</html>
