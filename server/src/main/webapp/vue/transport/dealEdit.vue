dealEdit = new Vue({
    el:'#dealEdit',
    components:{
        'object-input':objectInput
    },
    data:{
        api:{},
        deal:{
            id:-1,
            type:null,
            organisation:{
                id:-1
            },
            product:-1,
            price:0,
            shipper:-1,
            unit:-1
        },
        shippers:[],
        products:[],
        units:[],
        productActions:{},
        organisationProps:{},
        typeNames:{}
    },
    computed:{
        currentActions:function () {
            if (this.deal.product !== -1){
                return this.productActions[this.deal.product]
            }
        }
    },
    methods:{
        putOrganisation:function (organisation) {
            this.deal.organisation = organisation;
        },
        addType:function(action){
            let id = action.product.id;
            if (!this.productActions[id]){
                this.productActions[id] = [];
            }
            this.productActions[id].push(action.type);
        },
        check:function(){
            this.checkActions();
            this.checkUnit();
        },
        checkActions:function(){
            this.deal.type = this.currentActions[0];
        },
        checkUnit:function(){
            for (let i in this.products){
                if (this.products.hasOwnProperty(i)){
                    let p = this.products[i];
                    if (p.id === this.deal.product){
                        this.deal.unit = p.unit.id;
                        break;
                    }
                }
            }
        },
        save:function () {
            const self = this;
            let deal = {
                id: this.deal.id,
                type:this.deal.type,
                organisation:this.deal.organisation.id,
                product:this.deal.product,
                price:this.deal.price,
                shipper:this.deal.shipper,
                quantity:this.deal.quantity,
                unit:this.deal.unit
            };
            PostApi(this.api.save, deal, function (a) {
                if (a.status === 'success'){
                    console.log(a);
                    self.deal.id = a.id;
                    closeModal();
                    saveModal(self.deal);
                }
            })
        }
    }
});