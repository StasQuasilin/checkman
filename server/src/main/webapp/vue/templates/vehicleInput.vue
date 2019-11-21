var objectInput = {
    props:{props:Object, object:Object},
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
        if (!props.add){
            console.warn('Title \'add\' required');
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
            this.props.put(object);
            this.foundObjects = [];

        },
        openObjectInput:function(){
            this.open = true;
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
                values.push(item[a])
            });
            return values.join(' ');
        }
    },
    template:
        '<span v-if="object.id" class="object-block">' +
            '<a v-on:click="edit">{{show(object)}}</a>' +
            '<span class="mini-close" v-on:click="closeObject()">' +
                '&times;' +
            '</span>' +
        '</span>' +
        '<div v-else style="display: inline-block">'+
            '<div v-if="open" style="display: inline-block">' +
                '<input v-model="input" autocomplete="off" ' +
                    'v-on:keyup="findObject()" ' +
                    ':class="{error : error}" v-on:click="error = false"' +
                    'style=" width: 100%; border: none">' +
                '<div class="custom-data-list" v-if="foundObjects.length > 0 || input">' +
                    '<div v-for="o in foundObjects"' +
                        'class="custom-data-list-item" ' +
                            'v-on:click="putObject(o)">' +
                                '{{show(o)}} ' +
                    '</div>' +
                    '<div class="custom-data-list-item">' +
                        '<b>' +
                            '+ {{props.add}}' +
                        '</b>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<a v-else style="font-size: 10pt" v-on:click="openObjectInput()">' +
                '{{props.header}}' +
            '</a>' +
        '</div>'

};