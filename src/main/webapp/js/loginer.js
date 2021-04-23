loginer = {
    api:{},
    autologin:function(onSuccess) {
        let data = localStorage.getItem('access');
        if (data != null){
            let json = JSON.parse(data);
            this.login(json.uid, json.hash, onSuccess);
        } else {
            alert('ELSE!')
            // location.reload();
        }
    },
    login:function (uid, hash, onSuccess) {
        let access = {
            uid:uid,
            hash:hash
        };
        const self = this;
        PostApi(this.api.signin,
            access, function (a) {
                self.saveLastAccess(access);
                onSuccess(a)
            })
        },
    saveLastAccess:function(json){
        localStorage.setItem('access',JSON.stringify(json));
    },
};
