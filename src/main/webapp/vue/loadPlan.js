var plan = new Vue({
    el: '#load_plan',

    data: {
        api: {
            save: '',
            update: '',
            findVehicleAPI: '',
            findDriverAPI: '',
            editVehicleAPI: '',
            editDriverAPI: ''
        },
        deal:0,
        quantity:0,
        unit:'',
        customers:[],
        plans:[],
        foundVehicles:[],
        foundDrivers:[],
        fnd:-1,
        upd:-1,
        picker:false
    },
    methods:{
        messages:function(){
            var msgs = [];
            var total = this.totalPlan();
            if (total > this.quantity) {
                msgs.push('Объем по сделке будет увеличен на ' + (total - this.quantity).toLocaleString() + ' ' + this.unit + '!');
            }
            return msgs;
        },
        newPlan:function(){
            this.add({
                date: new Date().toISOString().substring(0, 10),
                plan:0,
                customer:this.customers[0].id,
                transportation:{
                    vehicle:{},
                    driver:{}
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
                if (p.add.length || p.update.length || p.remove.length) {
                    console.log(p);
                    for (var a in p.add){
                        if (p.add.hasOwnProperty(a)) {
                            self.add(p.add[a])
                        }
                    }
                    for (var u in p.update){
                        if (p.update.hasOwnProperty(u)) {
                            self.update(p.update[u])
                        }
                    }
                    for (var r in p.remove){
                        if (p.remove.hasOwnProperty(r)){
                            self.remove(p.remove[r])
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
                vehicleInput:'',
                driverInput : '',
                item : plan
            });
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
            for (var r in this.plans){
                if (this.plans.hasOwnProperty(r)){
                    if (this.plans[r].item.id === id) {
                        this.plans.splice(r, 1);
                        break;
                    }
                }
            }
            this.plans.splice(id, 1);
        },
        save:function(){
            var parameters = {};
            parameters.dealId = this.deal;
            var plans = [];
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)) {
                    plans.push(this.plans[i].item);
                }
            }
            parameters.plans = plans;

            console.log(parameters);

            
            PostApi(this.api.save, parameters, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            }, null, true)
        },
        openVehicleInput:function(id){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    plan.editVehicle = plan.item.id == id;
                    plan.editDriver = false;
                    plan.vehicleInput = '';
                }
            }
        },
        closeVehicleInput:function(key){
            this.plans[key].editVehicle = false;
            this.plans[key].vehicleInput = '';
        },
        findVehicle:function(input){
            clearTimeout(this.fnd);

            var param = {};
            param.key = input;
            const self = this;
            this.fnd = setTimeout(function(){
                PostApi(self.api.findVehicleAPI, param, function(a){
                    self.findVehicles = a;
                })
            }, 500)
        },
        setVehicle:function(vehicle, key){
            this.plans[key].item.transportation.vehicle = vehicle;
        },
        editVehicle:function(value, key){
            var parameters = {};
            parameters.key = value;
            const self = this;
            loadModal(this.api.editVehicleAPI, parameters, function(a){
                self.plans[key].item.transportation.vehicle = a;
            })

        },
        openDriverInput:function(id){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    plan.editDriver = plan.item.id == id;
                    plan.editVehicle = false;
                    plan.driverInput = '';
                }
            }
        },
        closeDriverInput:function(key){
            this.plans[key].editDriver = false;
            this.plans[key].driverInput = '';
        },
        findDriver:function(input){
            if (input) {
                clearTimeout(this.fnd);
                var param = {};
                param.key = input;
                const self = this;
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findDriverAPI, param, function (a) {
                        self.findDrivers = a;
                    })
                }, 500)
            }
        },
        setDriver:function(driver, key){
            this.plans[key].item.transportation.driver = driver
        },
        editDriver:function(value, key){
            var parameters = {};
            parameters.key = value;
            const self = this;
            loadModal(this.api.editDriverAPI, parameters, function(a){
                self.plans[key].item.transportation.driver = a;
            })
        },
        weighs:function(weights){
            var total = 0;
            for (var i in weights){
                if (weights.hasOwnProperty(i)) {
                    var w = weights[i];
                    total += w.brutto == 0 || w.tara == 0 ? 0 : w.brutto - w.tara;
                }
            }
            return total;
        },
        totalPlan:function(){
            var total = 0;
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var p = parseFloat(this.plans[i].item.plan)
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
            const self =this;
            datepicker.show(function (date) {
                self.plans[key].item.date = date;
                self.sort();
            }, this.plans[key].item.date)

        },
        sort:function(){
            this.plans.sort(function(a, b){
                return new Date(a.item.date) - new Date(b.item.date)
            })
        }

    }
});