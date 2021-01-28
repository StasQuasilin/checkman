customTime = new Vue({
    el:'#custom',
    data:{
        api:{},
        id:-1,
        action:'',
        date:null
    },
    methods:{
        selectDate:function(){
            const self = this;
            datepicker.show(function(date){
                let d = new Date(date);
                d.setHours(self.date.getHours());
                d.setMinutes(self.date.getMinutes());
                self.date = d;
            }, this.date.toISOString() );
        },
        selectTime:function(){
            const self = this;
            timepicker.show(function(time){
                time.setDate(self.date.getDate());
                time.setMonth(self.date.getMonth());
                time.setFullYear(self.date.getFullYear());
                self.date = time;
            }, this.date.getHours(), this.date.getMinutes());
        },
        save(){
            let d = this.date;
            let data = {
                id:this.id,
                date:d.toISOString().substring(0, 10) + ' ' + d.toLocaleTimeString(),
                action:this.action
            };
            PostApi(this.api.save, data, function (answer) {
                if (answer.status === 'success'){
                    closeModal();
                }
            });
            saveModal(this.date);
        }
    }
});