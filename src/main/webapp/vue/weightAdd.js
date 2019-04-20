var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            findOrganisation:'',
            parseOrganisation:'',
            findDeals:'',
            findVehicle:'',
            parseVehicle:'',
            findDriver:'',
            parseDriver:'',
            save:''
        },
        types:{},
        products:[],
        units:[],
        deals:[],
        visibles:[],
        customers:{},
        plan:{
            type:'sell',
            date:new Date().toISOString().substring(0, 10),
            deal:-1,
            organisation:-1,
            product:-1,
            plan:20,
            from:-1,
            price:0,
            unit:-1,
            customer:'',
            vehicle:-1,
            driver:-1
        },
        input:{
            organisation:'',
            vehicle:'',
            driver:''
        },
        foundOrganisations:[],
        foundVehicles:[],
        foundDrivers:[]
    },
    methods:{
        productList:function(){
            if (this.plan.deal == -1){
                return this.products;
            } else {
                var products = [];
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.plan.deal){
                            products.push(deal.product);
                            this.plan.product = deal.product.id;
                            break;
                        }
                    }
                }
                return products;
            }
        },
        visibleList:function(){
            if (this.plan.deal == -1){
                return this.visibles;
            } else {
                var froms = [];
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.plan.deal){
                            froms.push(deal.visibility);
                            this.plan.from = deal.visibility;
                            break;
                        }
                    }
                }
                return froms;
            }
        },
        typeList:function(){
            if (this.plan.deal == -1){
                return this.types;
            } else {
                var types = {};
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.plan.deal){
                            types[deal.type] = this.types[deal.type];
                            this.plan.type = deal.type;
                            break;
                        }
                    }
                }
                return types;
            }
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.plan.date = date;
            }, self.plan.date)
        },
        findOrganisation:function(){
            if (this.input.organisation) {
                const self = this;
                PostApi(this.api.findOrganisation, {key: this.input.organisation}, function (a) {
                    self.foundOrganisations = a;
                })
            } else {
                this.foundOrganisations = []
                this.plan.organisation = -1;
            }
        },
        parseOrganisation:function(){
            if (this.foundOrganisations.length == 0 && this.input.organisation){
                const self = this;
                PostApi(this.api.parseOrganisation, {name:this.input.organisation}, function(a){
                    self.plan.organisation = a.id;
                    self.input.organisation = a.value;
                })
            } else {
                this.foundOrganisations = [];
            }
        },
        putOrganisation:function(organisation){
            this.plan.organisation = organisation.id;
            this.input.organisation = organisation.value;
            this.findDeals(organisation.id);
            this.foundOrganisations = []
        },
        findDeals:function(id){
            const self = this;
            PostApi(this.api.findDeals, {organisation:id}, function(a){
                self.deals = a;
                var now = new Date(self.plan.date);
                for (var i in a){
                    if (a.hasOwnProperty(i)){
                        var deal = a[i];
                        var from = new Date(deal.date);
                        var to = new Date(deal.date_to);
                        if (now >= from && now <= to){
                            self.plan.deal = deal.id;
                        }
                    }
                }
            })
        },
        getPrice: function(){
            for (var d in this.deals){
                if (this.deals.hasOwnProperty(d)){
                    var deal = this.deals[d];
                    if (deal.id === this.plan.deal){
                        return this.plan.price = deal.price;
                    }
                }
            }
        },
        findVehicle:function(){
            if (this.input.vehicle) {
                const self = this;
                PostApi(this.api.findVehicle, {key: this.input.vehicle}, function(a){
                    self.foundVehicles = a;
                })
            } else {
                this.foundVehicles =[]
                this.plan.vehicle = -1;
            }

        },
        parseVehicle:function(){
            if (this.foundVehicles.length == 0 && this.input.vehicle){
                const self = this;
                loadModal(this.api.parseVehicle, {key:this.input.vehicle}, function(v){
                    self.plan.vehicle = v.id;
                    self.input.vehicle = v.model + ' \'' + v.number + '\' ' + v.trailer;
                })
            }
        },
        putVehicle:function(vehicle){
            this.plan.vehicle = vehicle.id;
            this.input.vehicle = vehicle.model + ' \'' + vehicle.number + '\' ' + vehicle.trailer
            this.foundVehicles = [];
        },
        findDriver:function(){
            if (this.input.driver){
                const self = this;
                PostApi(this.api.findDriver, {key:this.input.driver}, function(a){
                    self.foundDrivers = a;
                })
            } else {
                this.foundDrivers = [];
                this.plan.driver = -1;
            }
        },
        parseDriver:function(){
            if (this.foundDrivers.length == 0 && this.input.driver){
                const self = this;
                loadModal(this.api.parseDriver, {key:this.input.driver}, function(d){
                    self.plan.driver = d.id;
                    self.input.driver = d.person.value;
                })
            }
        },
        putDriver:function(driver){
            this.plan.driver = driver.id;
            this.input.driver = driver.person.value;
            this.foundDrivers = [];
        },
        save:function(){
            PostApi(this.api.save, this.plan, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});