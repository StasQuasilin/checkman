<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 24.10.2019
  Time: 9:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var collapse = new Vue({
    el:'#collapse',
    data:{
      api:{},
      o1:{
        id:-1,
        input:'',
        list:[],
        deals:[],
        fnd:-1
      },
      o2:{
        id:-1,
        input:'',
        list:[],
        deals:[],
        fnd:-1
      }
    },
    methods:{
      findOrganisation:function(o){
        clearTimeout(o.fnd);
        const self = this;
        if (o.input) {
          o.fnd = setTimeout(function () {
            PostApi(self.api.find, {key: o.input}, function (a) {
              o.list = a;
            })
          }, 300)
        } else {
          o.list = [];
        }
      },
      putOrganisation:function(o, organisation){
        o.id= organisation.id;
        o.input = organisation.value;
      },
      closeOrganisation:function(o){
        o.id = -1;
        o.input = '';
      },
      collapse:function(){
        PostApi(this.api.collapse, {from:this.o2, to:this.o1}, function(a){

        })
      }
    }
  });
  collapse.api.find = '${find}';
</script>
  <table id="collapse">
    <tr>
      <td>
        <label for="organisation1">
          <fmt:message key="deal.organisation"/> 1
        </label>
      </td>
      <td>
        &nbsp;
      </td>
      <td>
        <label for="organisation2">
          <fmt:message key="deal.organisation"/> 2
        </label>
      </td>
    </tr>
    <tr>
      <td>
        <template v-if="o1.id == -1">
          <input id="organisation1" v-model="o1.input" v-on:keyup="findOrganisation(o1)">
          <div class="custom-data-list">
            <div class="custom-data-list-item" v-for="org1 in o1.list" v-on:click="putOrganisation(o1, org1)">
              {{org1.value}}
            </div>
          </div>
        </template>
        <template v-else>
          {{o1.input}}
          <span class="mini-close" v-on:click="closeOrganisation(o1)">
            &times;
          </span>
        </template>
      </td>
      <td>
        <
      </td>
      <td>
        <template v-if="o2.id == -1">
          <input id="organisation2" v-model="o2.input" v-on:keyup="findOrganisation(o2)">
          <div class="custom-data-list">
            <div class="custom-data-list-item" v-for="org2 in o2.list" v-on:click="putOrganisation(o2, org2)">
              {{org2.value}}
            </div>
          </div>
        </template>
        <template v-else>
          {{o2.input}}
          <span class="mini-close" v-on:click="closeOrganisation(o2)">
            &times;
          </span>
        </template>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center">
        <button onclick="closeModal()">
          <fmt:message key="button.cancel"/>
        </button>
        <button>
          COLLAPSE!
        </button>
      </td>
    </tr>
  </table>
</html>
