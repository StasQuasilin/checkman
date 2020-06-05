let reports = new Vue({
    el:'#reports',
    data:{
        api:{},
        reports:{}
    },
    computed:{
        maxDate:function(){
            let now = new Date();
            if (reports) {
                let values = Object.values(reports);
                for (let i in values) {
                    if (values.hasOwnProperty(i)) {
                        let value = values[i];
                        let date = new Date(value.leave);
                        if (date > now) {
                            now = date;
                        }
                    }
                }
            }
            return now;
        }
    },
    methods:{
        show:function(item){
            let data = {
                id:item.id
            };
            loadModal(this.api.show, data);
        },
        handler:function (data) {
            console.log(data);
            for (let i in data.add){
                if (data.add.hasOwnProperty(i)){
                    let add = data.add[i];
                    this.add(add);
                }
            }
            if (data.update){
                this.add(data.update);
            }
        },
        add:function(data){
            let owner = Object.assign({}, data.owner);
            if (!this.reports[owner.id]){
                Vue.set(this.reports, owner.id, {
                    owner: owner,
                    reports:[]
                });
            }
            delete data.owner;
            this.update(this.reports[owner.id].reports, data);
        },
        update:function(list, data){
            let found = false;

            for (let i in list){
                if (list.hasOwnProperty(i)){
                    let item = list[i];
                    if (item.id === data.id){
                        found = true;
                        list.splice(i, 1, data);
                    }
                }
            }

            if (!found){
                list.push(data);
            }
            list.sort(function (a, b) {
                return new Date(b.leave) - new Date(a.leave);
            })
        }
    }
});