<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <script src="${context}/vue/dataList.vue"></script>
  <link rel="stylesheet" href="${context}/css/DataContainer.css">
  <link rel="stylesheet" href="${context}/css/TransportList.css">

  <script>
    list.api.edit = '${edit}';
    list.types['buy'] = '<fmt:message key="_buy"/>';
    list.types['sell'] = '<fmt:message key="_sell"/>';
    list.sort = function(){
      list.items.sort(function(a, b){
        if (a.item.date === b.item.date) {
          let ai = a.item.timeIn.time && !a.item.timeOut.time;
          let bi = b.item.timeIn.time && !b.item.timeOut.time;
          if (ai && !bi || !a.item.done && b.item.done){
            return -1
          }
          if (!ai && bi || a.item.done && !b.item.done){
            return 1;
          }

        }

        return new Date(a.item.date) - new Date(b.item.date);
      })
    };
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
  <div id="container-header" class="container-header">
    <link rel="stylesheet" href="${context}/css/drop-menu.css">
    <c:if test="${role eq 'admin'}">
      <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="document.print"/>&nbsp;&#9660;</a>
        <div class="drop-menu-content">
          <div class="drop-menu-item" onclick="loadModal('${printOnTerritory}')">
            <fmt:message key="transport.on.territory"/>
          </div>
          <div class="drop-menu-item" onclick="loadModal('${printIncome}')">
            <fmt:message key="transport.income"/>
          </div>
        </div>
      </div>
    </c:if>
  </div>
  <transition-group name="flip-list" tag="div" class="container" id="container">
    <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
         class="container-item" style="position: relative"
         :class="['container-item-' + new Date(value.item.date).getDay()
         + ( value.item.weight.brutto > 0 && value.item.weight.tara > 0 ? '-done' : ''),
         { 'transport-loading': value.item.timeIn.id && !value.item.timeOut.id}]"
         v-on:click="edit(value.item.id)" v-on:dblclick="">
      <div class="upper-row">
        <span>
          {{new Date(value.item.date).toLocaleDateString()}}
        </span>
        <span style="width: 22em">
          <fmt:message key="deal.organisation"/>:
          <b>
            {{value.item.organisation.value}}
          </b>
        </span>
        <span>
          <fmt:message key="deal.product"/>:
          <b>
            {{(types[value.item.type]).toLowerCase(),}}
            {{value.item.product.name}}
          </b>
        </span>
          <span>
          <fmt:message key="deal.from"/>
          <b>
            {{value.item.shipper}},
          </b>
      </span>
      </div>
      <div class="middle-row">
        <div style="display: inline-block; font-size: 10pt; width: 14em">
          <div>
            <fmt:message key="transportation.time.in"/>:
            <span v-if="value.item.timeIn.time">
              {{new Date(value.item.timeIn.time).toLocaleTimeString().substring(0, 5)}}
            </span>
            <span v-else>
              --:--
            </span>
          </div>
          <div>
            <fmt:message key="transportation.time.out"/>:
            <span v-if="value.item.timeOut.time">
              {{new Date(value.item.timeOut.time).toLocaleTimeString().substring(0, 5)}}
            </span>
            <span v-else>
              --:--
            </span>
          </div>
        </div>
        <div style="display: inline-block; font-size: 10pt; width: 28em">
          <div>
            <span style="width: 5em">
              <fmt:message key="transportation.automobile"/>:
            </span>
            <span>
              <template v-if="value.item.vehicle.id">
                {{value.item.vehicle.model}}
                <span class="vehicle-number">
                  {{value.item.vehicle.number}}
                </span>
                <span v-if="value.item.vehicle.trailer" class="vehicle-number">
                  {{value.item.vehicle.trailer}}
                </span>
              </template>
              <span v-else>
                <fmt:message key="no.data"/>
              </span>
            </span>
          </div>
          <div>
            <span style="width: 5em">
              <fmt:message key="transportation.driver"/>:
            </span>
            <template v-if="value.item.driver.id">
              {{value.item.driver.person.value}}
            </template>
            <template v-else>
              <fmt:message key="no.data"/>
            </template>
          </div>
        </div>
        <div style="display: inline-block; font-size: 10pt;">
          <div v-if="value.item.weight.id">
            <span>
                Б:
            </span>
            <b>
              {{(value.item.weight.brutto).toLocaleString()}},
            </b>
              <span>
                  Т:
              </span>
            <b>
              {{(value.item.weight.tara).toLocaleString()}},
            </b>
            <div>
              <span>
                Н:
              </span>
              <b>
                {{value.item.weight.brutto > 0 && value.item.weight.tara > 0 ?
                (value.item.weight.brutto - value.item.weight.tara).toLocaleString() : 0}}
              </b>
            </div>

          </div>
        </div>
        <div style="display: inline-block; float: right">
          {{value.item.manager.person.value}}
        </div>
      </div>
      <div class="lower-row" v-if="value.item.notes.length > 0">
        <div style="display: inline-block; padding-left: 4pt" v-for="note in value.item.notes">
          <span v-if="note.creator.person">
            {{note.creator.person.value}}:
          </span>
          <b>
            {{note.note}}
          </b>
        </div>
      </div>
    </div>
  </transition-group>
</html>
