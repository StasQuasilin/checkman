var holder = new Vue({
    el: '#chatHolder',
    data:{
        show:false
    },
    methods:{
        showChat:function(){
            this.show = true;
        },
        hideChat:function(){
            this.show = false;
        },
        switchChat:function(){
            this.show = !this.show;
        },
        unread:function(){
            return chat.unread;
        }
    }
});