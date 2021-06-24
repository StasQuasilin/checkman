<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 07.10.2019
  Time: 22:58
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/warehousing/warehousingEdit.vue"></script>
<script>

  edit.api.save = '${save}';
  edit.document = ${plan.id};
  edit.shipper = ${plan.shipper.id};
  <c:if test="${plan.weight.netto > 0}">
  edit.netto = Math.round((${plan.weight.netto}) * 100) / 100;
  </c:if>

  var r = {};
  <c:forEach items="${usedStorages}" var="use">
  if (!r[${use.id}]){
    r[${use.id}]=':)';
    edit.relations.push({
      id:${use.id},
      storage:${use.storage.id},
      shipper:${use.shipper.id},
      amount:${use.amount}
    });
  }
  </c:forEach>
  <c:forEach items="${storages}" var="storage">
  edit.storages.push({
    id:${storage.storage.id},
    name:'${storage.storage.name}'
  });
  </c:forEach>
  <c:forEach items="${shippers}" var="shipper">
  edit.shippers.push({
    id:${shipper.id},
    name:'${shipper.value}'
  });
  </c:forEach>

</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<html>
  <div id="editor" class="editor">
    <table width="100%">
      <tr>
        <td>
          <fmt:message key="date"/>
        </td>
        <td>
          <fmt:formatDate value="${plan.date}" pattern="dd.MM.yyyy"/>
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="deal.organisation"/>
        </td>
        <td>
          ${plan.counterparty.value}
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="weight.net"/>
        </td>
        <td>
          {{(netto).toLocaleString()}}
          <%--&nbsp;${plan.deal.unit.name}--%>
        </td>
      </tr>
      <tr v-for="(relation, key) in relations">
        <td>
          <span class="mini-close" style="font-size: 10pt" v-on:click="remove(key)" v-if="relations.length > 1">
            -
          </span>
          <span v-else>
            &nbsp;
          </span>
          <select v-model="relation.storage">
            <option v-for="storage in storages" :value="storage.id">
              {{storage.name}}
            </option>
          </select>
        </td>
        <td>
          <select v-model="relation.shipper">
            <option v-for="shipper in shippers" :value="shipper.id">
              {{shipper.name}}
            </option>
          </select>
          <input type="number" step="0.01" v-model.number="relation.amount" onfocus="this.select()">
        </td>
      </tr>
      <tr>
        <td>
          <b>
            <fmt:message key="amount.total"/>:
          </b>
        </td>
        <td align="right">
          {{total().toLocaleString()}} {{different()}}
        </td>
      </tr>
      <tr>
        <td colspan="2" align="right">
          <span class="mini-close" v-on:click="add()" style="font-size: 10pt">
            + <fmt:message key="button.add"/>
          </span>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <button onclick="closeModal()">
            <fmt:message key="button.close"/>
          </button>
          <button v-on:click="save">
            <fmt:message key="button.save"/>
          </button>
        </td>
      </tr>
    </table>
  </div>
</html>
