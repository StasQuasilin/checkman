var editor = new Vue({
    el: '#editor',
    data: {
        api:{
            save:'',
            remove:''
        },
        laborants:[],
        times: [],
         crude:{},
        storages:[],
        already:false
    },
    methods:{
        currentTime:function(target){
            let now = target ? new Date(target) : new Date();
            let min = Number.MAX_VALUE;
            let current = '';
            for (let t in this.times){
                if (this.times.hasOwnProperty(t)) {
                    let time = this.times[t];
                    let date = new Date();
                    date.setHours(parseInt(time.hour));
                    date.setMinutes(time.minute);

                    let d = Math.abs((date.getHours() * 60 + date.getMinutes()) - (now.getHours() * 60 + now.getMinutes()));
                    if (d < min) {
                        min = d;
                        current = time.hour + ':' + time.minute
                    }
                }
            }
            return current;
        },
        save:function(){
            if (!this.already) {
                this.already = true;
                const self = this;
                PostApi(this.api.save, this.crude, function (a) {
                    if (a.status === 'success') {
                        closeModal();
                    }
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
        },
        datePicker:function(){
            const self = this;
            datepicker.show(function(date){
                self.crude.date = date;
            }, this.crude.date)
        },
        removeAnalyses:function(){
            if (this.api.remove) {
                closeModal();
                loadModal(this.api.remove + '?id=' + this.crude.id);
            }
        }
    },
    mounted:function(){
        this.time = this.currentTime();
    }
});