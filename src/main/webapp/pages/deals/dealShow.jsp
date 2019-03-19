<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<script>
  var closeAction;
  function onClose(action){
    closeAction = action;
  }
  function closeShow(){
    closeAction();
  }

</script>

<table border="0">
  <tr>
    <td valign="top">
      <table border="0">
        <tr>
          <td>
            <fmt:message key="deal.type"/>:
          </td>
          <td>
            <fmt:message key="${deal.type}"/>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="period"/>:
          </td>
          <td>
            <fmt:formatDate value="${deal.date}" pattern="dd.MM.yyyy"/>
            <c:if test="${deal.date ne deal.dateTo}">
              -<fmt:formatDate value="${deal.dateTo}" pattern="dd.MM.yyyy"/>
            </c:if>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.organisation"/>:
          </td>
          <td>
            ${deal.organisation.value}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.realisation"/>:
          </td>
          <td>
            ${deal.documentOrganisation.value}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.product"/>
          </td>
          <td>
            ${deal.product.name}
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.quantity"/>
          </td>
          <td>
            <fmt:formatNumber value="${deal.quantity}"/>
          </td>
        </tr>
        <tr>
          <td>
            <fmt:message key="deal.price"/>
          </td>
          <td>
            <fmt:formatNumber value="${deal.price}"/>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="center">
            <button><fmt:message key="button.edit"/> </button>
          </td>
        </tr>
      </table>
    </td>
    <td>
      <link rel="stylesheet" href="${context}/css/LoadPlan.css">
      <script src="${context}/vue/loadPlan.vue"></script>
      <script>
        <c:forEach items="${customers}" var="customer">
        plan.addCustomer('${customer}', '<fmt:message key="${customer}"/> ')
        </c:forEach>
        plan.deal = ${deal.id}
        plan.save_link = '${save_link}'
        plan.update_link = '${update_link}'
      </script>
      <div class="plan-wrapper" id="load_plan">
        <table border="0">
          <tr>
            <td colspan="4" align="center">
              <fmt:message key="load.plan"/>
              <button v-on:click="newPlan"><fmt:message key="button.add"/> </button>
            </td>
          </tr>
          <tr>
            <td>
              <c:set var="dropTitle"><fmt:message key="load.plan.drop.title"/> </c:set>
              <c:set var="dateTitle"><fmt:message key="load.date.title"/> </c:set>
            <span title="${dateTitle}" class="table-header" style="width: 8em">
              <fmt:message key="date"/>
            </span>
            </td>
            <td>
              <c:set var="planTitle"><fmt:message key="load.plan.title"/> </c:set>
            <span title="${planTitle}" class="table-header" style="width: 6em">
              <fmt:message key="deal.plan"/>
            </span>
            </td>
            <td>
              <c:set var="customerTitle"><fmt:message key="load.customer.title"/> </c:set>
            <span title="${customerTitle}" class="table-header" style="width: 8em">
              <fmt:message key="transport.customer"/>
            </span>
            </td>
            <td>
              <c:set var="factTitle"><fmt:message key="load.fact.title"/> </c:set>
            <span title="${factTitle}" class="table-header" style="width: 6em">
              <fmt:message key="fact"/>
            </span>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <div class="plan-container">
                <div v-for="plan in plan_list" class="plan-item">
                  <div>
                    <button title="${dropTitle}" class="minus-button">&times;</button>
                    <input v-model="plan.date" id="date" name="date" title="${dateTitle}" readonly style="width: 7em">
                    <input v-model="plan.plan" type="number" title="${planTitle}" style="width: 6em" min="1">
                    <select v-model="plan.customer" title="${customerTitle}">
                      <option v-for="customer in customers" v-bind:value="customer.id">{{customer.name}}</option>
                    </select>
                    <span title="${factTitle}">{{plan.fact}}</span>
                  </div>
                  <div style="padding: 1pt">
                    <button v-if="plan.transportation.vehicle || plan.transportation.driver">
                      <fmt:message key="transport.insert.infortation"/>
                    </button>
                    <template v-else>
                      <input>
                      <button class="minus-button">&times;</button>
                      <input>
                      <button class="minus-button">&times;</button>
                    </template>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <fmt:message key="totle.plan"/>:
              <span id="total_plan">{{total_plan}}</span>
              /
              <span>${deal.quantity}</span>
            </td>
          </tr>
          <tr>
            <td colspan="4">
              <fmt:message key="totle.fact"/>:
              <span id="total_fact">{{total_fact}}</span>
              /
              <span>${deal.quantity}</span>
            </td>
          </tr>
        </table>

      </div>
      <script>
        plan.loadPlan()
      </script>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeShow()"><fmt:message key="button.cancel"/> </button>

      <button onclick="plan.save();"><fmt:message key="button.save"/> </button>
    </td>
  </tr>
</table>

</html>
