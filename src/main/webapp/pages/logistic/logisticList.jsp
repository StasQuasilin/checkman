<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <script src="${context}/vue/logisticList.js"></script>
  <script>
  </script>
  <div id="logistic">
    <div v-for="(value, key) in items">
      <div>
        {{key}}
      </div>
    </div>
  </div>
</html>
