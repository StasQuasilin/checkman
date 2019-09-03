var editor = new Vue({
    el: '#editor',
    data:{
        api:{},
        types:[],
        realisations:[],
        products:[],
        units:[],
        deal: {
            id: -1,
            type:'',
            date: new Date().toISOString().substring(0, 10),
            dateTo: new Date().toISOString().substring(0, 10),
            counterparty: -1,
            realisation: -1,
            product: -1,
            quantity: 0,
            unit:-1,
            price: 0
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
            e.quantity = this.deal.quantity <= 0;
            e.price = this.deal.price <= 0;

            if (!e.organisation && !e.quantity && !e.price) {
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
                    self.counterpartyInput = a.organisation.value;
                }
            });
        }
    }
});