var language = new Vue({
    el:'#languageBase',
    data:{
        api:{
            save:''
        },
        languages:[],
        language:'',
        lang:''
    },
    methods:{
        save:function(){
            PostApi(this.api.save, {lang : this.language}, function(a){
                if (a.status === 'success'){
                    location.reload()
                }
            })
        }

    }
});