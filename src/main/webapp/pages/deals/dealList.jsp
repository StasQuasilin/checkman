<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/DataContainer.css?v=${now}">
<script src="${context}/vue2/baseList.vue?v=${now}"></script>
<script src="${context}/vue2/contextMenuView.vue?v=${now}"></script>
<script src="${context}/vue2/deals/dealList.vue?v=${now}"></script>

  <script>
    dealList.api.edit = '${edit}';
    dealList.api.show = '${show}';
    dealList.limit = ${limit};
    dealList.attributes['type'] = '${type}';
    subscribe('${subscribe}', function(a){
      dealList.handle(a);
    });
    stopContent = function(){
      unSubscribe('${subscribe}');
    };
    dealList.menuItems.push({
      title:'<fmt:message key="edit"/>',
      onClick:function (itemId) {
        loadModal('${edit}', {id:itemId});
      }
    });
    dealList.menuItems.push({
      title:'<fmt:message key="menu.copy"/>',
      onClick:function (itemId) {
        loadModal('${edit}', {copy:itemId});
      }
    });
    dealList.menuItems.push({
      title:'<fmt:message key="menu.delete"/>',
      onClick:function (itemId) {
        loadModal('${delete}', {id:itemId});
      }
    })
  </script>
  <div id="container-header" class="container-header">
    <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
  </div>
  <div id="dealList" >
    <transition-group name="flip-list" tag="div" class="container" >
      <div v-for="(item, key) in getItems()" :key="item.id" :id="item.id"
           class="container-item" :class="'container-item-' + new Date(item.date).getDay()"
           v-on:click="show(item.id)" ondblclick="" v-on:click.right="showMenu(item)">
        <div style="display: table-cell; border-right: solid gray 1.2pt; padding: 2pt 4pt; vertical-align: middle" >
          <span style="font-size: 8pt">
            <span v-if="item.products.length > 0" style="color: green">
              &checkmark;
            </span>
            ID:{{item.id}}
          </span><br>
          <template v-if="item.number">
            <span>
              № {{item.number}}
            </span>
          </template>
          <span v-if="item.date">
            {{new Date(item.date).toLocaleDateString()}}
            <template v-if="item.to && item.date !== item.to">
              <br>{{new Date(item.to).toLocaleDateString()}}
            </template>
          </span>
        </div>
        <div style="width: 87%; display: table-cell; width: 100%">
          <div class="row upper-row">
          <span style="width: 35%">
            <fmt:message key="deal.organisation"/>:
            <b v-if="item.organisation" class="secure">
              {{item.organisation.value}}
            </b>
            <b v-else>
              <fmt:message key="unknown"/>
            </b>
          </span>
            <span style="float: right">
              <fmt:message key="deal.product"/>:
              <template v-if="item.products.length > 0">
                <span v-for="product in item.products">
                  <b>
                    {{product.product.name}},
                  </b>
                  <b v-if="item.products.length < 3">
                    {{product.quantity.toLocaleString()}}
                    {{product.unit.name}}
                  </b>
                  <span v-if="item.products.length < 2 && product.price > 0">
                    <fmt:message key="deal.price"/>:
                    {{(product.price).toLocaleString()}}
                  </span>
                  <fmt:message key="deal.from"/>:
                    <b class="secure">
                      {{product.shipper.name}}
                    </b>
                  </span>
              </template>
              <template v-else>
                <b>
                  {{item.product.name}},
                </b>
                <span v-if="item.quantity > 0">
                  <b>
                    {{item.quantity}}
                    {{item.unit.name}}
                  </b>
                </span>
                <span v-if="item.price > 0">
                  <fmt:message key="deal.price"/>:
                    <b class="secure">
                      {{(item.price).toLocaleString()}}
                    </b>
                </span>
                <span style="width: 12%">
                <fmt:message key="deal.from"/>:
                  <b class="secure">
                    {{item.shipper.name}}
                  </b>
                </span>
              </template>
            </span>

          </div>
          <div class="row" style="font-size: 10pt">
          <span>
            <fmt:message key="deal.done"/>:
            <b>
              {{(item.complete).toLocaleString()}} / {{(item.quantity).toLocaleString()}}
              {{item.unit.name}}
            </b>
            <span v-if="item.quantity > 0">
              ( {{(item.complete / item.quantity * 100).toLocaleString() + ' %'}} )
              <span v-if="item.quantity>item.complete">
                {{(item.quantity-item.complete).toLocaleString()}} {{item.unit.name}} <fmt:message key="deal.leave"/>
              </span>
            </span>

          </span>
          <span style="float: right">
            <fmt:message key="deal.manager"/>:
            <b class="secure">
              {{item.create.creator.person.value}}
            </b>
          </span>
          </div>
        </div>
      </div>
    </transition-group>
  <context-menu ref="contextMenu" :menu="menu" :items="menuItems"></context-menu>
  </div>
</html>
