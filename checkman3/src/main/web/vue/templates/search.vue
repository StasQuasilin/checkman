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
            done:true,
            edit:false
        }
    },
    methods:{
        find:function () {
            clearTimeout(this.timer);
            if(this.input.length > 2) {
                this.timer = setTimeout(this.searchReq, 500)
            }
        },
        searchReq:function(){
            PostApi(this.props.findApi, {key: this.input}, this.reqHandler, function (e) {
                console.log(e);
            })
        },
        reqHandler:function(a){
            if (a.status === 'success') {
                this.items = a.result
            }
            this.timer = -1;
        },
        check:function () {
            let item = this.object;
            item.name = this.input;
            this.put(item)
        },
        put:function (item) {
            if (this.props.put){
                this.props.put(item);
            }
            this.items = [];
            this.edit= false;
            this.input = item.name
        }
    },
    template:'<div style="position: relative; display: inline-block">' +
            '<template v-if="edit">' +
                '<div>' +
                    '{{props.holder}}' +
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
            '</template>' +
            '<div v-else v-on:click="edit=true" class="text-button">' +
                '<b v-if="input">' +
                    '{{input}}' +
                '</b>' +
                '<template v-else>' +
                    '{{props.holder}}' +
                '</template>' +
            '</div>' +
        '</div>'
};