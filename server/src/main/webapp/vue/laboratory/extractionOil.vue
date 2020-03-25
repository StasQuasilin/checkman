var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        oil:{},
        turns:[],
        laborants:[],
        already:false,
        err:{
            turn:false
        }
    },
    methods:{
        datePicker:function(){
            const self = this;
            datepicker.show(function(date){
                self.oil.date = date;
            }, this.oil.date)
        },
        turnDate:function(day){
            var date = new Date(this.oil.date);
            date.setDate(date.getDate() + day);
            return date.toLocaleDateString()
        },
        save:function(){
            if (!this.already) {

                this.err.turn = this.oil.turn === -1;

                if (!this.err.turn) {
                    this.already = true;
                    const self = this;
                    PostApi(this.api.save, this.oil, function (a) {
                        if (a.status === 'success') {
                            closeModal();
                        }
                        self.already = false;
                    }, function(e){
                        self.already = false;
                    })
                }
            }
        },
        remove:function(){
            if (this.api.remove){
                PostApi(this.api.remove, {id:this.oil.id}, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        }
    }
});