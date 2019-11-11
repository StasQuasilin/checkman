<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/dataList.vue"></script>
<link rel="stylesheet" href="${context}/css/DataContainer.css">
  <script>
    list.api.edit = '${edit}';
    list.api.show = '${show}';
    list.limit = ${limit};
    list.types['buy'] = '<fmt:message key="buy"/>'
    list.types['sell'] = '<fmt:message key="sell"/>'
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
           class="container-item" :class="'container-item-' + new Date(value.item.from).getDay()"
           v-on:click="show(value.item.id)"
           ondblclick=""
           v-on:click.right="contextMenu(value.item)">
        <div style="display: table-cell; border-right: solid gray 1.2pt; padding: 2pt 4pt; vertical-align: middle" >
          <span v-if="value.item.from">
            {{new Date(value.item.from).toLocaleDateString()}}
            <template v-if="value.item.from !== value.item.to">
              <br>{{new Date(value.item.to).toLocaleDateString()}}
            </template>
          </span>
        </div>
        <div style="width: 87%; display: table-cell; width: 100%">
          <div class="middle-row">
            <span style="width: 35%">
              <fmt:message key="deal.organisation"/>:
              <b>
                {{value.item.counterparty.value}}
              </b>
            </span>
            <div style="display: inline-block; margin: 0 4pt" v-for="product in value.item.products">
              <div>
                {{types[product.type]}}
                {{product.product.name}}
              </div>
              <div style="font-size: 10pt">
                <fmt:message key="seal.quantity"/>
                {{product.done}}/{{product.amount}}
              </div>
              <div style="font-size: 10pt">
                <fmt:message key="deal.price"/>:
                {{product.price.toLocaleString()}},
                {{product.shipper.name}}
              </div>
            </div>
          <%--<span>--%>
            <%--<fmt:message key="deal.product"/>:--%>
            <%--<b>--%>
              <%--{{value.item.product.name}}--%>
            <%--</b>--%>
          <%--</span>--%>
          <%--<span>--%>
            <%--<fmt:message key="deal.quantity"/>:--%>
            <%--<b>--%>
              <%--{{value.item.quantity}}--%>
              <%--{{value.item.unit}}--%>
            <%--</b>--%>
          <%--</span>--%>
          <%--<span>--%>
            <%--<fmt:message key="deal.price"/>:--%>
            <%--<b>--%>
              <%--{{(value.item.price).toLocaleString()}}--%>
            <%--</b>--%>
          <%--</span>--%>
          <%--<span style="width: 12%">--%>
            <%--<fmt:message key="deal.from"/>:--%>
            <%--<b>--%>
              <%--{{value.item.visibility}}--%>
            <%--</b>--%>
          <%--</span>--%>
          </div>
          <%--<div class="row" style="font-size: 10pt">--%>
          <%--&lt;%&ndash;<span>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<fmt:message key="deal.done"/>:&ndash;%&gt;--%>
            <%--&lt;%&ndash;<b>&ndash;%&gt;--%>
              <%--&lt;%&ndash;{{(value.item.complete).toLocaleString()}}&ndash;%&gt;--%>
              <%--&lt;%&ndash;{{value.item.unit}}&ndash;%&gt;--%>
            <%--&lt;%&ndash;</b>&ndash;%&gt;--%>
            <%--&lt;%&ndash;( {{(value.item.complete / value.item.quantity * 100).toLocaleString() + ' %'}} ),&ndash;%&gt;--%>
            <%--&lt;%&ndash;{{(value.item.quantity-value.item.complete).toLocaleString()}} {{value.item.unit}} <fmt:message key="deal.leave"/>&ndash;%&gt;--%>
          <%--&lt;%&ndash;</span>&ndash;%&gt;--%>
          <%--<span style="float: right">--%>
            <%--<fmt:message key="deal.manager"/>:--%>
            <%--&lt;%&ndash;<b>&ndash;%&gt;--%>
              <%--&lt;%&ndash;{{value.item.creator.person.value}}&ndash;%&gt;--%>
            <%--&lt;%&ndash;</b>&ndash;%&gt;--%>
          <%--</span>--%>
          <%--</div>--%>
        </div>
      </div>
    </transition-group>
    <div v-show="menu.show" v-on:click="closeMenu" class="menu-wrapper">
      <div ref="contextMenu" :style="{ top: menu.y + 'px', left:menu.x + 'px'}" class="context-menu">
        <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.edit"/></div>
        <div class="custom-data-list-item" :copy="menu.id" onclick="editableModal('${edit}')"><fmt:message key="menu.copy"/></div>
        <div class="custom-data-list-item" :id="menu.id" onclick="editableModal('${delete}')"> <fmt:message key="menu.cancel"/></div>
        <div class="custom-data-list-item" v-if="menu.item.done"><fmt:message key="menu.archive"/></div>
      </div>
    </div>
  </div>
</html>
