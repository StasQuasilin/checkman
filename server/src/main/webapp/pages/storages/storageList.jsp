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
  <script>
    var storages = new Vue({
      el:'#storages',
      data:{
        api:{},
        storages:[]
      },
      methods:{
        edit:function(id){
          if (this.api.edit){
            loadModal(this.api.edit + '?id=' + id);
          }
        }
      }
    });
    storages.api.edit = '${editStorage}';
    <c:forEach items="${storages}" var="storage">
    storages.storages.push({
      id:${storage.id},
      name:'${storage.name}'
    });
    </c:forEach>
  </script>
  <div id="static-content">
    <div id="storages">

        <div v-for="storage in storages">
          <span>
              {{storage.name}}
          </span>
          <span class="mini-close flipY" style="padding: 0" v-on:click="edit(storage.id)">
            &#9998;
          </span>
        </div>

    </div>
  </div>
</html>
