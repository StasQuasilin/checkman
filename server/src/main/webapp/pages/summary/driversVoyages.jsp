<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 18.11.2019
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script>
  var voyage = new Vue({
    el:'#voyage',
    data:{
      opens:{}
    },
    methods:{
      sortedItems:function(values){
        let val = Object.values(values);
        val.sort(function(a, b){
          let aL = Object.values(a.values).length;
          let bL = Object.values(b.values).length;
          if (aL == bL){
            return a.driver.person.value.localeCompare(b.driver.person.value);
          }
          return bL - aL;
        });
        return val;
      },
      open:function(item){
        this.opens[item.key] = !this.opens[item.key];
      },
      getOpen:function(id){
        if (!this.opens[id]){
          Vue.set(this.opens, id, false);
        }
        return this.opens[id];
      },
      getItems:function(){
        var items = list.getItems();
        var calendar = {};
        for (var i in items) {
          if (items.hasOwnProperty(i)){
            var a = items[i].item;
            var date = calendar[a.date];
            if (!date){
              date = Vue.set(calendar, a.date, {
                values:{}
              })
            }
            let d = a.driver;
            if (d.id){
              let driver = date.values[d.id];
              let driverKey = a.date + '/' + d.id;

              if (!driver){
                driver = Vue.set(date.values, d.id, {
                  key:driverKey,
                  open:this.getOpen(driverKey),
                  driver: d,
                  values:[]
                })
              }
              driver.values.push({
                organisation: a.organisation.value,
                product: a.product.name
              });
            }
          }
        }
        return calendar;
      }
    }
  })
</script>
<style>
  .calendar{
    display: inline-block;
    font-size: 8pt;
    width: 100%;
  }
  .calendar table{
    border-collapse: collapse;
  }
</style>
  <div id="voyage">
    <table style="font-size: 10pt">
      <template v-for="(value, key) in getItems()">
        <tr>
          <td>
            <span style="font-size: 10pt">
              {{new Date(key).toLocaleDateString().substring(0, 5)}}
            </span>
          </td>
        </tr>
        <template v-for="driver in sortedItems(value.values)">
          <tr v-on:click="open(driver)">
            <td style="padding-left: 4pt" width="100%">
              <span v-if="driver.open">
                  -
              </span>
              <span v-else>
                  +
              </span>
              <span>
                {{driver.driver.person.value}} - {{driver.values.length}}
              </span>
            </td>
          </tr>
          <template v-for="organisation in driver.values" v-if="driver.open">
            <tr>
              <td style="padding-left: 16pt" width="100%">
                {{organisation.organisation}}
              </td>
            </tr>
            <tr>
              <td style="padding-left: 24pt">
                {{organisation.product}}
              </td>
            </tr>
          </template>
        </template>
      </template>
    </table>
  </div>
</html>
