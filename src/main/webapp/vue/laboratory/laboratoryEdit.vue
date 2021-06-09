laboratoryEdit = {
    el: '#editor',
    data: {
            api: {},
            products:[],
            empty: '',
            plan: -1,
            organisation: '',
            vehicle: {
                model: '',
                number: '',
                trailer: ''
            },
            driver: '',
            analyses: {},
            helpers:{},
            already:false
    },
    methods:{
        addProducts:function(item){
            this.checkAnalyses(item);
            this.products.push(item);
        },
        checkAnalyses:function(){
            console.error('\'Checkanalyses\' not realized')
        },
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
        }
    }
};