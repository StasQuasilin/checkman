Vue.component('date-picker', {
    data:function(){
        return {
            show:true,
            date: new Date().toLocaleDateString(),
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
                'Декабрь'
            ],
            weekDays:[
                'Пн',
                'Вт',
                'Ср',
                'Чт',
                'Пт',
                'Сб',
                'Нд'
            ]
        }
    },
    methods:{

    },
    template:`<div style="display: inline-block;">
                      <v-menu class="date-picker"
                              v-model="show"
                              :close-on-content-click="false"
                              :nudge-right="40"
                              lazy
                              transition="scale-transition"
                              offset-y
                              full-width
                              min-width="290px"
                              >
                        <template v-slot:activator="{ on }">
                          <input style="width: 7em"
                                  v-model="date"
                                  readonly
                                  v-on="on"
                                  />
                        </template>
                        <v-date-picker v-model="date" @input="show = false"></v-date-picker>
                      </v-menu>
                      </div>`



})

