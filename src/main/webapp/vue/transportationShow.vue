var show = new Vue({
    el: '#show',
    data:{
        api:{},
        id:-1,
        registrationTime:'',
        timeIn:'',
        timeOut:'',
        seals:[],
        offer:[],
        sealInput:'',
        foundSeals:[],
        fnd:-1,
        already:false,
        directionIn:'',
        directionOut:''
    },
    methods: {
        customRegTime:function(){
            const self = this;
            this.customTime('reg', function (a) {
                self.registrationTime = a;
            })
        },
        customTimeIn:function(){
            const self = this;
            this.customTime('in', function (a) {
                self.timeIn = a;
            })
        },
        customTimeOut:function(){
            const self = this;
            this.customTime('out', function (a) {
                self.timeOut = a;
            })
        },
        customTime:function(action, onSave){
            let data = {
                id:this.id,
                action:action
            };

            loadModal(this.api.customTime, data, onSave);
        },
        removeRegTime:function(){
            this.removeTime('reg');
            this.registrationTime = '';
        },
        removeTimeIn:function(){
            this.removeTime('in');
            this.timeIn = '';
        },
        removeTimeOut:function(){
            this.removeTime('out');
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
            } else {
                this.foundSeals = [];
            }
        },
        addSeal:function(seal, doOffer){
            let parameters = {};
            parameters.seal = seal.id;
            parameters.transportation = this.id;
            if (doOffer) {
                parameters.doOffer = doOffer;
            }
            var self = this;
            PostApi(this.api.saveSeal, parameters, function(a){
                if (a.status = 'success'){
                    self.seals.push(seal);
                    self.sealInput = '';
                    self.foundSeals = [];
                    self.seals.sort(function(a, b){
                        return a.number - b.number;
                    })
                }
                if (a.offer){
                    self.offer = a.offer;
                }
            })
        },
        addOffer:function(){
            for (var i in this.offer){
                if (this.offer.hasOwnProperty(i)){
                    this.addSeal(this.offer[i], false);
                }
            }
            this.offer = [];
        },
        removeSeal:function(key){
            var seal = this.seals[key];
            var self = this;
            PostApi(this.api.removeSeal, {seal : seal.id}, function(a){
                if (a.status = 'success'){
                    self.seals.splice(key, 1);
                }
            })

        }
    }
});