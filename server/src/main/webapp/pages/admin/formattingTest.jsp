<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 24.10.2019
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script>
  var test = new Vue({
    el:'#test',
    data:{
      api:'',
      input:''
    },
    methods:{
      send:function(){
        const self = this;
        PostApi(this.api, {text: this.input}, function(a){
          self.input = '';
        });
      }
    }
  });
  test.api = '${api}'
</script>
<table id="test">
  <tr>
    <td>
      <input v-model="input" v-on:keyup.enter="send">
    </td>
  </tr>
  <tr>
    <td align="center">
      <a onclick="closeModal()">
        Close
      </a>
    </td>
  </tr>
</table>
</html>
