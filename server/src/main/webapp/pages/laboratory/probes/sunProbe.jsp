<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/probeEdit.vue"></script>
<script>
  editor.api.findManager = '${findManager}';
  editor.api.findOrganisation = '${findOrganisation}';
  editor.api.save = '${save}';
  <c:choose>
  <c:when test="${not empty probe}">
  editor.probe={
    id:${probe.id},
    humidity:${probe.analyses.humidity},
    soreness:${probe.analyses.soreness},
    oiliness:${probe.analyses.oiliness},
    oilImpurity:${probe.analyses.oilImpurity},
    acidValue:${probe.analyses.acidValue},
    manager:{
      id:-1,
      value:'${probe.manager}'
    },
    organisation:{
      id:-1,
      value:'${probe.organisation}'
    },
    creator:${probe.analyses.createTime.creator.id}
  };
  </c:when>
  <c:otherwise>
  editor.probe={
    humidity:0,
    soreness:0,
    oiliness:0,
    oilImpurity:0,
    acidValue:0,
    manager:{
      id:-1,
      value:''
    },
    organisation:{
      id:-1,
      value:''
    },
    creator:${worker.id}
  };
  </c:otherwise>
  </c:choose>
</script>
<div id="editor">
  <table class="editor" v-if="probe.manager">
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
          <input id="manager" v-model="probe.manager.value" v-on:keyup="findManager()"
                 onfocus="this.select()" autocomplete="off">
          <div class="custom-data-list" v-if="foundManagers.length > 0">
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
          <input id="organisation" v-model="probe.organisation.value" v-on:keyup="findOrganisation()"
                 onfocus="this.select()" autocomplete="off">
          <div class="custom-data-list" v-if="foundOrganisations.length > 0">
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
</div>
</html>
