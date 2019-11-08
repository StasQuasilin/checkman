var editor = new Vue({
    el: '#editor',
    data:{
        api:{},
        types:[],
        shippers:[],
        products:[],
        units:[],
        deal: {
            id: -1,
            type:'',
            date: new Date().toISOString().substring(0, 10),
            dateTo: new Date().toISOString().substring(0, 10),
            counterparty: -1,
            number:'',
            dealProducts:[],
        },
        errors:{
            organisation:false,
            quantity:false,
            price:false
        },
        counterpartyInput:'',
        counterpartyName:'',
        foundOrganisations:[],
        fnd:-1,
        picker:false,
        date:0
    },
    methods:{
        total:function(){
            let total = 0;
            for (let i in this.deal.dealProducts){
                if (this.deal.dealProducts.hasOwnProperty(i)){
                    total += this.deal.dealProducts[i].total();
                }
            }
            return total;
        },
        removeDealProduct:function(idx){
            this.deal.dealProducts.splice(idx, 1);
        },
        newDealProduct:function(){
            this.addDealProduct({
                id:-1,
                type:this.types[0].id,
                product:this.products[0].id,
                amount:0,
                unit:this.units[0],
                price:0,
                shipper:this.shippers[0].id,
            })
        },
        addDealProduct:function(product){
            product.total = function(){
                return product.amount * product.price;
            };
            this.deal.dealProducts.push(product);
        },
        findOrganisation:function(){
            this.errors.organisation = false;
            if (!this.counterpartyInput){
                this.deal.contragent = -1;
            }
            const self = this;
            if (event.keyCode >= 65 && event.keyCode <= 90) {
                if (this.counterpartyInput) {
                    clearTimeout(this.fnd);
                    this.fnd = setTimeout(function () {
                        PostApi(self.api.findOrganisation, {key : self.counterpartyInput}, function (a) {
                            self.foundOrganisations = a;
                        })
                    }, 400)
                }
            } else if (event.key == 'Escape'){
                this.foundOrganisations = [];
                if (this.counterpartyName){
                    this.counterpartyInput = this.counterpartyName;
                } else {
                    this.counterpartyInput = '';
                }
            }
        },
        parseOrganisation:function(){
            if (this.foundOrganisations.length == 0 && this.counterpartyInput && this.counterpartyInput !== this.counterpartyName){
                const self = this;
                PostApi(this.api.parseOrganisation, { name : this.counterpartyInput }, function(a){
                    self.setCounterparty(a);
                });
            }
        },
        setCounterparty:function(counterparty){
            this.deal.counterparty = counterparty.id;
            this.counterpartyInput = this.counterpartyName = counterparty.value;
            this.foundOrganisations = [];

        },
        save:function(onSave){
            var e = this.errors;
            e.organisation = this.deal.counterparty == -1;

            if (!e.organisation) {
                PostApi(this.api.save, this.deal, function (a) {
                    if (a.status == 'success') {
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