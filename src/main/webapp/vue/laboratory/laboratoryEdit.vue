editor = new Vue({
    el: '#editor',
    data: {
        api: {},
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
        saveLogic:function(onSave){
            if (this.api.save && !this.already) {
                const self = this;
                this.already = true;
                PostApi(this.api.save, {plan: this.plan, analyses: this.analyses}, function (a) {
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
        recount:function(){
            return 0;
        },
        print:function(){
            const self = this;
            this.saveLogic(function(a){
                loadModal(self.api.print + '?type=' + self.type, {id: self.plan});
            })
        }
    }
});