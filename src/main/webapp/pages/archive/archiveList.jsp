<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script src="${context}/vue/archive/dealArchive.vue"></script>
<script>

</script>
<div id="archive">
    <div v-for="(value, key) in items" class="container-item" :class="rowName(value.date)" style="padding: 0 4pt">
        <div style="display: inline-block">
      <span>
        {{new Date(value.date).toLocaleDateString()}}<br>
        <template v-if="value.date !== value.date_to">
            {{new Date(value.date_to).toLocaleDateString()}}
        </template>
      </span>
        </div>
        <div style="width: 87%; display: inline-block">
            <div class="row">
        <span style="width: 35%">
          <fmt:message key="deal.organisation"/>:
          <b>
              {{value.organisation.value}}
          </b>
        </span>
        <span style="width: 12%">
          <fmt:message key="deal.from"/>:
          <b>
              {{value.visibility}}
          </b>
        </span>
        <span style="width: 30%">
          <fmt:message key="deal.manager"/>:
          <b>
              {{value.creator.person.value}}
          </b>
        </span>
            </div>
            <div class="row" style="font-size: 10pt">
        <span>
          <fmt:message key="deal.product"/>:
          <b>
              {{value.product.name}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.quantity"/>:
          <b>
              {{value.quantity}}
              <%--{{value.item.unit}}--%>
          </b>
        </span>
        <span>
          <fmt:message key="deal.price"/>:
          <b>
              {{(value.price).toLocaleString()}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.done"/>:
          <b>
              {{(value.done).toLocaleString()}}
              <%--{{value.item.unit}}--%>
          </b>
          ( {{(value.done / value.quantity * 100).toLocaleString() + ' %'}} ),
          {{(value.quantity-value.done).toLocaleString()}} <fmt:message key="deal.leave"/>
        </span>
        </div>
    </div>
</div>
</html>