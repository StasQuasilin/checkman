<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/chat/chat.css">
<html>
  <div>
    <c:set var="enterMessage"><fmt:message key="chat.enter.message"/></c:set>
    <c:set var="send"><fmt:message key="chat.send"/></c:set>
    <table style="width: 100%; height: 100%" border="1">
      <tr>
        <td valign="top" width="150pt">
          <div v-if="!showContacts">
            <div class="chat-title">
              <fmt:message key="chat.conversations"/>
              <span v-on:click="switchContacts" class="mini-close" style="float: right; position: absolute">
                +
              </span>
            </div>
            <table>
              <tr v-for="(value, key) in chats">
                <td v-on:click="selectChat(key)">
                  {{value.title}}
                </td>
              </tr>
            </table>
          </div>
          <div v-else style="height: 100%">
            <div class="chat-title">
              <fmt:message key="chat.contacts"/>
              <span v-on:click="switchContacts" class="mini-close" style="float: right; position: absolute">
                &times;
              </span>
            </div>
            <div class="chat-list">
              <table>
                <tr v-for="(value, key) in contacts" class="selected">
                  <td v-on:click="openChat(value)">
                    {{value.person.value}}
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </td>
        <td valign="top">
          <table v-if="selectedChat != -1" border="1" style="height: 100%; width: 100%">
            <tr>
              <td>
                <div class="chat-title">
                  <fmt:message key="chat.messages"/>
                </div>
              </td>
            </tr>
            <tr>
              <td height="100%" valign="bottom">
                <div v-for="(msg, key) in chats[selectedChat].messages">
                  <div v-if="message.sender.id == worker">
                    {{msg}}
                  </div>
                  <div v-else>
                    {{msg.message}}
                  </div>

                </div>
              </td>
            </tr>
            <tr>
              <td>
                <div style="display: flex">
                  <input placeholder=${enterMessage} v-on:keyup.enter="sendMessage"
                         v-on:keyup.escape="clearMessage" v-model="messageInput"
                         style="border: none; background: transparent; width: 100%">
                  <span title="${send}" class="mini-close" v-on:click="sendMessage">
                    >
                  </span>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html>