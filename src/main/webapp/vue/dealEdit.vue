dealEdit = new Vue({
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
            products:[],
            quantity: 0,
            unit:-1,
            price: 0,
            costs:[]
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
    computed:{
        totalSum:function () {
            let sum = 0;
            let products = this.deal.products;
            for (let i = 0; i < products.length; i++){
                let product = products[i];
                sum += product.quantity * product.price;
            }
            return sum;
        }
    },
    methods:{
        addCost:function(){
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
        typesByProduct:function(productId){
            let types = this.types[productId];
            let fount = false;
            for (let i in types){
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

        setCounterparty:function(counterparty){
            this.deal.counterparty = counterparty;
        },
        save:function(onSave){
            let e = this.errors;
            e.organisation = this.deal.counterparty.id === -1;
            e.quantity = this.deal.quantity < 0;
            e.price = this.deal.price < 0;

            if (!e.organisation && !e.quantity && !e.price) {
                let data = Object.assign({}, this.deal);
                data.counterparty = this.deal.counterparty.id;

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

        selectProduct:function(){
            if (this.products.length > 0){
                let type = this.deal.type;
                let interrupt = false;
                for(let i in this.products){
                    if (this.products.hasOwnProperty(i)){
                        let product = this.products[i];
                        let actions = this.types[product.id];
                        for (let j in actions){
                            if (actions.hasOwnProperty(j)){
                                let action = actions[j];
                                if (action === type){
                                    this.deal.product = product.id;
                                    interrupt = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(interrupt){
                        break;
                    }
                }
            }
        },
        addType:function(action){
            if (!this.types[action.product.id]){
                this.types[action.product.id] = [];
            }
            this.types[action.product.id].push(action.type);
        },
        addProduct:function(){
            let product = this.products[0];
            product.name = product.value;
            let shipper = this.shippers[0];
            shipper.name = shipper.value;
            this.deal.products.push({
                id:-1,
                productId:product,
                shipper:shipper,
                quantity:0,
                price:0
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