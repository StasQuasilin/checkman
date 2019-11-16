<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 16.11.2019
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
   var collapse = new Vue({
     el:'#collapse',
     data:{
       api:{},
       drivers:[]
     },
     methods:{
       collapseIt:function(){
         PostApi(this.api.collapse, {drivers:this.drivers}, function(a){
           if(a.status === 'success'){
             closeModal();
             location.reload();
           }
         })
       }
     }
   });
  <c:forEach items="${drivers}" var="driver">
   collapse.drivers.push({
     id:'${driver.id}',
     value:'${driver.person.surname} ${driver.person.forename} ${driver.person.patronymic}',
     license:'${driver.license}',
     <c:if test="${not empty driver.vehicle}">
     vehicle:{
       id:'${driver.vehicle.id}',
       model:'${driver.vehicle.model}',
       number:'${driver.vehicle.number}',
       trailer:'${driver.vehicle.trailerNumber}'
     }
     </c:if>
   });
   collapse.api.collapse = '${save}';
   </c:forEach>
</script>
<table id="collapse">
  <tr>
    <td>
      <div>
        <div v-for="driver in drivers" style="border: dotted gray 1pt; margin: 1pt">
          <div>
            {{driver.value}}
          </div>
          <div v-if="driver.license" style="font-size: 10pt">
            <fmt:message key="driver.license"/>: {{driver.license}}
          </div>
          <div v-if="driver.vehicle">
            {{driver.vehicle.model}}
            {{driver.vehicle.number}}
            {{driver.vehicle.trailer}}
          </div>
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="collapseIt">
        <fmt:message key="collapse"/>
      </button>
    </td>
  </tr>
</table>
</html>
