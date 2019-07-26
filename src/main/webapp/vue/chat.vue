var chat = new Vue({
    el:'#chat',
    data:{
        show:false,
        chats:[],
        messages:[],
        unread:0,
        selectedChat:-1
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
        }
    }
});