<html>
    <link rel="stylesheet" href="${context}/css/Notificator.css">
    <div id="notificator" class="notification-line">
        <transition-group name="slide-fade">
            <div class="notification-line-item" :key="notification.uid" v-on:mouseenter="hover(notification)"
                 v-on:mouseleave="leave(notification)"
                 v-for="(notification, notificationIdx) in notifications">
                <div class="notification-title">
                    <span class="notification-time">
                        <template v-if="notification.date">
                            {{notification.date.toLocaleTimeString().substring(0, 5)}}
                        </template>
                        <template v-else-if="notification.time">
                            {{new Date(notification.time).toLocaleTimeString().substring(0, 5)}}
                        </template>
                        <template v-else>
                            {{new Date().toLocaleTimeString().substring(0, 5)}}
                        </template>
                    </span>
                    <span v-if="notification.author">
                        {{notification.author.value}}
                    </span>
                    <span class="mini-close" v-on:click="closeNotification(notificationIdx)">
                        &times;
                    </span>
                </div>
                <div class="notification-text" v-html="notification.text"></div>
            </div>
        </transition-group>
        <div class="notification-close-all mini-close" v-if="notifications.length > 1">
                <a v-on:click="closeAll()">Close all</a>
        </div>
    </div>
    <script src="${context}/vue/notificator.vue"></script>
    <script>
        //notificator.test();
    </script>
</html>