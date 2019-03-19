var plan = new Vue({
    el: '#load_plan',

    data:{
        planId:0,
        save_link:'',
        update_link:'',
        deal:0,
        customer:'',
        customers:{},
        plan_list:{},
        user_id:'',
        total_plan:0,
        total_fact:0
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
            var parameters = [];
            parameters.deal_id = this.deal;
            const self = this;
            PostApi(this.update_link, parameters, function(e){
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

            plan.date = new Date(plan.date).toLocaleDateString()
            console.log(plan)
            Vue.set(this.plan_list, this.planId, plan)
            this.planId++
        },save:function(){
            for (var i in this.plan_list){
                var plan = this.plan_list[i];
                var parameters = [];

                if (plan.id != -1) {
                    parameters.id = plan.id;
                }
                parameters.deal_id = this.deal
                parameters.date = plan.date
                parameters.plan = plan.plan
                parameters.customer = plan.customer
                console.log(parameters)
                PostApi(this.save_link, parameters, function(a){
                    if (a.status == 'success'){

                    }
                }, true)
            }
        },
        addCustomer:function(id, name){
            if (!this.customer){
                this.customer = id
            }
            this.customers[id] = {
                id:id,
                name:name
            }
        }

    }
});