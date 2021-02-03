<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var editor = new Vue({
    el:'#edit',
    data:{
      api:{},
      organisation:{},
      type:'',
      codeValid:false,
      codeTimer:-1,
      legalAddress:{},
      loadAddress:[],
      err:{
        name:false
      }
    },
    methods:{
      validateCode:function(){
        clearTimeout(this.codeTimer);
        if (this.organisation.code.length === 8 || this.organisation.code.length === 10){
          const self =this;
          this.codeTimer = setTimeout(function () {
            PostApi(self.api.codeValid, {code:self.organisation.code}, function (a) {
              if (a.status === 'success'){
                self.codeValid = a.result;
              }
            })
          }, 300)
        } else {
          this.codeValid = false;
        }
      },
      findType:function(){
        if (!this.type.fullName && this.organisation.type){
          const self = this;
          PostApi(this.api.findType, {key:this.organisation.type}, function(a){
            if (a.status && a.status === 'success'){
              var full = a.result.fullName;
              if (full) {
                self.type.fullName = full;
              }
            }
          })
        }
      },
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
      info:function(){
        PostApi(this.api.info, {id:this.organisation.id}, function(a){
          console.log(a);
        })
      },
      save:function(){
        let err = this.err.name = this.organisation.name === '';
        if (!err) {
          let data = {
            organisation: this.organisation,
            type: this.type
          };
          PostApi(this.api.save, data, function (a) {
            saveModal(a);
            if (a.status === 'success') {
              closeModal();
            }
          })
        }
      }
    }
  });
  editor.api.save='${save}';
  editor.api.editAddress = '${editAddress}';
  editor.api.findType = '${findOrganisationType}';
  editor.api.info = '${info}';
  editor.api.codeValid = '${codeValid}';

  <c:choose>
  <c:when test="${not empty organisation}">
  editor.organisation=${organisation.toJson()};
  </c:when>
  </c:choose>
  <c:if test="${not empty organisation.create}">
  editor.organisation.create = ${organisation.create.toJson()}
  </c:if>
  editor.organisation.code = '${organisation.code}';
  if (editor.organisation.code){
    editor.codeValid = true;
  }
  <c:if test="${not empty organisationType}">
  editor.type = ${organisationType.toJson()};
  </c:if>
  <c:if test="${not empty legalAddress}">
  editor.legalAddress = ${legalAddress.toJson()};
  </c:if>
  <c:forEach items="${loadAddress}" var="address">
  editor.loadAddress.push(${address.toJson()});
  </c:forEach>
</script>
<c:set var="shortName"><fmt:message key="organisation.short.name"/></c:set>
<c:set var="fullName"><fmt:message key="organisation.full.name"/> </c:set>
<table id="edit" style="width: 300pt">
  <tr>
    <td colspan="2">
      ID ${organisation.id}
    </td>
  </tr>
  <tr>
    <td>
      <label for="code">
        <fmt:message key="organisation.code"/>
      </label>
    </td>
    <td>
      <input id="code" v-model="organisation.code" autocomplete="off"
             v-on:keyup="validateCode()" onfocus="this.select()">
      <span v-if="codeValid" class="counterparty-code code-valid">
        &check;
        <span class="code-text">
          <fmt:message key="code.valid"/>
        </span>
      </span>
      <span v-else class="counterparty-code code-invalid">
        &times;
        <span class="code-text">
          <fmt:message key="code.invalid"/>
        </span>
      </span>
      <span class="mini-close" v-on:click="info()" v-if="!organisation.code">
        i
      </span>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <label for="name">
        <fmt:message key="organisation.name"/>
      </label>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <input id="name" v-model="organisation.name" v-on:click="err.name = false" style="width: 100%"
          onfocus="this.select()" :class="{error : err.name}" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td colspan="2" style="border: solid gray 1pt">
      <label for="form">
        <fmt:message key="organisation.form"/>
      </label>
      <input id="form" v-model="organisation.type" autocomplete="off" title="${shortName}"
             style="width: 10em" onfocus="this.select()" v-on:blur="findType()">
      <template v-if="organisation.type">
      <input style="width: 100%" v-model="type.fullName" title="${fullName}"
             autocomplete="off" onfocus="this.select()">
      </template>
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
    <td colspan="2">
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
          <br v-if="legalAddress.index || legalAddress.region || legalAddress.district">
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
  <tr v-if="organisation.create">
    <td colspan="2" style="font-size: 8pt; color: grey">
      <fmt:message key="laboratory.response"/>: {{organisation.create.creator.person.value}}
      {{new Date(organisation.create.time).toLocaleString()}}
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <input id="we" type="checkbox" v-model="organisation.we">
      <label for="we">
        <fmt:message key="some.check.box"/>
      </label>
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
