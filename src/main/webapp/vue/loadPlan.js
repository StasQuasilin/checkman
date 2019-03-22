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
        customer:'',
        customers:{},
        plans:{},
        findVehicles:{},
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
                    vehicle:{},
                    driver:{}
                }
            })
        },
        loadPlan:function(){
            var parameters = {};
            parameters.deal_id = this.deal;
            const self = this;
            PostApi(this.api.updateAPI, parameters, function(e){
                for (var i in e){
                    self.add(e[i])
                }
            }, function(e){
                console.log(
                    e
                )
            },true)
        },
        add:function(plan){
            //plan.date = new Date(plan.date).toLocaleDateString();
            var item = {};
            item.editVehicle=false;
            item.editDriver=false;
            item.vehicleInput='';
            item.driverInput = '';

            item.item = plan;
            Vue.set(this.plans, this.planId, item);
            this.planId++
        },
        save:function(){
            var parameters = {};
            parameters.deal_id = this.deal;
            parameters.plans = this.plans;
            console.log(parameters);
            //for (var i in this.plans){
            //    var plan = this.plans[i];
            //    if (plan.id != -1) {
            //        parameters.id = plan.id;
            //    }
            //
            //    parameters.date = plan.date
            //    parameters.plan = plan.plan
            //    parameters.customer = plan.customer
            //    console.log(parameters)
            //
            //}
            PostApi(this.save_link, parameters, function(a){
                if (a.status == 'success'){

                }
            }, true)
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
            clearTimeout(this.upd);

            var param = {};
            param.key = input;
            const self = this;
            this.upd = setTimeout(function(){
                PostApi(self.api.findVehicleAPI, param, function(a){
                    self.findVehicles = a;
                })
            }, 500)
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
        }

    }
});