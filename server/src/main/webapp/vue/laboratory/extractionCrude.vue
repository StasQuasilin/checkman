var editor = new Vue({
    el: '#editor',
    data: {
        api:{
            save:''
        },
        laborants:[],
        times: [],
        crude:{},
        storages:[]
    },
    methods:{
        currentTime:function(target){
            var now = target ? new Date(target) : new Date();
            var min = Number.MAX_VALUE;
            var current = '';
            for (var t in this.times){
                if (this.times.hasOwnProperty(t)) {
                    var time = this.times[t];
                    var date = new Date();
                    date.setHours(parseInt(time.hour));
                    date.setMinutes(time.minute);

                    var d = Math.abs((date.getHours() * 60 + date.getMinutes()) - (now.getHours() * 60 + now.getMinutes()));
                    if (d < min) {
                        min = d;
                        current = time.hour + ':' + time.minute
                    }
                }
            }
            return current;
        },
        save:function(){
            PostApi(this.api.save, this.crude, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })

        },
        datePicker:function(){
            const self = this;
            datepicker.show(function(date){
                self.crude.date = date;
            }, this.crude.date)
        }
    },
    mounted:function(){
        this.time = this.currentTime();
    }
})