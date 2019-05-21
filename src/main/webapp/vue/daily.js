var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        daily:{
            forpress:[]
        },
        forpress:[],
        turns:[],
        laborants:[]
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
                if (a.status == 'success'){
                    closeModal();
                }
            })
        },
        info: function () {
            var date = new Date(this.daily.date);
            date.setDate(date.getDate() - 1);
            return date.toLocaleDateString().substring(0, 5) + ' ' + this.turns[0].begin.substring(0, 5) + ' - ' +
                new Date(this.daily.date).toLocaleDateString().substring(0, 5) + ' ' + this.turns[this.turns.length - 1].end.substring(0, 5)
        },
        addForpress:function() {
            if (this.forpress.length > 0) {
                this.daily.forpress.push({
                    forpress: editor.forpress[0].id,
                    oiliness: 0,
                    humidity: 0
                })
            }
        },
        removeForpress:function(key){
            this.daily.forpress.splice(key, 1);
        }
    }
});