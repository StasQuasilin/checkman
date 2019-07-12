var plan = new Vue({
    el: '#load_plan',
    data: {
        api: {
            save: '',
            findVehicle: '',
            parseVehicle:'',
            findDriver: '',
            parseDriver:'',
            editVehicle: '',
            editDriver: ''
        },
        deal:0,
        dateFrom:'',
        dateTo:'',
        quantity:0,
        unit:'',
        customers:[],
        plans:[],
        foundVehicles:[],
        foundDrivers:[],
        fnd:-1,
        upd:-1,
        picker:false,
        worker:{}
    },
    methods:{
        handler:function(a){

        },
        messages:function(){
            var msgs = [];
            var total = this.totalPlan();
            if (total > this.quantity) {
                msgs.push('Объем по сделке будет увеличен на ' + (total - this.quantity).toLocaleString() + '&nbsp;' + this.unit + '!');
            }
            return msgs;
        },
        newPlan:function(){
            var date = new Date();
            if (date < this.dateFrom){
                date = this.dateFrom;
            }else if (date > this.dateTo){
                date = this.dateTo;
            }
            this.add({
                id:-randomNumber(),
                date: date.toISOString().substring(0, 10),
                plan:0,
                customer:this.customers[0].id,
                transportation:{
                    vehicle:{
                        id:-1
                    },
                    driver:{
                        id:-1
                    },
                    notes:[],
                    weight:{}
                }
            })
        },
        loadPlan:function(){
            var plans = {};
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    if (plan.item) {
                        plans[plan.item.id] = plan.item.hash;
                    }
                }
            }
            const self = this;
            PostApi(this.api.update, {dealId : this.deal, plans: plans}, function(p){
                if (p.update.length) {
                    console.log(p);
                    for (var u in p.update){
                        if (p.update.hasOwnProperty(u)) {
                            self.update(p.update[u])
                        }
                    }
                    self.sort();
                }

            });
            this.upd = setTimeout(function () {
                self.loadPlan();
            }, 1000)
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
        },
        initSaveTimer:function(key){
            console.log('Init save for ' + key);
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    if(plan.key === key){
                        const p = plan;
                        const self = this;

                        clearTimeout(plan.saveTimer);

                        plan.saveTimer = setTimeout(function(){

                            self.save(p.item, key);
                        }, 2500);
                        break;
                    }
                }
            }
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
        remove:function(key){
            for (var r in this.plans){
                if (this.plans.hasOwnProperty(r)){
                    var item = this.plans[r];
                    if (item.key === key) {
                        if (item.item.id > 0){
                            console.log('mark as removed ' + key);
                            item.removed = true;
                        } else {
                            console.log('remove ' + key);
                            this.plans.splice(r, 1);
                        }
                    }
                }
            }
        },
        save:function(item, key){
            console.log('-------------');
            console.log('Save');
            const plan = item;
            console.log(plan);
            const self = this;
            PostApi(this.api.save, {dealId : this.deal, plan : item},function(a){
                if (a.status === 'success'){
                    plan.id = a.id;
                    //for (var i in self.plans){
                    //    if (self.plans.hasOwnProperty(i)){
                    //        var p = self.plans[i];
                    //        if (p.key === key){
                    //            p.item.id = a.id;
                    //            break;
                    //        }
                    //    }
                    //}
                }
            });
        },
        openVehicleInput:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    plan.editVehicle = plan.key == key;
                    plan.editDriver = false;
                    plan.vehicleInput = '';
                }
            }
            this.focusInput();
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
        closeVehicleInput:function(key){
            this.plans[key].editVehicle = false;
            this.plans[key].vehicleInput = '';
            this.foundVehicles=[];
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
        setVehicle:function(vehicle, key){
            console.log('---------');
            console.log('Set vehicle');
            console.log(vehicle);
            console.log('---------');
            this.plans[key].item.transportation.vehicle = vehicle;
            this.closeVehicleInput(key);
            this.initSaveTimer(this.plans[key].key);
        },
        editVehicle:function(value, key){
            if (value) {
                const self = this;
                PostApi(this.api.parseVehicle, {key: value}, function (a) {
                    console.log(a);
                    self.setVehicle(a, key);
                })
            }
        },
        openDriverInput:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    plan.editDriver = plan.key == key;
                    plan.editVehicle = false;
                    plan.driverInput = '';
                }
            }
            this.focusInput();
        },
        closeDriverInput:function(key){
            this.plans[key].editDriver = false;
            this.plans[key].driverInput = '';
            this.foundDrivers=[];
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
        setDriver:function(driver, key){
            this.plans[key].item.transportation.driver = driver;
            this.closeDriverInput(key);
            this.initSaveTimer(this.plans[key].key);
        },
        editDriver:function(value, key){
            if(value) {
                const self = this;
                PostApi(this.api.parseDriver, {key: value}, function (a) {
                    console.log(a);
                    self.setDriver(a, key);
                })
            }
        },
        cancel:function(key){
            this.setVehicle({}, key);
            this.setDriver({}, key);
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
        dateTimePicker:function(key){
            const self = this;
            datepicker.show(function (date) {
                self.plans[key].item.date = date;
                self.sort();
                self.initSaveTimer(self.plans[key].key);
            }, this.plans[key].item.date)
        },
        sort:function(){
            this.plans.sort(function(a, b){
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
        },
        saveNote:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var p = this.plans[i];
                    if (p.key === key){
                        p.item.transportation.notes.push({
                            id:-randomNumber(),
                            creator:this.worker,
                            note:p.noteInput
                        });
                        p.noteInput='';
                        p.editNote = false;
                        break;
                    }
                }
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
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var p = this.plans[i].item;
                    for (var j in p.transportation.notes){
                        if (p.transportation.notes.hasOwnProperty(j)){
                            var n = p.transportation.notes[j];
                            if (n.id === id){
                                p.transportation.notes.splice(j, 1);
                                break;
                            }
                        }
                    }
                }
            }
        }

    }
});