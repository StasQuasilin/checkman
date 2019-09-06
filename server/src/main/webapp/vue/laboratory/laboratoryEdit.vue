var editor = new Vue({
    el: '#editor',
    data: {
        api: {
            save: ''
        },
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
        laborants: []
    },
    methods:{
        saveLogic:function(onSave){
            PostApi(this.api.save, {plan:this.plan, analyses:this.analyses}, function(a){
                if (onSave){
                    onSave(a);
                }
            })
        },
        save:function(){
            this.saveLogic(function(a){
                if (a.status == 'success'){
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
                PostReq(self.api.print, {id: self.plan}, function (p) {
                    if (p) {
                        var print = window.open();
                        print.document.write(p);
                        print.document.close();
                        $(print.document).ready(function(){
                            setTimeout(function(){
                                print.print();
                                print.close();
                            }, 10);
                        });
                        //print.document.write('<div style="top: 0; left: 0; position: absolute; background-color: white; width: 100%; height: 100%"></div>')
                    }
                })
            })
        }
    }
});