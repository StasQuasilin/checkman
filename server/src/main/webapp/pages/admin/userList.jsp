<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .user-list{
    overflow-y: scroll;
    border: solid gray 1pt;
    padding: 2pt;
    height: 360px
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
      api:{},
      users:{},
      all:[]
    },
    methods:{
      handler:function(a){
        if (a.update){
          for (let i in a.update){
            if (a.update.hasOwnProperty(i)){
              let update = a.update[i];
              this.users[update.token] = update;
            }
          }
          this.$forceUpdate();
        }
        if (a.remove){
          for (let r in a.remove){
            if (a.remove.hasOwnProperty(r)){
              let remove = a.remove[r];
              delete this.users[remove];
            }
          }
          this.$forceUpdate();
        }
      },
      openUserPage:function(id){
        loadModal(this.api.userPage, {id:id});
      }
    }
  });
  list.api.userPage = '${userPage}';
  <c:forEach items="${subscribe}" var="s">
  subscribe('${s}', function(a){
    list.handler(a);
  });
  </c:forEach>
</script>
<table id="list" style="width: 400pt; height: 300pt">
  <tr>
    <th style="width: 50%">
      <fmt:message key="admin.user.list.all"/>
    </th>
  </tr>
  <tr style="height: 100%">
    <td style="vertical-align: top">
      <div class="user-list">
        <div v-for="user in users" v-on:click="openUserPage(user.id)" style="border-bottom: dashed gray 1pt">
          <span style="font-size: 6pt; color: gray">
            {{user.token}}
          </span><br>
          <span v-if="user.session" style="color: green">
            &#9679;
          </span>
          <span class="mini-close">
            {{user.person.value}}
          </span>
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
