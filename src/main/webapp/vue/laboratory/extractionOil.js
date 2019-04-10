var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        oil:{},
        turns:[],
        laborants:[]
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
            PostApi(this.api.save, this.oil, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});