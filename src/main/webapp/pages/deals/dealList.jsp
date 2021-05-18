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
          <div class="row upper-row" style="display: flex">
          <span style="width: 35%">
            <fmt:message key="deal.organisation"/>:
            <b v-if="item.organisation" class="secure">
              {{item.organisation.value}}
            </b>
            <b v-else>
              <fmt:message key="unknown"/>
            </b>
          </span>
            <div style="float: right; display: flex; flex-direction: row">
              <fmt:message key="deal.product"/>:
              <div style="display: flex; flex-direction: column">
                <span v-for="product in item.products">
                  <b>
                    !{{product.productName}},
                  </b>
                  <b v-if="item.products.length < 3">
                    {{product.quantity.toLocaleString()}}
                    {{product.unitName}}
                  </b>
                  <span v-if="item.products.length < 2 && product.price > 0">
                    <fmt:message key="deal.price"/>:
                    {{(product.price).toLocaleString()}}
                  </span>
                  <fmt:message key="deal.from"/>:
                    <b class="secure">
                      {{product.shipperName}}
                    </b>
                  </span>
              </div>
            </div>
          </div>
          <div class="row" style="font-size: 10pt">
          <span v-if="item.complete > 0 && item.target > 0">
            <fmt:message key="deal.done"/>:
            <b>
              {{(item.complete).toLocaleString()}} / {{(item.target).toLocaleString()}}
              ( {{(item.complete / item.target * 100).toLocaleString() + ' %'}} )
            </b>
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
