interestItem = {
    props:{
        global:Object,
        props:Object,
        all:Boolean,
        items:Object
    },
    components:{
        'object-input':objectInput
    },
    data:function(){
        return {
            item:{}
        }
    },
    mounted:function(){
    },
    methods:{
        changeAll:function () {
            interestEdit.all = this.a;
        },
        style:function () {
            return {
                height:this.global.height
            }
        },
        dropItem:function (idx) {
            this.items.items.splice(idx, 1);
        },
        addItem:function (item) {
            this.items.items.push(item);
        }
    },
    template:'<div style="width: 100%; border-top: solid gray 1pt">' +
        '<div style="width: 100%; text-align: center">' +
            '<b>{{props.title}}</b>' +
        '</div>' +
        '<div :style="style" style="display: flex; flex-direction: row">' +
            '<div style="width: 180pt">' +
                '<div>' +
                    '<input disabled type="checkbox" v-model="all"> <span>{{props.all}}</span>' +
                '</div>' +
                '<div v-if="!all">' +
                    '<div style="overflow-y: scroll">' +
                        '<div v-for="(i, index) in items.items">' +
                            '<span class="mini-close" v-on:click="dropItem(index)">' +
                                '&times;' +
                            '</span>' +
                            '<template v-if="props.show">' +
                                '<template v-for="s in props.show">' +
                                    '<span v-if="i[s]">{{i[s]}}</span>' +
                                '</template>' +
                            '</template>' +
                            '<span v-else>' +
                                '{{i}}' +
                            '</span>' +
                        '</div>' +
                    '</div>' +
                    '<object-input v-if="props.addItemProps" ' +
                        ':props="props.addItemProps" ' +
                        ':object="item" :put="addItem"></object-input>' +
                '</div>' +
            '</div>' +
            '<div v-if="items">' +
                '<input type="checkbox" v-model="items.i">{{global.i}}<br>' +
                '<input type="checkbox" v-model="items.w">{{global.w}}<br>' +
                '<input type="checkbox" v-model="items.a">{{global.a}}<br>' +
                '<input type="checkbox" v-model="items.o">{{global.o}}<br>' +
            '</div>' +
        '</div>' +
    '</div>'
};