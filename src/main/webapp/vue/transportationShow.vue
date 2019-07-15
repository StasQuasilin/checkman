var show = new Vue({
    el: '#show',
    data:{
        api:{
            timeInApi:'',
            timeOutApi:'',
            findSeals:'',
            saveSeal:'',
            removeSeal:''
        },
        id:-1,
        timeIn:'',
        timeOut:'',
        seals:[],
        sealInput:'',
        foundSeals:[],
        fnd:-1
    },
    methods: {
        setTimeIn: function () {
            const self = this;
            var parameters = {};
            parameters.id = this.id;
            PostApi(this.api.timeInApi, parameters, function (a) {
                console.log(a);
                self.timeIn = new Date(a.time).toLocaleTimeString().substring(0, 5);
            })
        },
        setTimeOut: function () {
            const self = this;
            var parameters = {};
            parameters.id = this.id;
            PostApi(this.api.timeOutApi, parameters, function (a) {
                console.log(a);
                self.timeOut = new Date(a.time).toLocaleTimeString().substring(0, 5);
            })
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
            var parameters = {};
            parameters.seal = seal.id;
            var self = this;
            PostApi(this.api.removeSeal, parameters, function(a){
                if (a.status = 'success'){
                    self.seals.splice(key, 1)
                    console.log('remove seal \'' + key + '\'')
                }
            })

        }
    }
});