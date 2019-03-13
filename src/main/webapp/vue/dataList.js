var deamon = new Vue({
    el: '#container',
    data:{
        url:'',
        items:{
            1:{
                date:new Date().toLocaleDateString()
            },
            2:{
                date:new Date().toLocaleDateString()
            },
            3:{
                date:new Date().toLocaleDateString()
            }
        }
    },
    methods:{
        setUpdateUrl:function(url){
            this.url = url
        },
        day:function(date){
            return new Date(date).getDay()
        }
    }
})