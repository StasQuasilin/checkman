<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 28.10.2019
  Time: 15:15
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var printer = new Vue({
    el:'#print',
    data:{
      api:{},
      date1:new Date().toISOString().substr(0, 10),
      date2:new Date().toISOString().substr(0, 10),
      products:[],
      items:[]
    },
    mounted:function(){
      this.items = list.getItems();
    },
    methods:{
      productList:function(){
        let products = {};
        for (var i in this.items){
          if (this.items.hasOwnProperty(i)){
            let product = this.items[i].item.product;
            if (!products[product.id]){
              product.check = true;
              products[product.id] = product;
            }
          }
        }
        return this.products = Object.values(products);
      },
      pickDate1:function(){
        const self = this;
        datepicker.show(function(date){
          self.date1 = date;
        }, this.date1)
      },
      pickDate2:function(){
        const self = this;
        datepicker.show(function(date){
          self.date2 = date;
        }, this.date2)
      },
      getItems:function(){
        let items = {};

        let d1 = new Date(this.date1);
        let d2 = new Date(this.date2);

        for(let i in this.items){
          if (this.items.hasOwnProperty(i)){
            let item = this.items[i].item;
            let date = new Date(item.date);
            let productMatch = false;
            for(let p in this.products){
              if (this.products.hasOwnProperty(p)){
                let product = this.products[p];
                if (product.check && product.id == item.product.id){
                  productMatch = true;
                  break;
                }
              }
            }
            if (date >= d1 && date <= d2 && productMatch){
              if (!items[item.date]){
                items[item.date] = {};
              }
              if (!items[item.date][item.product.name]){
                items[item.date][item.product.name] = []
              }
              items[item.date][item.product.name].push(item);
            }
          }
        }
        return items;
      },
      print:function() {
        let items = this.getItems();
        let w = window.open();
        w.document.write('<style>table{border-collapse: collapse; width: 100%; font-size: 10pt;}');
        w.document.write('td{ border: solid gray 1pt; padding: 2pt 4pt; }');
        w.document.write('</style>');
        w.document.write('<html><table border="1">');
        for (let i in items){
          if (items.hasOwnProperty(i)){
            w.document.write('<tr><td colspan="7">');
            w.document.write('<b>');
            w.document.write(new Date(i).toLocaleDateString().substring(0, 10));
            w.document.write('</b>');
            w.document.write('</td>');
            let arr = items[i];
            for(let j in arr){
              if (arr.hasOwnProperty(j)){
                let item = arr[j];
                w.document.write('<tr><td colspan="7" style="padding-left: 8pt; background-color: #767676; color: white;">');
                w.document.write('<b>');
                w.document.write(j);
                w.document.write('</b>');
                w.document.write('</td>');
                for (let k in item){
                  if (item.hasOwnProperty(k)){
                    let row = item[k];

                    w.document.write('<tr><td style="padding-left: 16pt;">');
                    w.document.write(row.counterparty.value);
                    w.document.write('</td><td>');
                    w.document.write(row.product.name);
                    w.document.write('</td><td>');
                    w.document.write(row.plan);
                    w.document.write('</td><td>');
                    w.document.write(row.shipper);
                    w.document.write('</td><td>');
                    w.document.write(row.price);
                    w.document.write('</td><td>');
                    row.notes.forEach(function(note){
                      w.document.write(note.note + ' ');
                    });

                    w.document.write('</td></tr>')
                  }
                }

              }
            }
            w.document.write('</td></tr>')
          }
        }
        w.document.write('</table></html>');
        w.print();
//        w.close();
      }
    }
  })
</script>
<table id="print">
  <tr>
    <td>
      <label for="date1">
        <fmt:message key="date"/>
      </label>
    </td>
    <td>
      <input id="date1" v-model="new Date(date1).toLocaleDateString().substring(0, 10)"
             readonly style="width: 7em" v-on:click="pickDate1">
      <input id="date2" v-model="new Date(date2).toLocaleDateString().substring(0, 10)"
             readonly style="width: 7em" v-on:click="pickDate2">
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="products"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div v-for="product in productList()">
        <input type="checkbox" :id="product.id" v-model="product.check">
        {{product.name}}
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
      <button v-on:click="print">
        <fmt:message key="document.print"/>
      </button>
    </td>
  </tr>
</table>
</html>
