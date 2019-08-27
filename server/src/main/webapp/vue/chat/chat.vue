var chat = new Vue({
    el:'#chat',
    data:{
        api:{
            send:'',
            get:''
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
        worker:Settings.worker,
        windowWidth:0
    },
    created:function(){
        window.addEventListener('resize', this.handleResize);
        this.handleResize();
    },
    methods:{
        handleResize:function(){
            this.windowWidth = window.innerWidth;
        },
        oversize:function(){
            return this.windowWidth > Settings.switchWidth;
        },
        selectChat:function(id){
            this.selectedChat = id;
            const self = this;
            var chat = self.chats[this.selectedChat];
            var chatId = chat.id;

            if (chat.messages.length == 0) {
                PostApi(this.api.get, {chat: chatId}, function (a) {
                    console.log(a);
                    if (a.messages) {
                        for (var i in a.messages) {
                            if (a.messages.hasOwnProperty(i)) {
                                self.updateMessage(a.messages[i])
                            }
                        }
                    }
                });
            }
            self.sortMessages();

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
        },
        hideChat:function(){
            this.show = false;
        },
        switchChat:function(){
            this.show = !this.show;
        },
        switchContacts:function(){
            this.showContacts = !this.showContacts;
        },
        clearSearch:function(){
            this.search = '';
        },
        sortConversations:function(){
            var d1, d2;
            this.chats.sort(function(a, b){
                d1 = new Date(b.message.time);
                d2 = new Date(a.message.time);
                return d1 - d2;
            });
        },
        updateMessage:function(message){
            for (var c in this.chats) {
                if (this.chats.hasOwnProperty(c)) {
                    var chat = this.chats[c];
                    if (chat.id === message.chat) {
                        chat.messages.push(message);
                        chat.message = message;
                        this.sortConversations();
                        if (this.selectedChat != -1) {
                            if (chat.id === this.chats[this.selectedChat].id) {
                                this.sortMessages();
                            }
                        } else {
                            chat.unread++;
                        }
                        break;
                    }
                }
            }
        },
        updateChat:function(chats){
            var noChat = true;
            console.log(chats);
            if (chats.key) {
                for (var i in this.chats) {
                    if (this.chats.hasOwnProperty(i)) {
                        var chat = this.chats[i];
                        if (chat.key === chats.key) {
                            chat.id = chats.id;
                            noChat = false;
                            break;
                        }
                    }
                }
            }
            if (noChat) {
                this.addChat(chats);
            }
        },
        addChat:function(chat){

            if (!chat.key){
                chat.key = randomUUID();
            }
            chat.unread = 0;
            chat.messages = [];
            this.chats.unshift(chat);
            this.sortConversations();

        },
        updateContact:function(contacts){
            var noContact = true;
            for (var i in this.contacts){
                if (this.contacts.hasOwnProperty(i)){
                    var contact = this.contacts[i];
                    if (contact.id === contacts.id){
                        this.contacts.splice(i, 1, contacts);
                        noContact = false;
                        break;
                    }
                }
            }
            if (noContact){
                this.addContact(contacts);
            }
        },
        addContact:function(contact){
            this.contacts.push(contact);
            this.contacts.sort(function(a, b){
                return a.person.value.localeCompare(b.person.value);
            })
        },
        handle:function(a){
            if (a.update){
                if (a.update.length){
                    for (var m in a.update){
                        if (a.update.hasOwnProperty(m)) {
                            var chats = a.update[m].chats;
                            if (chats){
                                this.updateChat(chats);
                            }
                            var message = a.update[m].messages;
                            if (message) {
                                this.updateMessage(message);
                            }
                            var contacts = a.update[m].contacts;
                            if (contacts){
                                this.updateContact(contacts);
                            }
                        }
                    }
                } else {
                    if (a.update.chats) {
                        for (var i in a.update.chats) {
                            if (a.update.chats.hasOwnProperty(i)) {
                                this.updateChat(a.update.chats[i]);
                            }
                        }
                    }
                    if (a.update.contacts) {
                        for (var j in a.update.contacts) {
                            if (a.update.contacts.hasOwnProperty(j)){
                                this.updateContact(a.update.contacts[j]);
                            }
                        }
                    }
                    if (a.update.messages){
                        for (var n in a.update.messages){
                            if (a.update.messages.hasOwnProperty(n)){
                                this.updateMessage(a.update.messages[n]);
                            }
                        }
                    }
                }
            }
        },
        openChat:function(worker){
            console.log('Open chat for ' + worker.person.value);
            var haveChat = false;
            for (var i in this.chats){
                if (this.chats.hasOwnProperty(i)){
                    var chat = this.chats[i];
                    if(chat.members) {
                        for (var a in chat.members){
                            if (chat.members.hasOwnProperty(a)){
                                if(chat.members[a].id === worker.id){
                                    this.selectChat(i);
                                    haveChat = true;
                                    this.showContacts = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (!haveChat){
                console.log('No chat for ' + worker.person.value);
                this.addChat({
                    id:-1,
                    title:worker.person.value,
                    members:[
                        worker
                    ],
                    messages:[],
                    message:{
                        time:new Date().toISOString(),
                        message:''
                    }

                });
                this.selectChat(0);
                this.showContacts = false;
            } else{

            }
        },
        sendMessage:function(){
            if (this.messageInput){
                var chat = this.chats[this.selectedChat];
                var chatId = chat.id;
                var msg = {
                    chat: chatId,
                    key:chat.key,
                    message: {
                        id:-1,
                        text:this.messageInput
                    }
                };
                if (chatId == -1){
                    msg.members = this.chats[this.selectedChat].members;
                }

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