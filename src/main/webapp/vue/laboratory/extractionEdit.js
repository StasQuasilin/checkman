var editor = new Vue({
    el: '#editor',
    data: {
        api:{
            save:''
        },
        laborants:[],
        times: [
            {
                hour:'08',
                minute:30
            },
            {
                hour:'10',
                minute:30
            },
            {
                hour:'12',
                minute:30
            },
            {
                hour:'14',
                minute:30
            },
            {
                hour:'16',
                minute:30
            },
            {
                hour:'18',
                minute:30
            },
            {
                hour:'20',
                minute:30
            },
            {
                hour:'22',
                minute:30
            },
            {
                hour:'00',
                minute:30
            },
            {
                hour:'02',
                minute:30
            },
            {
                hour:'04',
                minute:30
            },
            {
                hour:'06',
                minute:30
            }
        ],
        crude:{
        }
    },
    methods:{
        currentTime:function(){
            var now = new Date();
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