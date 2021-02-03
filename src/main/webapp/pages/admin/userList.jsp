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
  list = new Vue({
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
          this.sort();
          this.$forceUpdate();
        }
        if (a.remove){
          for (let r in a.remove){
            if (a.remove.hasOwnProperty(r)){
              let remove = a.remove[r];
              delete this.users[remove];
            }
          }
          this.sort();
          this.$forceUpdate();
        }
      },
      sort:function(a, b){
        if (a && b && ((a.token && b.token) || (!a.token && !b.token))){
          return a.person.value.localeCompare(b.person.value);
        } else if (a && a.token){
          return 1;
        } else if (b && b.token){
          return -1;
        }
      },
      usersList:function(){
        return Object.values(this.users);
      },
      openUserPage:function(id){
        loadModal(this.api.userPage, {id:id});
      },
      killSession:function (session) {
        PostApi(this.api.kill, {'session' : session});
      }
    }
  });
  list.api.userPage = '${userPage}';
  list.api.kill = '${kill}';
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
        <div v-for="user in usersList()" style="border-bottom: dashed gray 1pt; display: flex; flex-direction: row; background-color: lightgray">
          <div style="width: 100%" v-on:click="openUserPage(user.id)">
            <span style="font-size: 6pt; color: gray">
              {{user.token}}
            </span><br>
            <span v-if="user.session" style="color: green">
              &#9679;
            </span>
            <span class="mini-close">
              {{user.person.value}}
            </span><br>
            <span v-if="user.ip" style="font-size: 8pt">
              {{user.ip}}
            </span>
          </div>
          <div v-if="user.token">
            <span class="mini-close" v-on:click="killSession(user.token)">
              &times;
            </span>
          </div>
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
