objectInput = {
    props:{
        props:Object,
        object:Object,
        item:Object,
        error:Boolean,
        is_open:Boolean,
        always_open:Boolean,
        put:Function
    },
    data:function(){
        return{
            input:'',
            foundObjects:[],
            timer:-1,
            open:false,
            showAdd:false
        }
    },
    mounted:function(){
        if (this.is_open || this.always_open){
            this.openObjectInput()
        }
    },
    methods:{
        findObject:function(){
            this.showAdd = false;
            if (this.props && this.props.find) {
                clearTimeout(this.timer);
                if (this.input) {
                    const self = this;
                    this.timer = setTimeout(function () {
                        PostApi(self.props.find, {key: self.input}, function (a) {
                            self.foundObjects = a;
                            if (a.length){
                                setTimeout(function () {
                                    self.showAdd = true;
                                }, 3000)
                            } else {
                                self.showAdd = true;
                            }
                        })
                    }, 600);
                } else {
                    this.foundObjects = [];
                }
            }
        },
        putObject:function(object){
            if (this.props && this.props.put){
                this.props.put(object, this.item);
            } else if (this.put){
                this.put(object)
            } else {
                console.warn('No place for put. Add attribute \'props.put\' or \'put\'')
            }

            if (object.isNew){
                const self = this;
                setTimeout(function(){
                    self.edit();
                }, 10);
            }
            this.open = false;
            this.input = '';
            this.foundObjects = [];
        },
        openObjectInput:function(){
            this.open = true;
            const self = this;
            setTimeout(function(){
                self.$refs.input.select();
            }, 10);
        },
        closeInput:function(){
            this.input = '';
            this.open = false;
            this.foundObjects = [];
            if (this.always_open){
                this.openObjectInput();
            }
        },
        closeObject:function(){
            this.props.put({id:-1}, this.item);
            if (this.always_open){
                this.openObjectInput();
            }
        },
        edit:function(){
            if (this.props && this.props.edit){
                const self = this;
                loadModal(this.props.edit, {id:this.object.id}, function(a){
                    if (a.status === 'success'){
                        self.putObject(a.result, self.item);
                    }
                })
            }
        },
        show:function(item){
            let values = [];
            this.props.show.forEach(function(a){
                let field = item;
                a.split('/').forEach(function(split){
                    field = field[split];
                });
                values.push(field)
            });
            return values.join(' ');
        },
        addItem:function(){
            if (this.props.add) {
                const self = this;
                PostApi(this.props.add, {key: this.input}, function (a) {
                    console.log(a);
                    if (a.status === 'success') {
                        self.putObject(a.result);
                    }
                });
            }
            this.closeInput();
        }
    },
    template:
        '<span v-if="object && object.id > 0" class="object-block">' +
            '<a v-on:click="edit">{{show(object)}}</a>' +
            '<div class="object-menu">' +
                '<span class="mini-close" v-on:click="closeObject()">' +
                    '&times;' +
                '</span>' +
            '</div>' +
        '</span>' +
        '<div v-else style="display: inline-block">'+
            '<div v-if="open" style="display: inline-block; position: relative;" v-on:blur="closeInput()">' +
                '<input v-model="input" autocomplete="off" ref="input"' +
                    'v-on:keyup="findObject()" v-on:keyup.escape="closeInput()"' +
                    ':class="{error : error}" v-on:click="error = false"' +
                    'style=" width: 90%; border: none">' +
                '<span class="mini-close" v-on:click="closeInput()">&times;</span>' +
                '<div class="custom-data-list" v-if="foundObjects.length > 0 || input">' +
                    '<div v-for="o in foundObjects"' +
                        'class="custom-data-list-item" ' +
                            'v-on:click="putObject(o)">' +
                                '{{show(o)}} ' +
                    '</div>' +
                    '<div v-if="props.add && showAdd" class="custom-data-list-item" v-on:click="addItem()">' +
                        '<b>' +
                            '+ {{props.addHeader}}' +
                        '</b>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<a v-else style="font-size: 10pt" v-on:click="openObjectInput()" ' +
        '       :class="{error : error}" v-html="props.header">' +
            '</a>' +
        '</div>'

};