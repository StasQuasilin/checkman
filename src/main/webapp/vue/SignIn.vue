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
            const self = this;
            if (this.worker != '') {
                clearTimeout(this.fnd);
                this.fnd = setTimeout(function () {
                    PostApi(self.api.find, {key: self.worker}, function (a) {
                        self.foundUsers = a;
                    })
                }, 500)
            } else {
                this.user.uid = '';
                this.foundUsers = [];
                clearTimeout(this.fnd);
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
                console.log('!!!');
                if (this.user.uid && this.user.password) {

                    this.cover = true;
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
                } else {
                    console.error('Something wrong with uid')
                }
            }
        }
    }
});