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
    <table style="width: 100%; height: 100%; border-collapse: collapse">
      <tr style="border-collapse: collapse">
        <td valign="top" width="240pt" style="border-left: solid gray 1.2pt">
          <div v-if="!showContacts">
            <div class="chat-title">
              <fmt:message key="chat.conversations"/>
              <span v-on:click="switchContacts" class="mini-close" style="float: right; position: absolute">
                +
              </span>
            </div>
            <table width="100%">
              <tr v-for="(value, key) in chats">
                <td v-on:click="selectChat(key)">
                  <div style="border-bottom: solid gray 1pt">
                    <div style="display: inline-block" :class="{bold : value.unread > 0}">
                      {{value.title}}
                    </div>
                    <div style="display: inline-block color: darkgray; font-size: 10pt; font-style: italic; float: right">
                      <span v-if="value.message.time">
                        {{new Date(value.message.time).toLocaleTimeString().substring(0, 5)}}
                      </span>
                    </div>
                    <div style="color: darkgray; font-size: 10pt; font-style: italic;">
                      <span v-if="value.message.message">
                        {{value.message.message.substring(0, 23)}}
                      </span>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          <div v-else style="height: 100%">
            <div class="chat-title">
              <span v-on:click="switchContacts" class="mini-close">
                &times;
              </span>
              <fmt:message key="chat.contacts"/>
              <input id="groupChat" type="checkbox" v-model="groupChat">
              <label for="groupChat">
                <fmt:message key="group.chat"/>
              </label>
            </div>
            <div v-if="groupChat">
              <div>
                <c:set var="groupNameHolder"><fmt:message key="chat.group.name"/></c:set>
                <input id="groupName" placeholder="${groupNameHolder}" style="width: 100%; border-radius: 0" v-model="chatTitle">
              </div>
              <div style="width: 100%; text-align: center">
                <button v-on:click="selectAll">
                  <fmt:message key="chat.select.all"/>
                </button>
                <button v-on:click="createChat">
                  <fmt:message key="chat.group.create"/>
                </button>
              </div>
            </div>
            <c:set var="selectContact"><fmt:message key="chat.select.contact"/></c:set>
            <div class="message-list" style="overflow-y: scroll">
              <div v-for="(value, key) in contacts" class="selected" v-on:click="openChat(key)">
                <input v-if="groupChat" type="checkbox" title="${selectContact}" v-model="value.selected">
                {{value.person.value}}
              </div>
            </div>
          </div>
        </td>
        <td valign="top" style="border-left: solid gray 1.2pt; padding: 0">
          <table v-if="selectedChat != -1" style="height: 100%; width: 100%; border-collapse: collapse">
            <tr>
              <td class="chat-title">
                <div>
                  {{chats[selectedChat].title}}
                </div>
                <div v-if="chats[selectedChat].isGroup" style="width: 100%">
                  {{chats[selectedChat].members.length}}
                  <span v-if="chats[selectedChat].members.length < 5">
                    <fmt:message key="chat.members"/>
                  </span>
                  <span v-else>
                    <fmt:message key="chat.members.ge.5"/>
                  </span>
                  <span class="mini-close" style="float: right; transform: rotate(90deg)"
                      v-on:click="chatSettings()">
                    &#8230;
                  </span>
                </div>
              </td >
            </tr>
            <tr>
              <td height="100%" valign="bottom">
                <div v-if="chats[selectedChat].setting" style="height: 100%;">
                  <div class="setting-container">
                    <fmt:message key="chat.group.name"/>
                    <input id="groupChat" v-model="chatTitle">
                    <span class="mini-close">
                      <fmt:message key="button.save"/>
                    </span>
                  </div>
                  <div class="setting-container">
                    <button>
                      <fmt:message key="chat.remove"/>
                    </button>
                  </div>
                  <div class="setting-container">
                    <span>
                      <fmt:message key="chat.members"/>
                    </span>
                    <span class="mini-close">
                      <fmt:message key="chat.add.member"/>
                    </span>
                    <div style="overflow-y: scroll">
                      <div v-for="member in chats[selectedChat].members" style="padding: 4pt 2pt">
                        <span class="mini-close">
                          &times;
                        </span>
                        <span>
                          {{member.person.value}}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-else class="message-list" ref="messagesList">
                  <div v-for="(msg, key) in chats[selectedChat].messages" :class="{my : msg.sender.id !== worker}" class="message-wrapper">
                    <div class="message">
                      <div class="message-sender">
                        {{new Date(msg.time).toLocaleTimeString().substring(0, 5)}}&nbsp;{{msg.sender.person.value}}
                      </div>
                      <div class="message-text">
                        {{msg.message}}
                      </div>
                    </div>
                  </div>
                </div>
              </td>
            </tr>
            <tr v-if="!chats[selectedChat].setting">
              <td>
                <div style="display: flex">
                  <input placeholder="${enterMessage}" v-on:keyup.enter="sendMessage"
                         v-on:keyup.escape="clearMessage" v-model="messageInput"
                         style="border: solid darkgray 1pt; border-radius: 1pt; background: transparent; width: 100%">
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

