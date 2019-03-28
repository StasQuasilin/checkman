<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: quasilin
  Date: 28.03.2019
  Time: 7:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <c:forEach items="${subdivisions}" var="s">
    <div>
      <input id="subdivision${s.id}" type="radio" name="subdivision"/>
      <label for="subdivision${s.id}">
          ${s.name}
      </label>
    </div>
  </c:forEach>

</html>
