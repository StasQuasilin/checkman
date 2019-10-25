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
    organoleptic:${probe.analyses.organoleptic},
    color:${probe.analyses.color},
    acidValue:${probe.analyses.acidValue},
    peroxideValue:${probe.analyses.peroxideValue},
    phosphorus:${probe.analyses.phosphorus},
    humidity:${probe.analyses.humidity},
    soap:${probe.analyses.soap},
    wax:${probe.analyses.wax},
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
    organoleptic:false,
    color:0,
    acidValue:0,
    peroxideValue:0,
    phosphorus:0,
    humidity:0,
    soap:false,
    wax:0,
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
        <fmt:message key="oil.organoleptic"/>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="organoleptic" type="checkbox" v-model="probe.organoleptic">
        <label for="organoleptic">
        <span v-if="probe.organoleptic">
          <fmt:message key="oil.organoleptic.match"/>
        </span>
        <span v-else>
          <fmt:message key="oil.organoleptic.doesn't.match"/>
        </span>
        </label>
      </td>
    </tr>
    <tr>
      <td>
        <label for="color">
          <fmt:message key="oil.color.value"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="color" type="number" step="1" autocomplete="off" v-model="probe.color">
      </td>
    </tr>
    <tr>
      <td>
        <label for="acid">
          <fmt:message key="sun.acid.value"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="acid" type="number" step="0.01" autocomplete="off" v-model="probe.acidValue">
      </td>
    </tr>
    <tr>
      <td>
        <label for="peroxide">
          <fmt:message key="oil.peroxide"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="peroxide" type="number" step="0.01" autocomplete="off" v-model="probe.peroxideValue">
      </td>
    </tr>
    <tr>
      <td>
        <label for="phosphorus">
          <fmt:message key="oil.phosphorus"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="phosphorus" type="number" step="0.01" autocomplete="off" v-model="probe.phosphorus">
      </td>
    </tr>
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
        <label for="soap">
          <fmt:message key="oil.soap"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="soap" type="checkbox" step="0.01" autocomplete="off" v-model="probe.soap">
      </td>
    </tr>
    <tr>
      <td>
        <label for="wax">
          <fmt:message key="oil.wax"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="wax" type="number" step="0.01" autocomplete="off" v-model="probe.wax">
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
