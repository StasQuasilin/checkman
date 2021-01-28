var laboratoryList = {
    mixins:[list],
    data:function(){
        return {
            turns: [],
            hours: {},
            offsets:[],
            timeOut:-1
        }
    },
    mounted:function(){
        console.log('Laboratory list mounted');

    },
    methods:{
        buildTime:function(date, offset, min){
            let d = new Date(date);
            d.setHours(d.getHours() + offset);
            d.setMinutes(min);
            d.setSeconds(0);
            d.setMilliseconds(0);

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

                    let begin = new Date(year, month, date, turn.begin, 0, 0);
                    let end = new Date(year, month, date , turn.end, 0, 0);
                    let offset = this.dayOffset(turn);

                    if (now.getHours() < begin.getHours()){
                        begin.setDate(begin.getDate() - offset);
                    } else {
                        end.setDate(end.getDate() + offset);
                    }
                    if (now > (begin) && now < (end)){
                        this.initTurn(turn.number, begin);
                        const self = this;
                        clearTimeout(this.timeOut);
                        this.timeOut = setTimeout(function () {
                            console.log('Check turns')
                            self.checkTurns();
                        }, end - now);
                        console.log('Next check at ' + end.toLocaleString());
                    }
                }
            }
        },
        update:function(itm){
            let key = new Date(itm.date).toLocaleDateString() + '-' + itm.number;
            Vue.set(this.items, key, {item:itm});
        },
        initTurn:function(number, date){
            this.update({
                number:number,
                date:date,
                crude:[],
                granulas:[]
            })
        },
        dayOffset:function(turn){
            return turn.begin < turn.end ? 0 : 1;
        }
    }
}