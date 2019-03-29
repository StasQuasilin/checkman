Vue.component('date-picker', {
    data:function(){
        return {
            show:true,
            date: new Date(),
            day:'',
            month:2,
            year:0,
            months:[
                'Январь',
                'Февраль',
                'Март',
                'Апрель',
                'Май',
                'Июнь',
                'Июль',
                'Август',
                'Сентябрь',
                'Октябрь',
                'Ноябрь',
                'Декабрь',
            ],
            weekDays:[
                'Пн',
                'Вт',
                'Ср',
                'Чт',
                'Пт',
                'Сб',
                'Нд',
            ],
        }
    },
    methods:{
        _show:function(){
            this.show = !this.show
        },
        _switchMonth(d){
            this.month += d;
            if (this.month < 0){
                this.month = 11;
            }
            if (this.month > 11){
                this.month = 0;
            }
        }
    },
    mounted:function(){
        this.month = this.date.getMonth()
        this.year = this.date.getYear()
    },
    template:`

        <div style="display: inline-block">
            <input v-on:click="_show()" v-model="date.toLocaleDateString()">
            <div style="position: absolute; width: 100%; height: 100%; top: 0; left: 0; background-color: rgba(0, 132, 255, 0.13)">
                <div v-show="show" id="picker" class="picker">
                    <div class="picker-header">
                        <span class="picker-button" v-on:click="_switchMonth(-1)">&lt;</span>
                        <span class="month-holder">{{months[month]}}</span>
                        <span class="picker-button" v-on:click="_switchMonth(1)">&gt;</span>
                    </div>
                    <span class="box" v-for="wd in weekDays">{{wd}}</span>
                    <span v-for="i in 3">
                        <span class="box" v-for="j in 7">
                            {{i*j}}
                        </span>
                    </span>
                </div>
            </div>
        </div>
        `



})

