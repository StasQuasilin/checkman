const datepicker = new Vue({
    el: '#datePicker',
    data:{
        date:new Date().toISOString().substring(0, 10),
        color:'aliceblue',
        locale:'ru',
        onSelects:[],
        type:'date',
        y:0,
        x:0
    },
    methods:{
        show:function(onSelect, date, type){
            this.y = event.pageY;
            this.x = event.pageX;
            if (type){
                this.type = type;
            } else {
                this.type = 'date'
            }

            if (this.onSelects.length == 0){
                this.date = date
            } else {
                this.date = new Date().toISOString().substring(0, 10)
            }
            this.onSelects.push(onSelect);
            const self = this;
            setTimeout(function(){
                var bound = self.$refs.date.getBoundingClientRect();
                if (bound.x + bound.width > document.body.clientWidth){
                    self.x = document.body.clientWidth - bound.width;
                }
            }, 10);

            return this.onSelects.length;
        },
        put:function(){
            console.log(this.date);
            for (var i in this.onSelects){
                if (this.onSelects.hasOwnProperty(i)){
                    this.onSelects[i](this.date)
                }
            }
            this.onSelects = [];
        },
        close:function(){
            if (event.target.className == 'datetime-picker'){
                this.onSelects = [];
            }
        }
    }
});