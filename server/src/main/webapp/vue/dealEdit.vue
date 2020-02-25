var editor = new Vue({
    components:{
      'object-input':objectInput
    },
    el: '#editor',
    data:{
        api:{},
        types:{},
        realisations:[],
        products:[],
        units:[],
        deal: {
            number:'',
            id: -1,
            type:'',
            date: new Date().toISOString().substring(0, 10),
            dateTo: new Date().toISOString().substring(0, 10),
            counterparty: {
                id:-1
            },
            realisation: -1,
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
        typesByProduct:function(){
            var types = this.types[this.deal.product];
            var fount = false;
            for (var i in types){
                if (types.hasOwnProperty(i)){
                    var t = types[i];
                    if (this.deal.type == t){
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
            var e = this.errors;
            e.organisation = this.deal.counterparty.id == -1;
            e.quantity = this.deal.quantity <= 0;
            e.price = this.deal.price <= 0;

            if (!e.organisation && !e.quantity && !e.price) {
                var data = Object.assign({}, this.deal);
                data.counterparty = this.deal.counterparty.id;
                PostApi(this.api.save, data, function (a) {
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