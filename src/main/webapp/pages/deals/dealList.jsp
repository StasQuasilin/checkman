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
    list.api.edit = '${edit}';
    list.api.show = '${show}';
    list.attributes['type'] = '${type}';
    subscribe('${subscribe}', function(a){
      list.handler(a);
    });
    stopContent = function(){
      unSubscribe('${subscribe}');
    }
  </script>
  <div id="container-header" class="container-header">
    <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
  </div>
  <div id="container" >
    <transition-group name="flip-list" tag="div" class="container" >
      <div v-for="(value, key) in getItems()" :key="value.item.id" :id="value.item.id"
           class="container-item" :class="'container-item-' + new Date(value.item.date).getDay()"
           v-on:click="show(value.item.id)"
           v-on:dblclick=""
           v-on:click.right="contextMenu(value.item.id)">
        <div style="display: table-cell; border-right: solid gray 1.2pt; padding: 2pt 4pt; vertical-align: middle" >
          <span>
            {{new Date(value.item.date).toLocaleDateString()}}
            <template v-if="value.item.date !== value.item.date_to">
              <br>{{new Date(value.item.date_to).toLocaleDateString()}}
            </template>
          </span>
        </div>
        <div style="width: 87%; display: table-cell; width: 100%">
          <div class="row upper-row">
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
              {{value.item.unit}}
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
              {{(value.item.done).toLocaleString()}}
              <%--{{value.item.unit}}--%>
            </b>
            ( {{(value.item.done / value.item.quantity * 100).toLocaleString() + ' %'}} ),
            {{(value.item.quantity-value.item.done).toLocaleString()}} <fmt:message key="deal.leave"/>
          </span>
          </div>
        </div>
      </div>
    </transition-group>

    <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
      <div v-bind:style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
        <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.edit"/> </div>
        <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.copy"/></div>
        <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${delete}')"> <fmt:message key="menu.delete"/></div>
        <div class="custom-data-list-item"><fmt:message key="menu.archive"/></div>
      </div>
    </div>
  </div>
</html>
