var edit = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        turn:{},
        turns:[],
        laborants:[],
        isInput:false,
        inp:''
    },
    methods:{
        add:function(worker){
            this.inp = '';
            this.isInput = false;
            this.turn.personal.push(worker)
        },
        remove:function(id){
            this.turn.personal.splice(id, 1);
        },
        save:function(){
            PostApi(this.api.save, this.turn, function(a){
                if(a.status == 'success'){
                    closeModal();
                }
            })
        },
        openInput:function(){
            this.isInput = true;
        },
        find:function(){
            const self = this;
            return self.laborants.filter(function(item){
                return self.inp && item.name.match(new RegExp(self.inp, "i"));
            });
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(a){
                self.turn.date = a;
            }, self.turn.date)
        }
    }
});