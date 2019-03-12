/**
 * Created by szpt_user045 on 12.03.2019.
 */
Vue.component('todo-item', {
    props:['todo'],
    template:'<li>{{todo.text}}</li>'
});
var vue = new Vue({
    el: '#app',
    data:{
        counter: -1,
        message: 'Olololo!',
        title: 'Страница загружена ' + new Date().toLocaleString(),
        save:true,
        items:[
            { id: 0, text: 'Овощи' },
            { id: 1, text: 'Сыр' },
            { id: 2, text: 'Что там ещё люди едят?' }
        ]
    },
    methods:{
        update:function(){
            const self = this;
            setInterval(function(){
                self.counter++;
                console.log(self.counter);
            }, 1000)
        },
        saveAs: function () {
            this.update();
        }
    },
    mounted:function(){
        console.log('mounted');
        this.update();
    }

});