<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var editor = new Vue({
    el:'#edit',
    data:{
      api:{},
      organisation:{},
      legalAddress:{},
      loadAddress:[],
      err:{
        name:false
      }
    },
    methods:{
      editLegalAddress:function(id){
        const self = this;
        this.editAddress('legal', id, function(a){
          console.log(a);
          if (a.result){
            self.legalAddress = a.result;
          } else{
            self.legalAddress = a;
          }
        })
      },
      editLoadAddress:function(id){
        const self = this;
        this.editAddress('load', id, function(a){
          self.loadAddress.push(a.result);
        })
      },
      editAddress:function(type, id, onSave){
        loadModal(this.api.editAddress, {counterparty:this.organisation.id, type:type, id:id}, onSave);
      },
      save:function(){
        var err = this.err.name = this.organisation.name === '';
        if (!err) {
          PostApi(this.api.save, this.organisation, function (a) {
            saveModal(a);
            if (a.status == 'success') {
              closeModal();
            }
          })
        }
      }
    }
  });
  editor.api.save='${save}';
  editor.api.editAddress = '${editAddress}';
  <c:choose>
  <c:when test="${not empty organisation}">
  editor.organisation=${organisation.toJson()};
  </c:when>
  </c:choose>
  <c:if test="${not empty legalAddress}">
  editor.legalAddress = ${legalAddress.toJson()};
  </c:if>
  <c:forEach items="${loadAddress}" var="address">
  editor.loadAddress.push(${address.toJson()});
  </c:forEach>
</script>
<table id="edit" style="width: 300pt">
  <tr>
    <td colspan="3">
      <label for="name">
        <fmt:message key="organisation.name"/>
      </label>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <input id="name" v-model="organisation.name" v-on:click="err.name = false" style="width: 100%"
          onfocus="this.select()" :class="{error : err.name}" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <label for="form">
        <fmt:message key="organisation.form"/>
      </label>
    </td>
  </tr>
  <tr>
    <td>
      <input id="form" v-model="organisation.type" autocomplete="off" style="width: 100%">
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="legal.address"/>
      <a v-if="!legalAddress.id" v-on:click="editLegalAddress(-1)">
        +<fmt:message key="add.address"/>
      </a>
    </td>
  </tr>
  <tr v-if="legalAddress.id">
    <td>
      <a v-on:click="editLegalAddress(legalAddress.id)">
        <b>
          <span v-if="legalAddress.index">
            {{legalAddress.index}},
          </span>
          <span v-if="legalAddress.region">
            {{legalAddress.region}}
            <fmt:message key="address.region.short"/>,
          </span>
          <span v-if="legalAddress.district">
            {{legalAddress.district}}
            <fmt:message key="address.district.short"/>,
          </span>
          <br>
          {{legalAddress.city}},
          {{legalAddress.street}},
          {{legalAddress.build}}
        </b>
      </a>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="load.address.list"/>
      <a class="mini-close" v-on:click="editLoadAddress(-1)">
        +<fmt:message key="add.address"/>
      </a>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="width: 100%; height: 100px; border: solid gray 1pt; overflow-y: scroll">
        <div v-for="address in loadAddress" v-on:click="editLoadAddress(address.id)">
          {{address.city}}
          {{address.street}}
          {{address.build}}
        </div>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="save">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
