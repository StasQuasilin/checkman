<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <div id="chat" class="chat-holder" :class="{full : show}">
    <div>
      <div class="chat-button-holder" v-on:click="switchChat">
        <span class="chat-button">
          &#128386;
        </span>
        <span class="chat-messages-counter" v-show="unread > 0">
          {{unread}}
        </span>
      </div>
    </div>
    <div v-show="show">
      <div class="invisible-close-button" v-on:click="hideChat"></div>
      <div  class="chat-view" >
        <jsp:include page="chatView.jsp"/>
      </div>
    </div>
  </div>
</html>
<script src="${context}/vue/chat.vue"></script>
