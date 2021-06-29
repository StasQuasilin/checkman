<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 30.11.2019
  Time: 0:22
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
<script>
  var productEdit = new Vue({
    el:'#productEdit',
    components:{
      'object-input':objectInput
    },
    data:{
      api:{},
      product:{
        id:-1,
        name:'',
        unit:-1,
        group:{
          id:-1,
          name:''
        },
        weight:1,
        pallet:1,
        path:[]
      },
      groupProps:{
        find:'${findGroup}',
        edit:'${editGroup}',
        add:'${parseGroup}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="product.group.add"/>',
        put:function(group){
          productEdit.product.group = group;
        },
        show:['name']
      },
      units:[],
      pathInput:''
    },
    methods:{
      getUnitName:function(){
        if (this.product) {
          const self = this;
          for (let i in this.units){
            if (this.units.hasOwnProperty(i)){
              let unit = this.units[i];
              if (unit.id === self.product.unit){
                return unit.name;
              }
            }
          }
        }
      },
      addPath:function(){
        this.product.path.push(this.pathInput);
        this.pathInput = '';
      },
      removePath:function(id){
        this.product.path.splice(id, 1);
      },
      save:function(){
        let product = Object.assign({}, this.product);
        product.group = this.product.group.id;
        PostApi(this.api.save, product, function(a){
          if (a.status === 'success'){
            closeModal();
            saveModal(a);
          }
        })
      }
    }
  });
  productEdit.api.save ='${save}';
  productEdit.product.id='${product.id}';
  productEdit.product.name='${product.name}';
  <c:if test="${not empty product.unit}">
  productEdit.product.unit = ${product.unit.id};
  </c:if>
  <c:forEach items="${units}" var="unit">
  productEdit.units.push({
    id:${unit.id},
    name:'${unit.name}'
  });
  </c:forEach>
  <c:forEach items="${types}" var="type">
  productEdit.product.${type} = {
    value:false,
    editable:true
  };
  </c:forEach>
  <c:forEach items="${actions}" var="action">
  productEdit.product.${action.type} = {
    value:true,
    editable:${action.editable}
  };
  </c:forEach>
</script>
<table id="productEdit">
  <tr>
    <td>
      <label for="name">
        <fmt:message key="product.name"/>
      </label>
    </td>
    <td>
      <input id="name" v-model="product.name" autocomplete="off">
    </td>
  </tr>
  <tr>
    <td>
      <label for="unit">
        <fmt:message key="unit"/>
      </label>
    </td>
    <td>
      <select id="unit" v-model="product.unit">
        <option v-if="product.unit === -1" value="-1" disabled>
          !!
        </option>
        <option v-for="unit in units" :value="unit.id">
          {{unit.name}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <fmt:message key="product.actions"/>
    </td>
    <td>
      <c:forEach items="${types}" var="type">
        <template v-if="product.${type}">
          <input id="${type}" :disabled="!product.${type}.editable" type="checkbox" v-model="product.${type}.value">
          <label for="${type}">
            <fmt:message key="${type}"/>
          </label>
          <c:if test="${role eq 'admin'}">
            <input title="EDITABLE?" type="checkbox" v-model="product.${type}.editable">
            <br>
          </c:if>
        </template>
      </c:forEach>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
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
