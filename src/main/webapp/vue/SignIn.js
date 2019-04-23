var login = new Vue({
    el: '#login',
    data:{
        api:{
            find:'',
            signIn:''
        },
        foundUsers:[],
        user:{
            uid:'',
            password:''
        },
        worker:'',
        fnd:-1,
        err:'',
        cover:false,
        errors:{
            user:false,
            password:false
        }
    },
    methods:{
        findUser:function(){
            if (this.worker) {
                clearTimeout(this.fnd);
                const self = this;
                this.fnd = setTimeout(function () {
                    var parameters = {};
                    parameters.key = self.worker;
                    PostApi(self.api.find, parameters, function (a) {
                        self.foundUsers = a;
                    })
                }, 500)
            } else {
                this.user.uid = '';
                this.foundUsers = [];
            }
        },
        setUser:function(user){
            this.user.uid = user.uid;
            this.worker = user.person.value;
            this.foundUsers = [];
        },
        signIn:function(){
            this.errors.user = this.user.uid === '';
            this.errors.password = this.user.password === '';
            if (!this.errors.user && !this.errors.password) {
                if (this.user.uid && this.user.password) {
                    this.cover = true;
                    var user = {};
                    user.uid = this.user.uid;
                    user.password = btoa(this.user.password);
                    const self = this;
                    PostApi(this.api.signin,
                        {
                            uid:this.user.uid,
                            password:btoa(this.user.password)
                        }, function (a) {
                        console.log(a);
                        if (a.status == 'success') {
                            location.href = context + a['redirect'];
                        } else {
                            self.err = a['msd'];
                            self.cover = false;
                        }
                    })
                }
            }
        }
    }
});