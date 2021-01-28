var timepicker = new Vue({
   el:'#timepicker',
    data:{
        hh:'',
        mm:'',
        hours:[
            '00', '01', '02', '03', '04', '05', '06','07', '08',
            '09', '10', '11', '12', '13', '14', '15', '16',
            '17', '18', '19', '20', '21', '22', '23'
        ],
        minutes:[
            '00', '05', '10', '15', '20', '25', '30', '35', '40', '45', '50', '55'
        ],
        color:'transparent',
        locale:'ru',
        x:0,
        y:0,
        onSave:[]
    }, methods:{
        prettyNumber:function(){
            return '00';
        },
        show(onSave, hh, mm){
            this.y = event.pageY;
            this.x = event.pageX;
            this.onSave.push(onSave);

            this.hh = hh < 10 ? '0' + hh : hh;
            this.mm = mm < 10 ? '0' + mm : mm;
        },
        setHours:function(hours){
            this.hh = hours;
        },
        setMinutes:function(minutes){
            this.mm = minutes;
        },
        save:function(){
            let date = new Date();
            date.setHours(this.hh);
            date.setMinutes(this.mm);
            for (let i in this.onSave){
                if (this.onSave.hasOwnProperty(i)) {
                    this.onSave[i](date);
                }
            }
            this.onSave = [];
        },
        close:function(){
            this.onSave = [];
        },
        saveAndClose:function(){
            if (event.target.className === 'datetime-picker') {
                this.save()
            }
        }
    }
});