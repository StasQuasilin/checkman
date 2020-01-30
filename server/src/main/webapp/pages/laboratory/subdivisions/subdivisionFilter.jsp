<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/filter.css">
<script>
  var filter_control = new Vue({
    el: '#filter_view',
    data:{
      api:{},
      date:'',
      turn:-1,
      turns:[],
      result:[],
      notFound:false,
      items:[]
    },
    methods:{
      pickDate:function(){
        const self = this;
        datepicker.show(function(date){
          self.date = date;
          self.find();
        }, self.date)
      },
      clear:function(){
        this.date = '';
        this.turn = -1;
        this.result = [];
      },
      find:function(){
        if (this.api.find){
          this.notFound = false;
          const self = this;
          PostApi(this.api.find, {date:this.date,turn:this.turn}, function(a){
              self.result = [];
            if (a.length > 0){
              a.forEach(function(item){
                self.result.push({item:item});
              })
            } else{
              self.notFound = true;
            }
          })
        }
      },
      leftDate:function(val){
        var date = new Date(this.date);
        date.setDate(date.getDate() + val);
        this.date = date.toISOString().substring(0, 10);
        this.find();
      },
      filteredItems:function(){
        if (this.result.length > 0){
          return this.result;
        } else{
          return this.items;
        }
      }
    }
  });
  filter_control.api.find = '${find}';
  <c:forEach items="${turns}" var="turn">
  filter_control.turns.push({
    id:${turn.number},
    name:'<fmt:message key="turn"/> #'+${turn.number}
  });
  </c:forEach>
</script>
<div id="filter_view" class="filter">
  <table width="100%">
    <tr>
      <td>
        <fmt:message key="date"/>
      </td>
      <td width="100%" align="center">
        <template v-if="date">
          <span class="mini-close" v-on:click="leftDate(-1)">
            <
          </span>
          <a v-on:click="pickDate">
            {{new Date(date).toLocaleDateString()}}
          </a>
          <span class="mini-close" v-on:click="leftDate(1)">
            >
          </span>
        </template>
        <a v-else v-on:click="pickDate">
          <fmt:message key="select"/>
        </a>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <select v-model="turn" style="width: 100%" v-on:change="find()">
          <option value="-1">
            <fmt:message key="not.select"/>
          </option>
          <option v-for="turn in turns" :value="turn.id">
            {{turn.name}}
          </option>
        </select>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <a v-on:click="find">
          <fmt:message key="find"/>
        </a>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <a v-on:click="clear">
          <fmt:message key="button.clear"/>
        </a>
      </td>
    </tr>
    <tr v-if="notFound">
      <td colspan="2" align="center" style="color: orangered">
        <fmt:message key="not.found"/>
      </td>
    </tr>
  </table>
</div>

</html>
