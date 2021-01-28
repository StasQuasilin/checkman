<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 07.10.2019
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .storage-row{
    border: dashed darkgray 1pt;
    background-color: #e2e2e2;
    width: 88%;
    display: inline-block;
  }
</style>
  <script>
    var storages = new Vue({
      el:'#storages',
      data:{
        api:{},
        storages:{}
      },
      mounted:function(){
        const self = this;
        subscribe('STOCK', function(a){
          for (let s in a.add){
            if (a.add.hasOwnProperty(s)){
              let storageItem = a.add[s];
              let storage = {id:storageItem.id,name:storageItem.name,open:false, values:{}};
              for (let p in storageItem.value){
                if (storageItem.value.hasOwnProperty(p)){
                  let productItem = storageItem.value[p];
                  let product = {id:productItem.id, name:productItem.name,open:false, values:{}};
                  for (let i in productItem.value){
                    if (productItem.value.hasOwnProperty(i)){
                      let shipperItem = productItem.value[i];
                      let shipper = {id:shipperItem.id,name:shipperItem.name,open:false, value:shipperItem.value};
                      self.updateStock(storage, product, shipper);
                    }
                  }
                }
              }
            }
          }
//          self.storages = a.add;
        })
      },
      methods:{
        edit:function(id){
          if (this.api.edit){
            loadModal(this.api.edit + '?id=' + id);
          }
        },
        sum:function(item){
          let sum = 0;
          for (let i in item.values){
            if (item.values.hasOwnProperty(i)){
              sum += item.values[i].value;
            }
          }
          return sum;
        },
        updateStock:function(storage, product, shipper){
          if (!this.storages[storage.id]){
            Vue.set(this.storages, storage.id, storage);
          }
          let storageValues = this.storages[storage.id].values;

          if (!storageValues[product.id]){
            Vue.set(storageValues, product.id, product);
          }

          let productValues = storageValues[product.id].values;
          Vue.set(productValues, shipper.id, shipper);
        }
      }
    });
    storages.api.edit = '${editStorage}';
  </script>
    <div id="storages">
        <div v-for="(storage, storageKey) in storages">
          <span v-on:click="storage.open = !storage.open">
            <span v-if="storage.open">
              &#9207;
            </span>
            <span v-else>
              &#9205;
            </span>
            <span class="storage-row" :class="{bold : storage.open}">
                {{storage.name}}
            </span>
          </span>
          <div v-if="storage.open" style="padding-left: 8pt">
            <div v-for="product in storage.values">
              <span v-on:click="product.open = !product.open">
                <span v-if="product.open">
                  &#9207;
                </span>
                <span v-else>
                  &#9205;
                </span>
                <span>
                  <span>
                    {{product.name}}
                  </span>
                  <span>
                    {{(sum(product)).toLocaleString()}}
                  </span>
                </span>
              </span>
              <div v-if="product.open" style="padding-left: 16pt">
                <div v-for="shipper in product.values">
                  <span style="font-size: 10pt">
                    {{shipper.name}}
                  </span>
                  <span :class="{error : shipper.value < 0}">
                    {{(shipper.value).toLocaleString()}}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
</html>
