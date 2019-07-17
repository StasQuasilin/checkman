<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <script src="${context}/vue/dataList.vue"></script>
  <link rel="stylesheet" href="${context}/css/DataContainer.css">
   <script>
     list.api.edit = '${show}';
     <c:forEach items="${subscribe}" var="s">
     subscribe('${s}', function(a){
       list.handler(a);
     });
     </c:forEach>
     stopContent = function(){
       <c:forEach items="${subscribe}" var="s">
       subscribe('${s}', function(a){
         unSubscribe('${s}');
       });
       </c:forEach>
     }
  </script>
  <transition-group  name="flip-list" tag="div" class="container" id="container">
      <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
           class="container-item" :class="'container-item-' + new Date(value.item.date).getDay()" v-on:click="edit(value.item.id)">
        <div class="upper-row">
          <span>
            {{new Date(value.item.date).toLocaleDateString()}}
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
            <fmt:message key="deal.from"/>
            <b>
              {{value.item.realisation}}
            </b>
          </span>
        </div>
        <div class="lower-row">
          <div style="display: inline-block; font-size: 10pt; width: 24em">
            <div>
              <fmt:message key="transportation.time.in"/>:
              <span v-if="value.item.transportation.timeIn.time">
                {{new Date(value.item.transportation.timeIn.time).toLocaleTimeString()}}
              </span>
            </div>
            <div>
              <fmt:message key="transportation.time.out"/>:
              <span v-if="value.item.transportation.timeOut.time">
                {{new Date(value.item.transportation.timeOut.time).toLocaleTimeString()}}
              </span>
            </div>
          </div>
          <div style="display: inline-block; font-size: 10pt">
            <div>
              <fmt:message key="transportation.automobile"/>:
              <span>
                <template v-if="value.item.transportation.vehicle.id">
                  {{value.item.transportation.vehicle.model}}
                  <span class="vehicle-number">
                    {{value.item.transportation.vehicle.number}}
                  </span>
                  <span v-if="value.item.transportation.vehicle.trailer" class="vehicle-number">
                    {{value.item.transportation.vehicle.trailer}}
                  </span>
                </template>
                <span v-else="value.item.transportation.vehicle.id">
                  <fmt:message key="no.data"/>
                </span>
              </span>
            </div>
            <div>
              <fmt:message key="transportation.driver"/>:
              <template v-if="value.item.transportation.driver.id">
                {{value.item.transportation.driver.person.value}}
              </template>
              <template v-else>
                <fmt:message key="no.data"/>
              </template>
            </div>
          </div>
        </div>
      </div>
  </transition-group>
</html>
