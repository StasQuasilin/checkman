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
                console.log(self.plan);
                    PostReq(self.api.print, {id: self.plan}, function (p) {
                        if (p) {
                            var print = window.open();
                            print.document.write('<html>');
                            print.document.write(p);
                            print.document.write('</html>');
                            $(print.document).ready(function(){
                                print.print();
                                print.close();
                            })
                        }
                    })
            })
        }
    }
});