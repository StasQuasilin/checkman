<%--
  Created by Kvasik
  Date: 07.10.2019
  Time: 23:30
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
  var storageEdit = new Vue({
    el:'#edit',
    data:{
      api:{},
      storage:{},
      products:[],
      storageProducts:[],
      add:false,
      newProduct:{
        product:-1,
        max:0
      }
    },
    methods:{
      getProducts:function(){
        return Object.values(this.products);
      },
      addProduct:function(){
        this.storageProducts.push({
          product:this.products[this.newProduct.product],
          max:this.newProduct.max
        });
        this.cancelAdd();
      },
      cancelAdd:function(){
        this.add = false;
        this.newProduct = {
          product:-1,
          max:0
        }
      }
    }
  });
  storageEdit.api.save = '${save}';
  storageEdit.storage = {
    id:${storage.id},
    name:'${storage.name}'
  };
  <c:forEach items="${products}" var="product">
  storageEdit.products[${product.id}]=({
    id:${product.id},
    name:'${product.name}'
  });
  </c:forEach>
  <c:forEach items="${storageProducts}" var="storageProduct">
  storageEdit.storageProducts.push({
    id:${storageProduct.id},
    product:{
      id:${storageProduct.product.id},
      name:'${storageProduct.product.name}'
    },
    max:${storageProduct.max}
  });
  </c:forEach>
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
  <table id="edit" class="editor">
    <tr>
      <td colspan="2">
        <label for="name">
          <span style="font-size: 10pt">
            <fmt:message key="storage.edit.name"/>
          </span>
        </label>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <input id="name" v-model="storage.name" autocomplete="off" onfocus="this.select()" style="width: 100%">
      </td>
    </tr>
    <tr>
      <td>
        <fmt:message key="storage.edit.producs"/>
      </td>
      <td>
        <fmt:message key="storage.edit.max"/>
      </td>
    </tr>
    <tr v-for="storageProduct in storageProducts">
      <td>
        {{storageProduct.product.name}}
      </td>
      <td>
        {{storageProduct.max}}
      </td>
    </tr>
    <tr v-if="!add">
      <td colspan="2" align="right">
        <span class="mini-close" v-on:click="add = !add">
          <fmt:message key="button.add"/>
        </span>
      </td>
    </tr>
    <tr v-else>
      <td>
        <select v-model="newProduct.product" style="width: 100%">
          <option v-for="product in getProducts()" :value="product.id">
            {{product.name}}
          </option>
        </select>
      </td>
      <td>
        <input type="number" v-model="newProduct.max">
        <span class="mini-close" v-on:click="addProduct()">
          +
        </span>
        <span class="mini-close" v-on:click="cancelAdd()">
          &times;
        </span>
      </td>
    </tr>
    <tr>
      <td>
        <button onclick="closeModal()">
          <fmt:message key="button.close"/>
        </button>
      </td>
    </tr>
  </table>
</html>
