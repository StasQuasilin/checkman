select = new Vue({
    el:'#select',
    components:{
        'object-input':objectInput
    },
    data:{
        api:{},
        organisationProps:{},
        organisation:{
            id:-1
        },
        deal:-1,
        deals:[],
        typeNames:{}
    },
    methods:{
        putOrganisation:function (organisation) {
            this.organisation = organisation;
            this.getDeals();
        },
        getDeals:function () {
            if (this.organisation.id !== -1) {
                const self = this;
                PostApi(this.api.deals, {organisation: this.organisation.id}, function (a) {
                    if(a.status === 'success'){
                        self.deals = a.result;
                    }
                    self.selectDeal();
                })
            } else {
                this.deal = -1
            }
        },
        selectDeal:function () {
            if (this.deals.length > 0){
                let deal = this.deals[0];
                let products = deal.products;
                this.deal = products[0].id;
            } else {
                this.deal = -2
            }
        },
        save:function () {
            closeModal();
            let answer = {
                code:-1
            };
            if (this.deal > 0) {
                let done = false;
                for (let i in this.deals) {
                    if (this.deals.hasOwnProperty(i)) {
                        let d = this.deals[i];
                        let products = d.products;
                        for(let j = 0; j < products.length; j++){
                            let product = products[j];
                            if (product.id === this.deal){
                                answer.code = 0;
                                answer.deal = d;
                                answer.product = product;
                                done = true;
                                break;
                            }
                        }
                        if (done){
                            break;
                        }
                    }
                }
                answer.deals = this.deals;
            } else{
                answer.organisation = this.organisation;
            }
            saveModal(answer);
        }
    }
});