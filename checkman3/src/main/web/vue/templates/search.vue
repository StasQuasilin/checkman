search = {
    data:function () {
        return{
            input:'',
            items:[],
            timer:-1
        }
    },
    methods:{
        find:function () {
            clearTimeout(this.timer);
            const self = this;
            this.timer = setTimeout(function () {
                self.items.push(self.input);
            }, 300)
        }
    },
    template:'<div style="position: relative">' +
            '<div>' +
                '<input v-model="input" v-on:keyup="find()">' +
            '</div>' +
            '<div v-if="items" class="data-list">' +
                '<div v-for="item in items" clas="data-list-item">' +
                    '{{item}}' +
                '</div>' +
            '</div>' +
        '</div>'
};