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
        saveLogic:function(onSave){
            if (this.api.save && !this.already) {
                const self = this;
                this.already = true;
                let data = {
                    products:[]
                };
                for (let i = 0; i < this.products.length; i++){
                    data.products.push(this.products[i])
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
                } else if (type === 'oil'){
                    if (typeof item.oil === "undefined"){
                        item.oil = {
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
                        }
                    } else{
                        item.oil.type = item.oil.explosion > 0 ? 2 : 1;
                    }
                }
            }
        }
    }
});