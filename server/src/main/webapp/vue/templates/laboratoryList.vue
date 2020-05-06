var laboratoryList = {
    mixins:[list],
    data:function(){
        return {
            turns: [],
            hours: {},
            offsets:[]
        }
    },
    mounted:function(){
        console.log('Laboratory list mounted');
    },
    methods:{
        buildTime:function(date, offset, min){
            let d = new Date(date);
            d.setHours(d.getHours() + offset)
            d.setMinutes(min);
            return d;
        },
        checkTurns:function(){
            let now = new Date();
            let year = now.getFullYear();
            let month = now.getMonth();
            let date = now.getDate();
            for (let i = 0; i < this.turns.length; i++){
                if (this.turns.hasOwnProperty(i)){
                    let turn = this.turns[i];
                    let offset = this.dayOffset(turn);
                    let begin = new Date(year, month, date, turn.begin, 0, 0);
                    let end = new Date(year, month, date + offset, turn.end, 0, 0);

                    if (now > (begin) && now < (end)){
                        this.initTurn(turn.number, begin);
                    }
                }
            }
        },
        initTurn:function(number, date){
            let hours = this.hours[number];
            let crude = [];
            for(let i in hours){
                if (hours.hasOwnProperty(i)){
                    let h = hours[i];
                    crude.push({
                        id:-1,
                        time:h,
                        empty:true
                    })
                }
            }
            this.items[this.items.length + 1] = {
                item:{
                    number:number,
                    date:date,
                    crude:crude,
                    granulas:[]
                }
            }
        },
        dayOffset:function(turn){
            return turn.begin < turn.end ? 0 : 1;
        }
    }
}