var editor = new Vue({
    el: '#editor',
    data:{
        api:{},
        daily:{
            forpress:[]
        },
        forpress:[],
        turns:[]
    },
    methods:{
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.daily.date = date
            }, self.daily.date)
        },
        save:function(){
            PostApi(this.api.save, this.daily, function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            })
        },
        addForpress:function() {
            if (this.forpress.length > 0) {
                this.daily.forpress.push({
                    forpress: editor.forpress[0].id,
                    wet:0,
                    humidity: 0,
                    dry: 0
                })
            }
        },
        removeForpress:function(key){
            this.daily.forpress.splice(key, 1);
        }
    }
});