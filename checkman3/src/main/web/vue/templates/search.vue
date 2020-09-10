search = {
    props:{
        props:Object,
        object:Object,
        field:String
    },
    data:function () {
        return{
            input:'',
            items:[],
            timer:-1,
            done:true
        }
    },
    methods:{
        find:function () {
            clearTimeout(this.timer);
            const self = this;
            this.timer = setTimeout(function () {
                let input = self.input;
                PostApi(self.props.findApi, {key:input}, function (a) {
                    console.log(a);
                    if (a.status === 'success'){
                        self.items = a.result
                    }
                    self.timer = -1;
                }, function (e) {
                    console.log(e);
                })
            }, 500)
        },
        check:function () {
            let item = this.object;
            item.name = this.input;
            this.put(item)
        },
        put:function (item) {
            if (this.props.put){
                this.props.put(item);
                this.items = [];
            }
        }
    },
    template:'<div style="position: relative">' +
            '<div>' +
                '<input v-model="input" v-on:blur="check" v-on:keyup="find()">' +
            '</div>' +
            '<div class="search-result">' +
                '<div v-if="timer !== -1">' +
                    'Loading' +
                '</div>' +
                '<div v-else-if="items.length > 0" class="data-list">' +
                    '<div v-for="item in items" v-on:click="put(item)" clas="data-list-item">' +
                        '{{item}}' +
                    '</div>' +
                '</div>' +
                '<div v-else-if="input">' +
                    'New item : \'{{input}}\'' +
                '</div>' +
            '</div>' +

        '</div>'
};