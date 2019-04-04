const datepicker = new Vue({
    el: '#picker',
    data:{
        date:new Date().toISOString().substring(0, 10),
        color:'aliceblue',
        locale:'ru',
        onSelects:[],
        y:0,
        x:0
    },
    methods:{
        show:function(onSelect, date){
            this.y = event.pageY;
            this.x = event.pageX;

            if (this.onSelects.length == 0){
                this.date = date
            } else {
                this.date = new Date().toISOString().substring(0, 10)
            }
            this.onSelects.push(onSelect);
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