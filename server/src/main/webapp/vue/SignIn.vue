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
        state:0,
        worker:'',
        fnd:-1,
        err:'',
        cover:false,
        errors:{
            user:false,
            password:false
        }
    },
    mounted:function(){
        const self = this;
        setTimeout(function(){
            self.$refs.worker.select();
        },10);
    },
    methods:{
        check:function(){
            console.log(this.worker);
        },
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
            this.state = 1;
            const self = this;
            setTimeout(function(){
                self.$refs.password.select();
            },10);

        },
        back:function(){
            this.user.uid = '';
            this.worker = '';
            this.state = 0;
            const self = this;
            setTimeout(function(){
                self.$refs.worker.select();
            },10);
        },
        signIn2:function(){
            const self = this;
            console.log(this.worker);
            var worker = this.worker;
            var index = worker.indexOf(' ');
            var surname = worker.substring(0, index);
            PostApi(self.api.find, {key: surname}, function(a){
                if (a.length == 1){
                    self.user.uid = a[0].uid;
                    self.signIn();
                } else {
                    for (var i in a){
                        if (a.hasOwnProperty(i)){
                            if (a[i].person.value === worker){
                                self.user.uid = a[i].uid;
                                self.signIn();
                            }
                        }
                    }
                }
            })
        },
        signIn:function(){
            if (this.user.uid === ''){
                this.signIn2();
            }
            this.errors.user = this.user.uid === '';
            this.errors.password = this.user.password === '';

            if (!this.errors.user && !this.errors.password) {
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
                                location.href = (context + a['redirect']);
                            } else {
                                self.err = a['msd'];
                                self.cover = false;
                            }
                    })
                } else {
                    console.error('Something wrong with uid')
                }
            }
        },
        clearErrors:function(){
            this.errors.user = false;
            this.errors.password = false;
        }
    }
});