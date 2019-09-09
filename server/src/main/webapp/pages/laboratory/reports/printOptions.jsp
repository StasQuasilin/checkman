<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
  var options = new Vue({
    el:'#options',
    data:{
      date:new Date().toISOString().substring(0, 10),
      number:0,
      workers:[],
      workerInput:''
    },
    methods:{
      foundWorkers:function(){
        if (this.workerInput) {
          const self = this;
          return this.workers
                  .filter(function (item) {
                    return item.value.match(self.workerInput);
                  })
        }
      }
    }
  });
  options.number = ${number};
  <c:forEach items="${workers}" var="worker">
  options.workers.push({
    id:${worker.id},
    value:'${worker.value}'
  });
  </c:forEach>
</script>
<div id="options">
  <table>
    <tr>
      <td>
        <label for="date">
          <fmt:message key="date"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="date" autocomplete="off" v-model="new Date(date).toLocaleDateString()" style="width: 7em">
      </td>
    </tr>
    <tr>
      <td>
        <label for="number">
          <fmt:message key="act.number"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input type="number" id="number" autocomplete="off" v-model="number" style="width: 7em">
      </td>
    </tr>
    <tr>
      <td>
        <label for="response">
          <fmt:message key="laboratory.response"/>
        </label>
      </td>
      <td>
        :
      </td>
      <td>
        <input id="response" autocomplete="off" v-model="workerInput">
        <div class="custom-data-list">
          <div class="custom-data-list-item" v-for="worker in foundWorkers()">
            {{worker.value}}
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td colspan="3" align="center">
        <button class="left-button close-button" onclick="closeModal()">
          <fmt:message key="button.cancel"/>
        </button>
        <button class="right-button save-button">
          <fmt:message key="document.print"/>
        </button>
      </td>
    </tr>
  </table>
</div>

