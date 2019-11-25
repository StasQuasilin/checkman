var objectInput = {
    props:{
        props:Object,
        object:Object,
        item:Object
    },
    data:function(){
        return{
            input:'',
            error:false,
            foundObjects:[],
            timer:-1,
            open:false
        }
    },
    mounted:function(){
        let props = this.props;
        if (!props.addHeader){
            console.warn('Title \'addHeader\' required');
        }
        if (!props.header){
            console.warn('Title \'header\' required');
        }
        if(!props.find){
            console.warn('String \'find\' no have');
        }
        if (!props.put){
            console.warn('Method \'put\' required');
        }
    },
    methods:{
        findObject:function(){
            if (this.props.find) {
                clearTimeout(this.timer);
                if (this.input) {
                    const self = this;
                    this.timer = setTimeout(function () {
                        PostApi(self.props.find, {key: self.input}, function (a) {
                            self.foundObjects = a;
                        })
                    }, 300);
                } else {
                    this.foundObjects = [];
                }
            }
        },
        putObject:function(object){
            this.props.put(object, this.item);
            this.foundObjects = [];
        },
        openObjectInput:function(){
            this.open = true;
            const self = this;
            setTimeout(function(){
                self.$refs.input.select();
            }, 0);

        },
        closeInput:function(){
            this.input = '';
            this.open = false;
            this.foundObjects = [];
        },
        closeObject:function(){
            console.log('Cancel');
            this.props.put({});
        },
        edit:function(){
            if (this.props.edit){
                const self = this;
                loadModal(this.props.edit, {id:this.object.id}, function(a){
                    self.props.put(a);
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
            const self = this;
            PostApi(this.props.add, {key:this.input}, function(a){
                if (a.status === 'success'){
                    self.putObject(a.result);
                }
            });
            this.closeInput();
        }
    },
    template:
        '<span v-if="object && object.id" class="object-block">' +
            '<a v-on:click="edit" style="font-weight: bold">{{show(object)}}</a>' +
            '<span class="mini-close" v-on:click="closeObject()">' +
                '&times;' +
            '</span>' +
        '</span>' +
        '<div v-else style="display: inline-block">'+
            '<div v-if="open" style="display: inline-block" v-on:blur="closeInput()">' +
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
                    '<div v-if="props.add" class="custom-data-list-item" v-on:click="addItem()">' +
                        '<b>' +
                            '+ {{props.addHeader}}' +
                        '</b>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<a v-else style="font-size: 10pt" v-on:click="openObjectInput()">' +
                '{{props.header}}' +
            '</a>' +
        '</div>'

};