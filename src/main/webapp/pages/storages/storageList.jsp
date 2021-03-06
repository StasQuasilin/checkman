<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 05.11.2019
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <script>
    var list = new Vue({
      el:'#list',
      data:{
        api:{},
        items:{},
        year:'',
        month:'',
        week:'',
        mon:'',
        tue:'',
        wed:'',
        thu:'',
        fri:''
      },
      methods:{
        handler:function(a){
          console.log(a);
        },
        addProduct:function(product, storage){
          if (!this.items[product.id]){
            Vue.set(this.items, product.id, {
              product:product,
              open:true,
              values:{}
            });
          }
          Vue.set(this.items[product.id].values, storage.id, storage);
        },
        weekDay:function(day){
          switch (day){
            case 1:
                  return ''
          }
        },
        getItem:function(items, date, scale){

          let key;
          let header;

          if (scale == 'year'){
            header = this.year;
            key = date.getFullYear();
          } else if (scale == 'month') {
            header = this.month;
            key = date.toLocaleDateString().substring(3);
          } else if (scale == 'week'){
            header = this.week;
            key = date.toLocaleDateString().substring(0, 5);
          } else if (scale == 'day'){
            header = this.weekDay(date.getDay());
            key = date.toLocaleDateString().substring(0, 5);
          } else if (scale == 'detail'){
            header = '--';
            key = date.toLocaleDateString().substring(0, 5);
          }
          const self = this;
          if (!items[key]){
            Vue.set(items, key, {
              date:date,
              title:key,
              header:header,
              items:items,
              parent:null,
              begin:function(){
                let tms = self.getValues(this.items);
                let idx = 0;
                for (let i in tms){
                  if (tms.hasOwnProperty(i)){
                    if (tms[i] == this){
                      idx = i;
                      break;
                    }
                  }
                }
                if (idx > 0){
                  return tms[idx - 1].end();
                } else if (this.parent && this.parent.begin){
                  return this.parent.begin();
                }

                return 0;
              },
              plus:0,
              minus:0,
              end:function(){
                return this.begin() + this.plus + this.minus;
              },
              open:false,
              values:{}
            })
          }

          return items[key];
        },
        getParent:function(item, parent, key){
          if (item[key]){
            let p = this.getItem(parent.values, new Date(item[key]), key);
            p.open = true;
            return p;
          }
          return parent;
        },
        getStocks:function(date, storage, product){
          const self = this;
          PostApi(this.api.getStocks, {date : date, storage: storage, product: product}, function(a){
            for (let i in a){
              if (a.hasOwnProperty(i)) {
                let item = a[i];

                let parent = self.items[product].values[storage];
                parent = self.getParent(item, parent, 'year');
                parent = self.getParent(item, parent, 'month');
                parent = self.getParent(item, parent, 'week');

                let obj = self.getItem(parent.values, new Date(item.date), item.scale);
                obj.scale = item.scale;
                obj.parent = parent;
                if (item.amount > 0){
                  obj.plus += item.amount;
                } else {
                  obj.minus += item.amount;
                }
              }
            }
          })
        },
        getValues:function(item){
          let values = Object.values(item);
          values.sort(function(a, b){
            return a.date - b.date;
          });
          return values;
        },
        getDetails:function(item, storage, product){
          const self = this;
          PostApi(this.api.getDetails, {
            scale:item.scale,
            date:item.date.toISOString().substring(0, 10),
            storage:storage, product:product }, function(a){
            for (let i in a){
              if (a.hasOwnProperty(i)) {
                let item = a[i];
                let parent = self.items[product].values[storage];
                parent = self.getParent(item, parent, 'year');
                parent = self.getParent(item, parent, 'month');
                parent = self.getParent(item, parent, 'week');
                parent = self.getParent(item, parent, 'day');


                if (item.scale == 'detail'){
                  console.log(item);
                  Vue.set(parent.values, item.id, item);
                } else {
                  let obj = self.getItem(parent.values, new Date(item.date), item.scale);
                  obj.scale = item.scale;
                  obj.parent = parent;
                  if (item.amount > 0){
                    obj.plus += item.amount;
                  } else {
                    obj.minus += item.amount;
                  }
                }
              }
            }
          })
        }
      }
    });
    list.api.getStocks = '${getStocks}';
    list.api.getDetails = '${stockDetails}';
    list.year = '<fmt:message key="year"/>';
    list.month = '<fmt:message key="month"/>';
    list.week = '<fmt:message key="week"/>';
    list.day = '<fmt:message key="day"/>';
    <c:forEach items="${products}" var="product">
    product = {
      id:${product.key.id},
      name:'${product.key.name}'
    };
    <c:forEach items="${product.value}" var="storage">
    storage = {
      id:${storage.id},
      name:'${storage.name}',
      open:false,
      values:{}
    };
    list.addProduct(product, storage);
    </c:forEach>
    </c:forEach>
//    SUBSCRIBES
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
      list.handler(a);
    });
    </c:forEach>
  </script>
  <style>
    .row{
      display: initial;
      border-bottom: dashed black 1pt;
      font-size: 12pt;
    }
    .content{
      width: fit-content;
      padding: 4pt 2pt;
    }
    .content:hover{
      background-color: #ececec;
    }
    .year, .month, .week, .day{

    }
    .year{
      transform: translateX(8px);
      background-color: #c4e043;
    }
    .month{
      transform: translateX(16px);
      background-color: #a2e8b8;
    }
    .week{
      transform: translateX(24px);
      background-color: #b3d4fc;
    }
    .day{
      transform: translateX(32px);
    }
    .detail{
      transform: translateX(40px);
    }
    .date{
      display: inline-block;
    }
    .year .date{
      width: 172px;
    }
    .month .date{
      width: 164px;
    }
    .week .date{
      width: 156px;
    }
    .day .date{
      width: 148px;
    }
    .number{
      display: inline-block;
      width: 130px;
    }
    .plus{
      color: green;
    }
    .minus{
      color: orange;
    }
  </style>
  <link rel="stylesheet" href="${context}/css/TransportList.css">
  <div id="container-header" class="container-header">
    <c:if test="${not empty replace}">
      <button onclick="loadModal('${replace}')"><fmt:message key="button.replace"/></button>
    </c:if>
    <c:if test="${not empty correction}">
      <button onclick="loadModal('${correction}')"><fmt:message key="button.stock.correction"/></button>
    </c:if>
  </div>
  <div id="list" style="padding-left: 8pt">
    <div v-for="item in items">
      <div v-on:click="item.open = !item.open">
        {{item.product.name}}
      </div>
      <div v-if="item.open">
        <div v-for="storage in item.values" style="padding-left: 16pt">
          <a v-on:click="getStocks(new Date(), storage.id, item.product.id)">
            {{storage.name}}
          </a>
          <template v-for="entry in getValues(storage.values)">
            <div class="year content">
              <div class="row" v-on:click="getDetails(entry, storage.id, item.product.id)">
                <span v-if="entry.open">
                  -
                </span>
                <span v-else>
                  +
                </span>
                <span class="date">
                  {{entry.header}} {{entry.title}}
                </span>
                <span class="number">
                  {{entry.begin().toLocaleString()}}
                </span>
                <span class="number plus">
                  +{{entry.plus.toLocaleString()}}
                </span>
                <span class="number minus">
                  {{entry.minus.toLocaleString()}}
                </span>
                <span class="number">
                  {{entry.end().toLocaleString()}}
                </span>
              </div>
            </div>
            <template v-for="month in getValues(entry.values)">
              <div class="month content" v-on:click="getDetails(month, storage.id, item.product.id)">
                <div class="row">
                  <span v-if="month.open">
                    -
                  </span>
                  <span v-else>
                    +
                  </span>
                  <span class="date">
                    {{month.header}} {{month.title}}
                  </span>
                  <span class="number">
                    {{month.begin().toLocaleString()}}
                  </span>
                  <span class="number plus">
                    +{{month.plus.toLocaleString()}}
                  </span>
                  <span class="number minus">
                    {{month.minus.toLocaleString()}}
                  </span>
                  <span class="number">
                    {{month.end().toLocaleString()}}
                  </span>
                </div>
              </div>
              <template v-for="week in getValues(month.values)">
                <div class="week content" v-on:click="getDetails(week, storage.id, item.product.id)">
                  <div class="row">
                    <span v-if="week.open">
                      -
                    </span>
                    <span v-else>
                      +
                    </span>
                    <span class="date">
                      {{week.header}} {{week.title}}
                    </span>
                    <span class="number">
                      {{week.begin().toLocaleString()}}
                    </span>
                    <span class="number plus">
                      +{{week.plus.toLocaleString()}}
                    </span>
                    <span class="number minus">
                      {{week.minus.toLocaleString()}}
                    </span>
                    <span class="number">
                      {{week.end().toLocaleString()}}
                    </span>
                  </div>
                </div>
                <template v-for="day in getValues(week.values)">
                  <div class="day content" v-on:click="getDetails(day, storage.id, item.product.id)">
                    <div class="row">
                      <span v-if="day.open">
                        -
                      </span>
                      <span v-else>
                        +
                      </span>
                      <span class="date">
                        {{day.header}} {{day.title}}
                      </span>
                      <span class="number">
                        {{day.begin().toLocaleString()}}
                      </span>
                      <span class="number plus">
                        +{{day.plus.toLocaleString()}}
                      </span>
                      <span class="number minus">
                        {{day.minus.toLocaleString()}}
                      </span>
                      <span class="number">
                        {{day.end().toLocaleString()}}
                      </span>
                    </div>
                  </div>
                  <template v-for="detail in getValues(day.values)">
                    <div class="detail content">
                      <div class="row">
                        <span class="date">
                          {{new Date(detail.date).toLocaleTimeString().substring(0, 5)}}
                        </span>
                        <div v-if="detail.type=='weight'" style="display: inline-block">
                          <div>
                            {{detail.counterparty}}
                          </div>
                          <div>
                            <span>
                              {{detail.driver}}
                            </span>
                            <span>
                              {{detail.vehicle.model}}
                            </span>
                            <span class="vehicle-number">
                              {{detail.vehicle.number}}
                            </span>
                            <span class="vehicle-number" v-if="detail.vehicle.trailer">
                              {{detail.vehicle.trailer}}
                            </span>
                          </div>
                        </div>
                        <span v-else>
                          {{detail.type}}
                        </span>
                        <span>
                          {{detail.shipper.name}}
                        </span>
                        <span>
                          {{detail.amount.toLocaleString()}}
                        </span>
                      </div>
                    </div>
                  </template>
                </template>
              </template>
            </template>
          </template>
        </div>
      </div>
    </div>
  </div>
</html>
