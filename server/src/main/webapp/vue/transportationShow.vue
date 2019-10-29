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
        already:false
    },
    methods: {
        setTimeIn: function () {
            if (!this.already) {
                this.already = true;
                const self = this;
                var parameters = {};
                parameters.id = this.id;
                PostApi(this.api.timeInApi, parameters, function (a) {
                    console.log(a);
                    self.timeIn = new Date(a.time).toLocaleTimeString().substring(0, 5);
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
        },
        setTimeOut: function () {
            if (!this.already) {
                this.already = true;
                const self = this;
                var parameters = {};
                parameters.id = this.id;
                PostApi(this.api.timeOutApi, parameters, function (a) {
                    console.log(a);
                    self.timeOut = new Date(a.time).toLocaleTimeString().substring(0, 5);
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
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