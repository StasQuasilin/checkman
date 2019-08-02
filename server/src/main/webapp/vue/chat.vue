var chat = new Vue({
    el:'#chat',
    data:{
        api:{
            send:''
        },
        show:false,
        contacts:[],
        chats:[],
        messages:[],
        unread:0,
        showContacts:false,
        selectedChat:-1,
        search:'',
        messageInput:'',
        worker:Settings.worker
    },
    methods:{
        selectChat:function(id){
            this.selectedChat = id;
            this.sortMessages();
        },
        sortMessages:function(){
            this.chats[this.selectedChat].messages.sort(function (a, b) {
                return new Date(a.time) - new Date(b.time);
            });
            const self = this;
            setTimeout(function(){
                var list = self.$refs.messagesList;
                list.scrollTop = list.scrollHeight;
            },0);

        },
        totalUnread:function(){
            var total = 0;
            for (var i in this.chats){
                if (this.chats.hasOwnProperty(i)){
                   total += this.chats[i].unread;
                }
            }
            return total;
        },
        showChat:function(){
            this.show = true;
            console.log('Show chat');
        },
        hideChat:function(){
            this.show = false;
            console.log('Hide chat');
        },
        switchChat:function(){
            this.show = !this.show;
            console.log('Switch chat view, show=\'' + this.show + '\'');
        },
        switchContacts:function(){
            this.showContacts = !this.showContacts;
        },
        clearSearch:function(){
            this.search = '';
        },
        handle:function(a){
            if (a.add){
                var contacts = a.add['contacts'];
                if (contacts){
                    for (var i in contacts){
                        if (contacts.hasOwnProperty(i)){
                            this.contacts.push(contacts[i])
                        }
                    }
                }
                var chats = a.add['chats'];
                if (chats){
                    for (var j in chats){
                        if(chats.hasOwnProperty(j)){
                            this.chats.push(chats[j]);
                        }
                    }
                }
            }
            if (a.addMessage){
                var message = a.addMessage;
                for (var m in message){
                    if (message.hasOwnProperty(m)){
                        var msg = message[m];
                        for (var c in this.chats){
                            if (this.chats.hasOwnProperty(c)){
                                var chat = this.chats[c];
                                if (chat.id === msg.chat){
                                    chat.messages.push(msg);
                                    if (chat.id === this.chats[this.selectedChat].id){
                                        this.sortMessages();
                                    }
                                }
                            }
                        }
                    }
                }

            }
        },
        openChat:function(worker){
            var haveChat = false;
            for (var i in this.chats){
                if (this.chats.hasOwnProperty(i)){
                    var chat = this.chats[i];
                    if(chat.members) {
                        if (chat.members.length == 1) {
                            if (chat.members[0].id === worker.id) {
                                this.selectChat(i);
                                haveChat = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!haveChat){
                console.log('No chat for ' + worker.person.value);
                this.chats.push({
                    id:-1,
                    title:worker.person.value,
                    members:[
                        worker
                    ],
                    messages:[]
                }, 0);
                this.selectChat(0);
                this.showContacts = false;
            }
        },
        sendMessage:function(){
            if (this.messageInput){
                var chatId = this.chats[this.selectedChat].id;
                var msg = {
                    chat: chatId,
                    message: {
                        id:-1,
                        text:this.messageInput
                    }
                };
                if (chatId == -1){
                    msg.members = this.chats[this.selectedChat].members;
                }

                console.log('Send ' + this.messageInput + ' to chat ' + chatId);
                const self = this;
                PostApi(this.api.send, msg, function(a){
                    if (a.status === 'success'){
                        self.clearMessage();
                    }
                });
            }

        },
        clearMessage:function(){
            this.messageInput = '';
        }

    }
});