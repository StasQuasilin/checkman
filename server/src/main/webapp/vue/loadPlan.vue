var plan = new Vue({
    el: '#load_plan',
    data: {
        api: {},
        deal:-1,
        dateFrom:'',
        dateTo:'',
        customers:[],
        contractProducts:[],
        transportations:{},
        foundVehicles:[],
        foundDrivers:[],
        fnd:-1,
        upd:-1,
        picker:false,
        worker:{},
        currentTransportations:[]
    },
    methods:{
        messages:function(){
            var msgs = [];
            var total = this.totalPlan();
            if (total > this.quantity) {
                msgs.push('Объем по сделке будет увеличен на ' + (total - this.quantity).toLocaleString() + '&nbsp;' + this.unit + '!');
            }
            return msgs;
        },
        selectRow:function(id){
            console.log('select row ' + id);
            this.checkTransportations(id);
            for (let i in this.contractProducts){
                if (this.contractProducts.hasOwnProperty(i)){
                    this.contractProducts[i].selected = this.contractProducts[i].id === id;
                }
            }
            this.currentTransportations = this.transportations[id];
        },
        checkTransportations:function(id){
            if (!this.transportations[id]){
                Vue.set(this.transportations, id, []);
            }
        },
        newVehicle:function(id){
            this.addTransportation(id, {
                id:-randomNumber(),
                product:id,
                date:new Date().toISOString().substring(0, 10),
                plan:20,
                customer:this.customers[0].id,
                driver:{
                    id:-1
                },
                truck:{
                    id:-1
                },
                trailer:{
                    id:-1
                },
                notes:[]
            });

            console.log('new vehicle ' + id);
        },
        addTransportation:function(contractProduct, transportation){
            this.checkTransportations(contractProduct);
            let transportations = this.transportations[contractProduct];
            let item = {
                key:randomUUID(),
                editVehicle:false,
                editDriver:false,
                editNote:false,
                vehicleInput:'',
                driverInput : '',
                noteInput:'',
                item : transportation,
                removed:false,
                saveTimer:-1
            };
            transportations.push(item);
            this.initSaveTimer(item);
        },
        add:function(plan){
            this.plans.push({
                key : randomUUID(),
                editVehicle:false,
                editDriver:false,
                editNote:false,
                vehicleInput:'',
                driverInput : '',
                noteInput:'',
                item : plan,
                removed:false,
                saveTimer:-1
            });
            this.sort();
        },
        initSaveTimer:function(value){
            clearTimeout(value.saveTimer);
            const p = value;
            const self = this;
            value.saveTimer = setTimeout(function(){
                self.save(p.item, p.key);
            }, 1000);
        },
        update:function(plan){
            var found = false;
            for (var p in this.plans){
                if (this.plans.hasOwnProperty(p)){
                    if (!this.plans[p].item.id){
                        this.plans.splice(p, 1);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                this.add(plan);
            }
        },
        remove:function(id){
            const _id = id;
            var plan = this.plans[id].item;
            if (plan.id > 0){
                console.log('remove ' + plan.id);
                const self = this;
                PostApi(this.api.remove, {id:plan.id},function(a){
                    if (a.status === 'success'){
                        self.plans.splice(_id, 1);
                    }
                })
            } else {
                this.plans.splice(id, 1);
            }
        },
        save:function(item){
            PostApi(this.api.save, item, function(a){
                console.log(a);
                if (a.status === 'success'){
                    if(a.id) {
                        item.id = a.id;
                    }
                }
            });
        },

        focusInput:function(){
            setTimeout(function(){
                var inp = document.getElementById('input');
                if (inp){
                    inp.focus();
                }else {
                    console.log('input not found')
                }
            }, 100)
        },


        editVehicle:function(id){
            const vehicle = this.plans[id].item.transportation.vehicle;
            const _id = id;
            const self = this;
            if (vehicle.id){
                loadModal(this.api.editVehicle, {id: vehicle.id}, function(a){
                    self.plans[_id].item.transportation.vehicle = a;
                })
            }
        },
        editDriver:function(id){
            const driver = this.plans[id].item.transportation.driver;
            const _id= id;
            const self = this;
            if (driver.id){
                loadModal(this.api.editDriver, {id: driver.id},function(a){
                    self.plans[_id].item.transportation.driver = a;
                })
            }
        },

        parseVehicle:function(value, key){
            if (value) {
                const self = this;
                console.log('Parse ' + value);
                PostApi(this.api.parseVehicle, {key: value}, function (a) {
                    console.log(a);
                    if (a.status === 'success'){
                        if (a.vehicle) {
                            self.setVehicle(a.vehicle, key);
                        } else {
                            console.log('Alarm!!! Vehicle not set');
                        }
                    }

                })
            }
        },
        openDriverInput:function(key){
            for (let i in this.currentTransportations){
                if (this.currentTransportations.hasOwnProperty(i)){
                    let item = this.currentTransportations[i];
                    item.editDriver = item.key === key;
                    item.editVehicle = false;
                    item.driverInput = '';
                }
            }
            this.focusInput();
        },
        openVehicleInput:function(key){
            for (let i in this.currentTransportations){
                if (this.currentTransportations.hasOwnProperty(i)){
                    let item = this.currentTransportations[i];
                    item.editVehicle = item.key === key;
                    item.editDriver = false;
                    item.vehicleInput = '';
                }
            }
            this.focusInput();
        },
        closeDriverInput:function(value){
            value.editDriver = false;
            value.driverInput = '';
            this.foundDrivers=[];
        },
        closeVehicleInput:function(value){
            value.editVehicle = false;
            value.vehicleInput = '';
            this.foundVehicles=[];
        },
        findDriver:function(input){
            clearTimeout(this.fnd);
            if (input) {
                const self = this;
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findDriver, {key : input}, function (a) {
                        self.foundDrivers = a;
                    })
                }, 500)
            } else {
                this.foundDrivers=[];
            }
        },
        findVehicle:function(input){
            clearTimeout(this.fnd);
            if (input) {
                const self = this;
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findVehicle, {key: input}, function (a) {
                        self.foundVehicles = a;
                    })
                }, 500)
            } else {
                this.foundVehicles=[]
            }

        },
        setDriver:function(driver, value){
            console.log(driver);
            if (driver.truck){
                this.setVehicle(driver.truck, value);
            }
            value.item.driver = driver;
            this.closeDriverInput(value);
            this.initSaveTimer(value);
        },
        setVehicle:function(truck, value){
            if (truck.trailer){
                this.setTrailer(truck.trailer, value);
            }
            value.item.truck = truck;
            this.closeVehicleInput(value);
            this.initSaveTimer(value);
        },
        setTrailer:function(trailer, value){
            value.item.trailer = trailer;
            this.initSaveTimer(value);
        },
        parseDriver:function(value, key){
            if(value) {
                const self = this;
                PostApi(this.api.parseDriver, {key: value}, function (a) {
                    console.log(a);
                    if (a.status === 'success'){
                        if (a.driver){
                            self.setDriver(a.driver, key);
                        } else {
                            console.log('Alarm!!! Driver not set!!!')
                        }
                    }

                })
            }
        },
        cancelVehicle:function(id){
            this.openVehicleInput(this.plans[id].key)
        },
        cancelDriver:function(key){
            this.openDriverInput(key);
        },
        weight:function(item){
            if (item.weight == 'undefined') {
                console.log('calculate weight');
                var w = {
                    brutto:0,
                    tara:0,
                    netto:function(){
                        if(brutto > 0 && tara > 0) {
                            return brutto - tara;
                        } else {
                            return 0;
                        }
                    }
                };
                for (var i in item.transportation.weights) {
                    if (item.transportation.weights.hasOwnProperty(i)){
                        var weight = item.transportation.weights[i];
                        w.brutto += weight.brutto;
                        w.tara += weight.tara;
                    }
                }
                item.weight = w;
            }

            return item.weight;
        },
        totalPlan:function(){
            var total = 0;
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var p = parseFloat(this.plans[i].item.plan);
                    if (!isNaN(p)){
                        total += p;
                    }
                }
            }
            return total;
        },
        totalFact:function(){
            var total = 0;
            for (var p in this.plans){
                if (this.plans.hasOwnProperty(p)){
                    var plan = this.plans[p];
                    for(var w in plan.item.transportation.weights){
                        if (plan.item.transportation.weights.hasOwnProperty(w)){
                            var weight = plan.item.transportation.weights[w];
                            total += weight.brutto == 0 || weight.tara == 0 ? 0 : weight.brutto - weight.tara;
                        }
                    }
                }
            }
            return total;
        },
        stop:function(){
            console.log('Stop load plan');
            clearTimeout(this.upd);
        },
        dateTimePicker:function(value){
            const self = this;
            datepicker.show(function (date) {
                value.item.date = date;
                self.sort();
                self.initSaveTimer(value);
            }, value.item.date)
        },
        sort:function(){
            this.currentTransportations.sort(function(a, b){
                return new Date(b.item.date) - new Date(a.item.date)
            })
        },
        different:function(w, plan){
            var netto = w.netto;
            if (netto > 0  && netto !== plan){
                var d = netto - plan;
                if (d > 0) {
                    d = '+' + (d).toLocaleString();
                } else {
                    d = (d).toLocaleString();
                }
                return '(' + d + ')';
            }
        },
        addNote:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    this.plans[i].editNote = this.plans[i].key === key;
                }
            }
            this.focusInput();
        },
        saveNote:function(key){
            var plan = this.plans[key];
            if (plan.noteInput) {
                plan.item.transportation.notes.push({
                    id: -randomNumber(),
                    creator: this.worker,
                    note: plan.noteInput
                });
                plan.noteInput = '';
                plan.editNote = false;
                this.initSaveTimer(plan.key);
            }
        },
        closeNote:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    this.plans[i].editNote = !(this.plans[i].key === key);
                }
            }
        },
        removeNote:function(key, id){
            var p = this.plans[key].item;
            for (var j in p.transportation.notes){
                if (p.transportation.notes.hasOwnProperty(j)){
                    var n = p.transportation.notes[j];
                    if (n.id === id){
                        p.transportation.notes.splice(j, 1);
                        break;
                    }
                }
            }
            this.initSaveTimer(this.plans[key].key);
        }

    }
});