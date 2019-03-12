<%@ page import="constants.Branches" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

  </head>
  <body>
  <div v-bind:title="title" id="app">
    <h2>'{{ message }}'</h2>
    <h4>Ticks: {{counter }}</h4>
    <button v-on:click="saveAs">Save</button>
    <div v-if="save">
      <ol>
        <todo-item
                v-for="item in items"
                v-bind:todo="item"
                v-bind:key="item.id">
        </todo-item>
      </ol>
    </div>
  </div>

  <%--<%response.sendRedirect(request.getContextPath()+ Branches.Base.HOME);%>--%>
  </body>
  <script src="vue/testVue.js"></script>
</html>
