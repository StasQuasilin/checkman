var editor = new Vue({
    components:{
      'object-input':objectInput
    },
    el: '#editor',
    data:{
        api:{},
        types:{},
        shippers:[],
        products:[],
        customers:[],
        customerNames:{},
        units:[],
        deal: {
            id: -1,
            number:'',
            type:'',
            date: new Date().toISOString().substring(0, 10),
            from:new Date().toISOString().substring(0, 10),
            to: new Date().toISOString().substring(0, 10),
            counterparty: {
                id:-1
            },
            shipper: -1,
            product: -1,
            quantity: 0,
            unit:-1,
            price: 0
        },
        typeNames:{},
        errors:{
            organisation:false,
            quantity:false,
            price:false
        },
        organisationProps:{},
        picker:false,
        date:0
    },
    methods:{
        addCost:function(){
            console.log('+');
            if (!this.deal.costs){
                Vue.set(this.deal, 'costs', []);
            }
            let customer;
            let customers = Object.assign([], this.customers);

            for (let c in this.deal.costs){
                if (this.deal.costs.hasOwnProperty(c)){
                    let cost = this.deal.costs[c];
                    let customer = cost.customer;
                    customers.splice(customers.indexOf(customer), 1);
                }
            }
            if (customers.length > 0) {
                this.deal.costs.push({
                    id: -1,
                    customer: customers[0],
                    cost: 0
                })
            }
        },
        removeCost:function(idx){
            this.deal.costs.splice(idx , 1);
        },
        typesByProduct:function(){
            var types = this.types[this.deal.product];
            var fount = false;
            for (var i in types){
                if (types.hasOwnProperty(i)){
                    let t = types[i];
                    if (this.deal.type === t){
                        fount = true;
                        break;
                    }
                }
            }
            if (!fount && types){
                this.deal.type = types[0];
            }
            return types;
        },
        addType:function(action){
            if (!this.types[action.product.id]){
                this.types[action.product.id] = [];
            }
            this.types[action.product.id].push(action.type);
        },
        setCounterparty:function(counterparty){
            this.deal.counterparty = counterparty;
        },
        save:function(onSave){
            let e = this.errors;
            e.organisation = this.deal.counterparty.id === -1;
            e.quantity = this.deal.quantity <= 0;
            e.price = this.deal.price <= 0;

            if (!e.organisation && !e.quantity && !e.price) {
                let data = Object.assign({}, this.deal);
                data.counterparty = this.deal.counterparty.id;
                data.products = [{
                    product:data.product,
                    quantity:data.quantity,
                    unit:data.unit,
                    shipper:data.shipper,
                    price:data.price
                }];
                PostApi(this.api.save, data, function (a) {
                    if (a.status === 'success') {
                        closeModal();
                        if (onSave) {
                            onSave(a);
                        }
                    }
                })
            }
        },
        newCounterparty:function(){
            const self = this;
            this.foundOrganisations=[];
            loadModal(this.api.editCounterparty, {key:this.counterpartyInput}, function(a){
                console.log(a);
                if (a.status === 'success'){
                    if (a.organisation) {
                        self.setCounterparty(a.organisation);
                    }
                }
            })
        },
        saveAndClose:function(){
            this.save()
        },
        saveAndRedirect:function(){
            const self = this;
            this.save(function(a){
                console.log(a);
                loadModal(self.api.redirect + '?id=' + a.id);
            })
        },
        showDatePicker:function(){
            const self = this;
            datepicker.show(function(date){
                self.deal.date = date
            }, self.deal.date)
        },
        showDateToPicker:function(){
            const self = this;
            datepicker.show(function(date){
                self.deal.dateTo = date
            }, self.deal.dateTo)
        },
        editOrganisation:function(){
            const self = this;
            loadModal(this.api.editCounterparty + '?id=' + this.deal.counterparty, null, function(a){
                console.log(a);
                if (a.status === 'success'){
                    if (a.organisation) {
                        self.counterpartyInput = a.organisation.value;
                    }
                }
            });
        },
        cancelOrganisation:function(){
            this.deal.counterparty = -1;
            this.counterpartyInput = '';
        }
    }
});