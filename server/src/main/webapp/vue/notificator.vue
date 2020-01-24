var notificator = new Vue({
    el:'#notificator',
    data:{
        notifications:[],
        show:0
    },
    methods:{
        notify:function(notification){
            notification.date = new Date();
            notification.uid = randomUUID();
            this.createTimer(notification);
            this.notifications.unshift(notification);
        },
        closeAll:function(){
            while(this.notifications.length > 0){
                this.closeNotification(0);
            }
        },
        closeNotification:function(idx){
            clearTimeout(this.notifications[idx]);
            this.notifications.splice(idx, 1);
        },
        hover:function(notification){
            clearTimeout(notification.timer)
        },
        leave:function(notification){
            this.createTimer(notification);
        },
        createTimer:function(notification){
            if (!notification.waitAnswer) {
                const self = this;
                notification.timer = setTimeout(function () {
                    for (var i in self.notifications) {
                        if (self.notifications.hasOwnProperty(i)) {
                            if (self.notifications[i].uid === notification.uid) {
                                self.closeNotification(i);
                                break;
                            }
                        }
                    }
                }, 5000);
            }
        },
        test:function(){
            if (this.show < 50){
                this.show++;
                this.notify({
                    text:'Test text!! #@(?$0'
                });
                const self = this;
                setTimeout(function(){
                    self.test();
                }, 5000 * Math.random());

            }
        }
    }
});