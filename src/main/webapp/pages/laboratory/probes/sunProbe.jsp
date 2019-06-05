<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/probeEdit.js"></script>
<script>
  editor.api.findManagerAPI = '${findManager}';
  editor.api.findOrganisationAPI = '${findOrganisation}';
  editor.api.saveAPI = '${saveApi}';
  <c:choose>
  <c:when test="${not empty probe}">
  editor.probe={
    id:${probe.id},
    humidity:${probe.analyses.humidity},
    soreness:${probe.analyses.soreness},
    oiliness:${probe.analyses.oiliness},
    oilImpurity:${probe.analyses.oilImpurity},
    acidValue:${probe.analyses.acidValue},
    organisation:${probe.organisation.value},
    creator:${probe.analyses.createTime.creator.id}
  };
  editor.organisationInput=${probe.organisation.value};
  </c:when>
  <c:otherwise>
  editor.probe={
    humidity:0,
    soreness:0,
    oiliness:0,
    oilImpurity:0,
    acidValue:0,
    manager:-1,
    organisation:'',
    creator:${worker.id}
  };
  </c:otherwise>
  </c:choose>
  <c:forEach items="${laborants}" var="l">
  editor.laborants.push({
    id:${l.id},
    value:'${l.person.value}'
  });
  </c:forEach>
</script>
<table id="editor" class="editor">
  <tr>
    <td>
      <label for="humidity">
        <fmt:message key="sun.humidity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="humidity" type="number" step="0.01" autocomplete="off" v-model="probe.humidity">
    </td>
  </tr>
  <tr>
    <td>
      <label for="soreness">
        <fmt:message key="sun.soreness"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="soreness" type="number" step="0.01" autocomplete="off" v-model="probe.soreness">
    </td>
  </tr>
  <tr>
    <td>
      <label for="oiliness">
        <fmt:message key="sun.oiliness"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="oiliness" type="number" step="0.01" autocomplete="off" v-model="probe.oiliness">
    </td>
  </tr>
  <tr>
    <td>
      <label for="oilImpurity">
        <fmt:message key="sun.oil.impurity"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="oilImpurity" type="number" step="0.01" autocomplete="off" v-model="probe.oilImpurity">
    </td>
  </tr>
  <tr>
    <td>
      <label for="acidValue">
        <fmt:message key="sun.acid.value"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="acidValue" type="number" step="0.01" autocomplete="off" v-model="probe.acidValue">
    </td>
  </tr>
  <tr>
    <td>
      <label for="manager">
        <fmt:message key="deal.manager"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <div>
        <input id="manager" v-model="managerInput" v-on:keyup="findManager()" autocomplete="off">
        <div class="custom-data-list">
          <div class="custom-data-list-item" v-for="manager in foundManagers" v-on:click="setManager(manager)">
            {{manager.person.value}}
          </div>
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <label for="organisation">
        <fmt:message key="deal.organisation"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <div>
        <input id="organisation" v-model="probe.organisation" v-on:keyup="findOrganisation()" autocomplete="off">
        <div class="custom-data-list">
          <div class="custom-data-list-item" v-for="organisation in foundOrganisations" v-on:click="setOrganisation(organisation)">
            {{organisation.value}}
          </div>
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
