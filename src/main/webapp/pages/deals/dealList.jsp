<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="${context}/vue/dataList.js"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<script>
  deamon.setUrls('${updateLink}', '${showLink}')
  function stopContent(){
    deamon.stop();
  }
</script>
<div id="container-header" style="display: inline">
  <button onclick="loadModal('${editLink}')"><fmt:message key="button.add"/> </button>
</div>
<div class="container" id="container" >
  <div v-for="(value, key) in items" v-bind:key="key" v-bind:id="value.item.id"
       class="container-item" v-bind:class="value.className" v-on:click="show(value.item.id)">
    <div style="display: inline-block">
      <span>
        {{new Date(value.item.date).toLocaleDateString()}}<br>
        <template v-if="value.item.date !== value.item.date_to">
          {{new Date(value.item.date_to).toLocaleDateString()}}
        </template>
      </span>
    </div>
    <div style="width: 87%; display: inline-block">
      <div class="row">
        <span style="width: 35%">
          <fmt:message key="deal.organisation"/>:
          <b>
            {{value.item.organisation.value}}
          </b>
        </span>
        <span style="width: 12%">
          <fmt:message key="deal.from"/>:
          <b>
            {{value.item.visibility}}
          </b>
        </span>
        <span style="width: 30%">
          <fmt:message key="deal.manager"/>:
          <b>
          {{value.item.creator.person.value}}
          </b>
        </span>
      </div>
      <div class="row" style="font-size: 10pt">
        <span>
          <fmt:message key="deal.product"/>:
          <b>
          {{value.item.product.name}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.quantity"/>:
          <b>
          {{value.item.quantity}}
            <%--{{value.item.unit}}--%>
          </b>
        </span>
        <span>
          <fmt:message key="deal.price"/>:
          <b>
          {{(value.item.price).toLocaleString()}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.done"/>:
          <b>
          {{value.item.done}}
            <%--{{value.item.unit}}--%>
          </b>
          ( {{(value.item.done / value.item.quantity * 100).toLocaleString() + ' %'}} ),
          {{value.item.quantity-value.item.done}} <fmt:message key="deal.leave"/>
        </span>
      </div>
    </div>

  </div>
</div>
</html>
