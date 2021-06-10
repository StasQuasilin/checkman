analysesEdit = new Vue({
    el:'#editor',
    data:{
        api:{},
        products:[],
        helpers: {
            type:-1,
            types:[]
        }
    },
    methods:{
        recount:function(item){
            const basis = {
                humidity:7,
                soreness:3
            };
            let humidity = (item.humidity1 > 0 || item.humidity2 > 0 ? (
                (item.humidity1 + item.humidity2) / ((item.humidity1 > 0 ? 1 : 0) + (item.humidity2 > 0 ? 1 : 0))
            ) : 0);
            let soreness = item.soreness;

            let recount = 0;
            if (humidity > basis.humidity && soreness > basis.soreness){
                recount = 100-((100-humidity)*(100-soreness)*100)/((100-basis.humidity)*(100-basis.soreness));
            } else if (humidity > basis.humidity) {
                recount = (humidity - basis.humidity)*100 / (100 - basis.humidity)
            } else if (soreness > basis.soreness){
                recount = (soreness - basis.soreness)*100 / (100 - basis.soreness);
            }
            let r = (Math.round(recount * 1000) / 1000);
            item.recount = r;
            return r;
        },
        saveLogic:function(onSave){
            if (this.api.save && !this.already) {
                const self = this;
                this.already = true;
                let data = {
                    products:[]
                };
                for (let i = 0; i < this.products.length; i++){
                    let product = this.products[i];
                    delete product.dealProduct;
                    delete product.deal;
                    delete product.amount;
                    delete product.quantity;
                    delete product.shipperId;
                    delete product.productId;
                    delete product.unitName;
                    delete product.shipperName;
                    delete product.product;
                    delete product.productName;
                    delete product.price;
                    delete product.counterparty;
                    delete product.unitId;
                    delete product.index;

                    data.products.push(product)
                }
                PostApi(this.api.save, data, function (a) {
                    self.already = false;
                    if (onSave) {
                        onSave(a);
                    }
                })
            } else {
                onSave();
            }
        },
        save:function(){
            this.saveLogic(function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        },
        print:function(){
            const self = this;
            this.saveLogic(function(a){
                loadModal(self.api.print + '?type=' + self.type, {id: self.plan});
            })
        },
        addProduct:function (item) {
            this.checkAnalyses(item);
            this.products.push(item);
        },
        checkAnalyses:function (item) {
            let product = item.product;
            if (product.analysesType) {
                let type = product.analysesType;
                if (type === 'sun' && typeof item.sun === "undefined"){
                    item.sun = {
                        oiliness:0,
                        humidity1:0,
                        humidity2:0,
                        soreness:0,
                        oilImpurity:0,
                        acidValue:0,
                        contamination:false
                    }
                } else if (type === 'oil' || type === 'raf'){
                    if (typeof item.oil === "undefined"){
                        let oil = {
                            organoleptic:false,
                            color:0,
                            acidValue:0,
                            peroxideValue:0,
                            phosphorus:0,
                            humidity:0,
                            degreaseImpurity:0,
                            transparency:0,
                            benzopyrene:0,
                            explosion:0,
                            type:1
                        };
                        if (type === 'raf'){
                            oil.wax = true;
                            oil.soap = true;
                        }
                        item.oil = oil;
                    } else{
                        item.oil.type = item.oil.explosion > 0 ? 2 : 1;
                    }
                }
            }
        }
    }
});