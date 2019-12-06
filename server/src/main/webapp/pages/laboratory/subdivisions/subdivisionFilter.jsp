<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/filter.css">
<script>
  var req_filter = new Vue({
    el: '#filter_view',
    data:{
      api:{},
      date:''
    },
    methods:{
      pickDate:function(){
        const self = this;
        datepicker.show(function(date){
          self.date = date;
        }, self.date)
      },
      clear:function(){
        this.date = '';
      },
      find:function(){
        if (this.api.find){
          PostApi(this.api.find, {date:this.date}, function(a){
            console.log(a);
          })
        }
      }
    }
  })
</script>
<div id="filter_view" class="filter">
  <table width="100%">
    <tr>
      <td>
        <fmt:message key="date"/>
      </td>
      <td width="100%" align="center">
        <div v-on:click="pickDate">
          <a v-if="date">
            {{new Date(date).toLocaleDateString()}}
          </a>
          <a v-else>
            ...
          </a>
        </div>
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
  </table>
</div>

</html>
