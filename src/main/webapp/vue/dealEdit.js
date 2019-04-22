var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            findOrganisationUrl:'',
            parseOrganisationUrl:'',
            saveUrl:''
        },
        types:[],
        realisations:[],
        products:[],
        units:[],
        deal: {
            id: -1,
            type:'',
            date: new Date().toISOString().substring(0, 10),
            dateTo: new Date().toISOString().substring(0, 10),
            contragent: -1,
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
        contragentInput:'',
        contragentName:'',
        foundContragents:[],
        fnd:-1,
        picker:false,
        date:0
    },
    beforeDestroy:function(){
        console.log('Before destroy');
    },
    destroyed:function(){
        console.log('Destroyed');
    },
    methods:{
        findOrganisation:function(){
            this.errors.organisation = false;
            if (!this.contragentInput){
                this.deal.contragent = -1;
            }
            const self = this;
            if (event.keyCode >= 65 && event.keyCode <= 90) {
                if (this.contragentInput) {
                    clearTimeout(this.fnd);
                    this.fnd = setTimeout(function () {
                        PostApi(self.api.findOrganisationUrl, {key : self.contragentInput}, function (a) {
                            self.foundContragents = a;
                        })
                    }, 400)
                }
            } else if (event.key == 'Escape'){
                this.foundContragents = [];
                if (this.contragentName){
                    this.contragentInput = this.contragentName;
                } else {
                    this.contragentInput = '';
                }
            }
        },
        parseOrganisation:function(){
            if (this.foundContragents.length == 0 && this.contragentInput && this.contragentInput !== this.contragentName){
                const self = this;
                PostApi(this.api.parseOrganisationUrl, {name : this.contragentInput}, function(a){
                    console.log('Parsed organisation');
                    console.log(a);
                    self.deal.contragent = a.id;
                    self.contragentInput = self.contragentName = a.value;
                })
            }
        },
        setContragent:function(contragent){
            this.deal.contragent = contragent.id;
            this.contragentInput = this.contragentName = contragent.value;
            this.foundContragents = [];

        },
        save:function(){
            var e = this.errors;
            e.organisation = this.deal.contragent == -1;
            e.quantity = this.deal.quantity <= 0;
            e.price = this.deal.price <= 0;

            if (!e.organisation && !e.quantity && !e.price) {
                PostApi(this.api.saveUrl, this.deal, function (a) {
                    if (a.status == 'success') {
                        closeModal();
                    }
                })
            }
        },
        init:function(){
            if (this.realisations){
                this.deal.realisation= this.realisations[0].id;
            }
            if (this.products){
                this.deal.product = this.products[0].id;
            }
            if (this.units){
                this.deal.unit = this.units[0].id;
            }
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
        }
    }
});