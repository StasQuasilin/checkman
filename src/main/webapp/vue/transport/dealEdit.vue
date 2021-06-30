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
            products:[],
            price:0,
            shipper:-1,
            unit:-1
        },
        shippers:[],
        products:[],
        units:[],
        productActions:{},
        organisationProps:{},
        types:[],
        typeNames:{}
    },
    methods:{
        currentActions:function (product) {
            return this.productActions[product]
        },
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
        check:function(product){
            this.checkActions(product);
            this.checkUnit();
        },
        checkActions:function(product){
            this.deal.type = this.currentActions(product)[0];
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
                counterparty:this.deal.organisation.id,
                products:this.deal.products,
            };
            PostApi(this.api.save, deal, function (a) {
                if (a.status === 'success'){
                    closeModal();
                    saveModal(a);
                }
            })
        }
    }
});