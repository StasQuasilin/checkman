var editor = new Vue({
    el: '#editor',
    data:{
        api:{},
        types:{},
        products:[],
        units:[],
        deals:[],
        visibles:[],
        customers:{},
        plan:{},
        input:{
            organisation:'',
            vehicle:'',
            driver:'',
            editDriver:false,
            editVehicle:false
        },
        foundOrganisations:[],
        foundVehicles:[],
        foundDrivers:[],
        errors:{
            organisation:false,
            product:false,
            manager:false,
            vehicle:false,
            driver:false,
            any:function(){
                return organisation || product || manager;
            }
        },
        managers:[],
        role:'',
        note:{
            key:-1,
            edit:false,
            id:-1,
            input:''
        }
    },
    methods:{
        editNote:function(note, key){
            this.note.edit = true;
            if (key){
                this.note.key = key;
            }
            if (note) {
                this.note.id = note.id;
                this.note.input = note.note;
            }
            const self = this;
            setTimeout(function(){
                self.$refs.comment.select();
            }, 10)
        },
        saveNote:function(){
            if (this.note.input) {
                if (this.note.key != -1) {
                    let note = this.plan.notes[key];
                    note.note = this.note.input;
                } else {
                    this.plan.notes.push({
                        creator: this.worker,
                        note: this.note.input
                    });
                }
                this.clearNote();
            }
        },
        removeNote:function(key){
            this.plan.notes.splice(key, 1);
        },
        setQuantity:function(){
            let quantity = 0;
            for (let i in this.deals){
                if (this.deals.hasOwnProperty(i)){
                    if (this.deals[i].id == this.plan.deal){
                        quantity = this.deals[i].quantity;
                    }
                }
            }
            this.plan.quantity = quantity;
        },
        clearNote:function(){
            this.note = {
                key:-1,
                edit:false,
                id:-1,
                input:''
            }
        },
        cancelOrganisation:function(){
            this.plan.organisation = -1;
            this.input.organisation = '';
        },
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
        newCounterparty:function(){
            const self = this;
            self.foundOrganisations = [];
            loadModal(this.api.editCounterparty, {key : this.input.organisation}, function(a){
                console.log(a);
                if (a.status == 'success'){
                    self.putOrganisation(a.organisation);
                }
            })
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
                this.foundOrganisations = [];
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
            } else if (this.foundOrganisations.length > 0){
                this.putOrganisation(this.foundOrganisations[0]);
                this.foundOrganisations = [];
            }
        },
        putOrganisation:function(organisation){
            console.log(organisation);
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
                this.foundVehicles =[];
                this.plan.vehicle = {};
            }

        },
        parseVehicle:function(){
            if (this.input.vehicle){
                const self = this;
                PostApi(this.api.parseVehicle, {key:this.input.vehicle}, function(v){
                    if (v.status === 'success'){
                        if (v.vehicle){
                            self.plan.vehicle = v.vehicle;
                            self.input.vehicle = '';
                        } else {
                            console.log('Ahtung!!! Vehicle not set!!!')
                        }
                    }

                })
            }
        },
        editVehicle:function(){
            const self = this;
            loadModal(this.api.editVehicle, this.plan.vehicle, function(a){
                self.plan.vehicle = a;
            })
        },
        putVehicle:function(vehicle){
            console.log(vehicle);
            this.plan.vehicle = vehicle;
            this.input.vehicle = '';
            this.foundVehicles = [];
        },
        cancelVehicle:function(){
            this.plan.vehicle = {
                id:-1
            }
        },
        findDriver:function(){
            if (this.input.driver){
                const self = this;
                PostApi(this.api.findDriver, {key:this.input.driver}, function(a){
                    self.foundDrivers = a;
                })
            } else {
                this.foundDrivers = [];
                this.plan.driver = {};
            }
        },
        parseDriver:function(){
            if (this.input.driver){
                const self = this;
                PostApi(this.api.parseDriver, {key:this.input.driver}, function(d){
                    console.log(d);
                    if (d.status === 'success'){
                        if (d.driver){
                            self.plan.driver = d.driver;
                            self.input.driver = '';
                        } else {
                            console.log('Ahtung!!! Driver not set')
                        }
                    }
                })
            }
        },
        editDriver:function(){
            const self = this;
            loadModal(this.api.editDriver, this.plan.driver, function(a){
                self.plan.driver = a;
                self.input.driver = '';
            });
        },
        putDriver:function(driver){
            this.plan.driver = driver;
            this.input.driver = '';
            this.foundDrivers = [];
        },
        cancelDriver:function(){
            this.plan.driver = {
                id:-1
            }
        },
        save:function(){
            var e = this.errors;
            if (this.plan.vehicle.id == -1 && this.input.vehicle){
                if ( this.foundVehicles){
                    e.vehicle = true;
                } else {
                    this.parseVehicle();
                }
            }
            if (this.note.edit){
                this.saveNote();
            }

            e.organisation = this.plan.organisation == -1;
            e.product = this.plan.product == -1;
            e.manager = this.plan.manager == -1;
            console.log(this.plan);
            console.log(e);
            if (!e.organisation && !e.product) {
                PostApi(this.api.save, this.plan, function (a) {
                    if (a.status === 'success') {
                        closeModal();
                    }
                })
            }
        },
        editOrganisation:function(){
            const self = this;
            loadModal(this.api.editCounterparty + '?id=' + this.plan.organisation, null, function(a){
                console.log(a);
                if (a.status === 'success'){
                    self.input.organisation = a.organisation.value;
                }
            });
        }
    }
});