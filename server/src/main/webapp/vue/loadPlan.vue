var plan = new Vue({
    el: '#load_plan',
    data: {
        api: {},
        deal:-1,
        dateFrom:'',
        dateTo:'',
        customers:[],
        contractProducts:[],
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
        newCarriage:function(){

        },
        selectRow:function(id){
            console.log('select row ' + id);
            for (let i in this.contractProducts){
                if (this.contractProducts.hasOwnProperty(i)){
                    this.contractProducts[i].selected = this.contractProducts[i].id === id;
                }
            }
        },
        newVehicle:function(id){
            //var date = new Date();
            //if (date < this.dateFrom){
            //    date = this.dateFrom;
            //}else if (date > this.dateTo){
            //    date = this.dateTo;
            //}
            //this.add({
            //    id:-randomNumber(),
            //    date: date.toISOString().substring(0, 10),
            //    plan:0,
            //    customer:this.customers[0].id,
            //    transportation:{
            //        vehicle:{
            //            id:-1
            //        },
            //        driver:{
            //            id:-1
            //        },
            //        notes:[],
            //        weight:{}
            //    }
            //})
            console.log('new vehicle ' + id);
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
                        }, 1500);
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
        save:function(item, key){
            console.log('-------------');
            console.log('Save');
            const plan = item;
            console.log(plan);
            const self = this;
            PostApi(this.api.save, {dealId : this.deal, plan : item},function(a){
                console.log(a);
                if (a.status === 'success'){
                    if(a.id) {
                        plan.id = a.id;
                    }
                }
            });
        },
        openVehicleInput:function(key){
            console.log('open vi ' + key);
            var any = false;
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    if (plan.key === key){
                        any = true;
                    }
                    plan.editVehicle = plan.key === key;
                    plan.editDriver = false;
                    plan.vehicleInput = '';
                }
            }
            if (any) {
                this.focusInput();
            }
        },
        focusInput:function(){
            setTimeout(function(){
                var inp = document.getElementById('input');
                if (inp){
                    inp.focus();
                }else {
                    console.log('input not found')
                }
            }, 500)
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
        setVehicle:function(vehicle, id){
            console.log('---------');
            console.log('Set vehicle');
            console.log(vehicle);
            console.log('---------');
            const _key = this.plans[id].key;
            this.plans[id].item.transportation.vehicle = vehicle;
            this.closeVehicleInput(id);
            this.initSaveTimer(_key);
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
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    plan.editDriver = plan.key === key;
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
        cancelDriver:function(id){
            this.openDriverInput(this.plans[id].key);
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
            const _key = this.plans[key].key;
            datepicker.show(function (date) {
                self.plans[key].item.date = date;
                self.sort();
                self.initSaveTimer(_key);
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