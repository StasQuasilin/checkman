<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .user-list{
    overflow-y: scroll;
    border: solid gray 1pt;
    padding: 2pt;
    height: 100%;
  }
  .user-row{

  }
  .user-office{
    font-size: 10pt;
    color: darkgray;
  }
  .user-ip{
    font-size: 9pt;
    color: grey;
  }

</style>
<script>
  var list = new Vue({
    el:'#list',
    data:{
      active:[],
      all:[]
    },
    methods:{
      handler:function(a){
        console.log(a);
        if (a.add){
          for(var i in a.add.active){
            if (a.add.active.hasOwnProperty(i)){
              this.active.push(a.add.active[i])
            }
          }

          for (var j in a.add.all){
            if (a.add.all.hasOwnProperty(j)){
              this.all.push(a.add.all[j]);
            }
          }
        }
      }
    }
  });
  <c:forEach items="${subscribe}" var="s">
  subscribe('${s}', function(a){
    list.handler(a);
  });
  </c:forEach>
</script>


<table id="list" style="width: 400pt; height: 300pt" border="0">
  <tr>
    <th style="width: 50%">
      <fmt:message key="admin.user.list.active"/>
    </th>
    <th style="width: 50%">
      <fmt:message key="admin.user.list.all"/>
    </th>
  </tr>
  <tr style="height: 100%">
    <td valign="top">
      <div class="user-list">
        <div v-for="user in active" class="user-row">
          <div class="user-name">
            {{user.person.value}}
          </div>
          <div class="user-office">
            {{user.ip}}
          </div>
          <div class="user-office">
            {{user.session}}
          </div>
        </div>
      </div>
    </td>
    <td valign="top">
      <div class="user-list">
        <div v-for="user in all">
          {{user.person.value}}
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <button class="cancel-button" onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>
</html>
