<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
  <script src="${context}/vue/logisticList.js"></script>
  <script>
    logistic.setUrls('${updateLink}', '${saveLink}')

    <c:forEach items="${dealTypes}" var="type">
    logistic.addType('${type}', '<fmt:message key="${type}"/>')
    </c:forEach>
    function stopContent(){
      logistic.stop()
    }

  </script>
  <div id="logistic">
    <div v-for="(value, key) in items" class="container-item" v-bind:class="value.className">
      <div>
        <span>
          {{new Date(value.item.date).toLocaleDateString()}}
        </span>
        <span>
          {{types[value.item.type]}}
        </span>
        <span>
          <fmt:message key="deal.organisation"/>:
          <b>
            {{value.item.organisation.value}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.product"/>:
          <b>
            {{value.item.product.name}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.quantity"/>:
          <b>
            {{(value.item.quantity).toLocaleString()}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.from"/>:
          <b>
            {{value.item.realisation}}
          </b>
        </span>
      </div>
      <div>
        <fmt:message key="transportation.automobile"/>:
        <fmt:message key="transportation.driver"/>:
      </div>
    </div>
  </div>
</html>
