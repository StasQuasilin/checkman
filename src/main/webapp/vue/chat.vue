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
        messageInput:''
    },
    methods:{
        selectChat:function(id){
            this.selectedChat = id;
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
                    ]
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
                PostApi(this.api.send, msg, function(a){
                    if (a.status === 'success'){
                        this.clearMessage();
                    }
                });
            }

        },
        clearMessage:function(){
            this.messageInput = '';
        }

    }
});