var show = new Vue({
    el: '#show',
    data:{
        api:{
            timeInApi:'',
            timeOutApi:'',
            registration:'',
            findSeals:'',
            saveSeal:'',
            removeSeal:''
        },
        id:-1,
        registrationTime:'',
        timeIn:'',
        timeOut:'',
        seals:[],
        sealInput:'',
        foundSeals:[],
        fnd:-1,
        already:false,
        directionIn:'',
        directionOut:''
    },
    methods: {
        removeTimeIn:function(){
            this.removeTime(this.directionIn);
            this.timeIn = '';
        },
        removeTimeOut:function(){
            this.removeTime(this.directionOut);
            this.timeOut = '';
        },
        removeTime:function(dir){
            if (!this.already){
                this.already = true;
                const self = this;
                PostApi(this.api.removeTime, {id:this.id,dir:dir}, function(a){
                    self.already = false;
                }, function(e){
                    self.already = false;
                });
            }
        },
        setTimeIn: function () {
            this.setTime('in');
        },
        setTime:function(dir){
            if (!this.already) {
                this.already = true;
                const self = this;
                PostApi(this.api.timeApi, {id:this.id, dir:dir}, function (a) {
                     var date = new Date(a.time).toLocaleTimeString().substring(0, 5);
                    if(dir === 'in'){
                        self.timeIn = date
                    } else{
                        self.timeOut = date
                    }
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
        },
        setTimeOut: function () {
            this.setTime('out');
        },
        registration:function(){
            if (!this.already) {
                this.already = true;
                var self = this;
                PostApi(this.api.registration, {transportation: this.id}, function (a) {
                    if (a.status === 'success') {
                        self.registrationTime = new Date();
                    }
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
        },
        findSeals:function(){
            clearTimeout(this.fnd);
            if (this.sealInput) {

                const self = this;
                this.fnd = setTimeout(function () {
                    var param = {};
                    param.key = self.sealInput;
                    PostApi(self.api.findSeals, param, function (a) {
                        self.foundSeals = a;
                    })
                }, 200)
            }
        },
        addSeal:function(seal){
            var parameters = {};
            parameters.seal = seal.id;
            parameters.transportation = this.id;
            var self = this;
            PostApi(this.api.saveSeal, parameters, function(a){
                if (a.status = 'success'){
                    self.seals.push(seal);
                    self.sealInput = '';
                    self.foundSeals = [];
                }
            })

        },
        removeSeal:function(key){
            var seal = this.seals[key];
            var self = this;
            PostApi(this.api.removeSeal, {seal : seal.id}, function(a){
                if (a.status = 'success'){
                    self.seals.splice(key, 1);
                    console.log('remove seal \'' + key + '\'')
                }
            })

        }
    }
});