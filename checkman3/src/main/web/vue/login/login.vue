var login = new Vue({
    components:{
        'user-input':search
    },
    el: '#login',
    data:{
        api:{
            signIn:''
        },
        userProps:{},
        user:{
            uid:'',
            password:'',
            value:''
        },
        state:0,
        err:'',
        cover:false,
        users:{},
        errors:{
            user:false,
            password:false
        }
    },
    mounted:function() {
        this.users = this.getUserAccess();
        let keys = Object.keys(this.users);
        if (keys.length === 1){
            let key = keys[0];
            this.user.uid = key;
            this.user.value = this.users[key];
            this.state = 1;
        }

    },
    methods:{
        check:function(){
            console.log(this.worker);
        },
        getUserAccess:function(){
            let userString = localStorage.getItem('users');
            if (userString) {
                return JSON.parse(userString);
            }
            return {};
        },
        addUserAccess:function(uid, name){
            let users = this.getUserAccess();
            users[uid] = name;
            this.saveUserAccess(users);
        },
        saveUserAccess:function(acc){
            localStorage.setItem('users', JSON.stringify(acc));
        },
        removeUserAccess:function(uid){
            Vue.delete(this.users, uid);
            this.saveUserAccess(this.users);
        },
        setUser:function(uid, user){
            this.user.uid = uid;
            this.user.value = user;
            console.log(this.user);
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
            let worker = this.worker;
            let index = worker.indexOf(' ');
            let surname = worker.substring(0, index);
            PostApi(self.api.find, {key: surname}, function(a){
                if (a.length === 1){
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
                            if (a.status === 'success') {
                                self.addUserAccess(self.user.uid, self.user.value);
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