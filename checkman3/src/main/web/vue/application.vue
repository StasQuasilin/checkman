applicaton = new Vue({
    el:'#app',
    data:{
        staticMenu:true,
        showMenu:true,
        content:''
    },
    methods:{
        loadPage:function (url) {
            const self = this;
            PostReq(url, null, function (a) {
                self.content = a;
            })
        }
    }
});