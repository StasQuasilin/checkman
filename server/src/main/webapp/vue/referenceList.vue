var referenceList = new Vue({
    el:'#referencesList',
    data:{
        api:{
            update:'',
            edit:''
        },
        items:{},
        selected:[]
    },
    methods:{
        update:function(){
            const self = this;
            PostApi(this.api.update, null, function(a){
                self.items = a;

            })
        },
        getKeys:function(){
            var keys = Object.keys(this.items);
            keys.sort(function(a, b){
                return a.localeCompare(b);
            });

            return keys;
        },
        sort:function(){
            this.items.sort(function (a, b) {
                return a.localeCompare(b);
            })
        },
        edit:function(id){
            loadModal(this.api.edit + '?id=' + id);
        },
        onClick:function(item){
            if (event.ctrlKey){
                Vue.set(item, 'selected', true);
                this.selected.push(item);
            } else {
                for (let i in this.selected){
                    if (this.selected.hasOwnProperty(i)){
                        this.selected[i].selected = false;
                    }
                }
                this.selected = [];
            }
        },
        collapse:function(){
            loadModal(this.api.collapse, {selected : this.selected});
        }
    }
});