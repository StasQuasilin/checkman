<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
<link rel="stylesheet" href="${context}/css/TransportList.css">

<script>
    list.api.update = '${update}';
    list.api.edit = '${show}';
    list.types['buy'] = '<fmt:message key="_buy"/>';
    list.types['sell'] = '<fmt:message key="_sell"/>';
    list.customers['szpt'] = '<fmt:message key="szpt"/>';
    list.customers['contragent'] = '<fmt:message key="contragent"/>';
</script>
<transition-group
        name="flip-list" tag="div"
        class="container" id="container">
    <div v-for="(value, key) in getItems()"
         :key="value.item.id" :id="value.item.id"
         class="container-item" :class="'container-item-' + new Date(value.item.date).getDay()" v-on:click="show(value.item.id)">
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
          <fmt:message key="deal.from"/>
          <b>
              {{value.item.realisation}}
          </b>
        </span>
        </div>
        <div class="lower-row">
            <div style="display: inline-block; font-size: 10pt; width: 14em">
                <div>
                    <fmt:message key="transportation.time.in"/>:
            <span v-if="value.item.transportation.timeIn.time">
              {{new Date(value.item.transportation.timeIn.time).toLocaleTimeString()}}
            </span>
            <span v-else>
              --:--:--
            </span>
                </div>
                <div>
                    <fmt:message key="transportation.time.out"/>:
            <span v-if="value.item.transportation.timeOut.time">
              {{new Date(value.item.transportation.timeOut.time).toLocaleTimeString()}}
            </span>
            <span v-else>
              --:--:--
            </span>
                </div>
            </div>
            <div style="display: inline-block; font-size: 10pt">
                <div>
            <span style="width: 5em">
              <fmt:message key="transportation.automobile"/>:
            </span>
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
            <span style="width: 5em">
              <fmt:message key="transportation.driver"/>:
            </span>
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
