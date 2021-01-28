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
        edit:function(item, key, idx){
            const self = this;
            loadModal(this.api.edit, {id:item.id}, function(a){
                var some = self.items[key];
                if (some){
                    some.splice(idx, 1, a);
                }

            });
        },
        onClick:function(item, key, idx){
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
                this.edit(item, key, idx);
            }
        },
        collapse:function(){
            loadModal(this.api.collapse, {selected : this.selected}, function(a){
                console.log(a);
            });
        }
    }
});