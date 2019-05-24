<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/filter.css">
<script>
  var req_filter = new Vue({
    el: '#filter',
    data:{
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
      }
    }
  })
</script>
<div id="filter" class="filter">
  <table width="100%">
    <tr>
      <td>
        <fmt:message key="date"/>
      </td>
      <td width="100%">
        <div v-on:click="pickDate">
          <a v-if="date">
            {{new Date(date).toLocaleDateString()}}
          </a>
          <i v-else>
            <a>
              <fmt:message key="need.select"/>
            </a>
          </i>

        </div>
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
