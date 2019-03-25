var plan = new Vue({
    el: '#load_plan',

    data:{
        api:{
            save_link:'',
            updateAPI:'',
            findVehicleAPI:'',
            findDriverAPI:'',
            editVehicleAPI:'',
            editDriverAPI:''
        },
        planId:0,
        deal:0,
        quantity:0,
        customer:'',
        customers:{},
        plans:{},
        findVehicles:{},
        findDrivers:{},
        fnd:-1,
        upd:-1
    },
    methods:{
        newPlan:function(){
            this.add({
                id:-1,
                date: new Date(),
                plan:0,
                customer:this.customers[this.customer].id,
                transportation:{
                    temp:randomUUID(),
                    vehicle:{},
                    driver:{}
                }
            })
        },
        loadPlan:function(){
            var parameters = {};
            parameters.deal_id = this.deal;
            var plans = {};
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var plan = this.plans[i];
                    if (plan.item) {
                        plans[plan.item.id] = plan.item.hash;
                    }
                }
            }
            parameters.plans = plans;
            const self = this;
            PostApi(this.api.updateAPI, parameters, function(p){
                if (p.add.length || p.update.length || p.remove.length) {
                    console.log(p);
                }
                for (var a in p.add){
                    self.add(p.add[a])
                }
                for (var u in p.update){
                    self.update(p.update[u])
                }
                setTimeout(function () {
                    self.loadPlan();
                }, 1000)
            })
        },
        add:function(plan){
            //plan.date = new Date(plan.date).toLocaleDateString();
            var item = {};
            item.editVehicle=false;
            item.editDriver=false;
            item.vehicleInput='';
            item.driverInput = '';

            item.item = plan;
            Vue.set(this.plans, plan.id, item);
            this.planId++
        },
        update:function(plan){
            this.plans[plan.id].item = plan;
        },
        remove:function(id){
            this.plans.splice(id, 1);
        },
        save:function(){
            var parameters = {};
            parameters.deal_id = this.deal;
            var plans = [];

            for (var i in this.plans){
                var p = this.plans[i].item;
                var plan = {};

                plan.id = p.id;
                plan.date = p.date;
                plan.plan = p.plan;
                plan.customer = p.customer;
                if (p.transportation.vehicle.id){
                    plan.vehicle = p.transportation.vehicle.id;
                }
                if (p.transportation.driver.id){
                    plan.driver = p.transportation.driver.id;
                }
                plans.push(plan);
            }
            parameters.plans = plans;

            console.log(parameters);

            PostApi(this.api.save_link, parameters, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            }, null, true)
        },
        addCustomer:function(id, name){
            if (!this.customer){
                this.customer = id
            }
            this.customers[id] = {
                id:id,
                name:name
            }
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
                    console.log(param);
                    PostApi(self.api.findDriverAPI, param, function (a) {
                        console.log(a);
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
                var w = weights[i];
                total += w.brutto == 0 || w.tara == 0 ? 0 : w.brutto - w.tara;
            }
            return total;
        },
        totalPlan:function(){
            var total = 0;
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    total += parseFloat(this.plans[i].item.plan);
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
        }

    }
});