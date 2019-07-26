<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <div>
    <table style="width: 100%; height: 100%" border="1">
      <tr>
        <td valign="top" width="150pt">
          <div style="width: 100%; text-align: center">
            Chats
          </div>
          <table>
            <tr v-for="(value, key) in chats">
              <td v-on:click="selectChat(key)">
                {{value.title}}
              </td>
            </tr>
          </table>
        </td>
        <td valign="top">
          <div style="width: 100%; text-align: center">
            Messages
          </div>
          <table v-if="selectedChat != -1">
            <tr v-for="message in chats[selectedChat].messages">
              <td>
                {{message}}
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</html>
