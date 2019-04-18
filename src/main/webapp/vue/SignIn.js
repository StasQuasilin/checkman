var login = new Vue({
    el: '#app',
    data:{
        api:{
            find:'',
            signin:''
        },
        foundUsers:[],
        user:{
            uid:'',
            password:''
        },
        worker:'',
        fnd:-1,
        err:'',
        cover:false
    },
    methods:{
        findUser:function(){
            clearTimeout(this.fnd);
            const self = this;
            this.fnd = setTimeout(function(){
                var parameters = {};
                parameters.key = self.worker;
                PostApi(self.api.find, parameters, function (a) {
                    self.foundUsers = a;
                })
            }, 500)
        },
        setUser:function(user){
            this.user.uid = user.uid;
            this.worker = user.person.value;
            this.foundUsers = [];
        },
        signin:function(){
            if (this.user.uid && this.user.password) {
                this.cover = true;
                var user = {};
                user.uid = this.user.uid;
                user.password = btoa(this.user.password);
                const self = this;
                PostApi(this.api.signin, user, function(a){
                    console.log(a);
                    if(a.status == 'success'){
                        location.href=context + a['redirect'];
                    } else {
                        self.err = a['msd'];
                        self.cover = false;
                    }
                })
            }
        }
    }
});