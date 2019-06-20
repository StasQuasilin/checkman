<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title><fmt:message key="password.restore"/> </title>
    <link rel="stylesheet" href="${context}/css/login.css">
    <script src="${context}/js/Logic.js"></script>
    <script>
      const context = '${context}';
    </script>
  </head>
  <body>
    <div class="wrapper" id="restorator">
      <fmt:message key="password.restore.text"/>
      <div class="content">
        <table width="100%">
          <tr>
            <td>
              <label for="email">
                <fmt:message key="email"/>
              </label>
            </td>
            <td>
              <input id="email" type="email" style="width: 100%" autocomplete="off" v-model="email">
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <button onclick="location.href='${context}${signIn}'">
                <fmt:message key="button.back"/>
              </button>
              <button v-on:click="restore()">
                <fmt:message key="button.restore"/>
              </button>
            </td>
          </tr>
        </table>
      </div>
      <span v-if="isError">
        {{error.msg}}
      </span>
      <span v-if="redirectLeft > 0">
        <fmt:message key="password.restore.success"/>
        <fmt:message key="password.restore.redirect"/>&nbsp;{{redirectLeft}}
        (
          <a onclick="location.href='${context}${signIn}"><fmt:message key="password.restore.redirect.now"/> </a>
        )


      </span>
    </div>
  </body>
  <script>
    var restore = new Vue({
      el:'#restorator',
      data:{
        api:{
          restore:'',
          back:''
        },
        email:'',
        waitAnswer:false,
        isError:false,
        error:{
          msg:''
        },
        redirectTime:10,
        redirectLeft:0

      },
      methods:{
        restore:function(){
          if (this.email && !this.waitAnswer) {
            const self = this;
            this.waitAnswer = true;
            PostApi(this.api.restore, {email: this.email}, function (a) {
              self.waitAnswer = false;
              console.log(a);
              if (a.status == 'success') {
                self.redirectLeft = self.redirectTime;
                self.redirect();
              } else {
                self.isError = true;
                self.error.msg = a.msg;
              }
            })
          }
        },
        redirect:function(){
          this.redirectLeft--;
          if (this.redirectLeft > 0){
            const self = this;
            setTimeout(function(){
              self.redirect()
            }, 1000)
          } else{
            location.href=context + this.api.back;
          }
        }
      }
    });
    restore.api.restore='${restore}';
    restore.api.back='${signIn}'
  </script>
</html>
