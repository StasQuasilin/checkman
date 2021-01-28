interestEdit = new Vue({
    el: '#interestEdit',
    components:{
        'interest-item':interestItem
    },
    data:{
        api:{},
        globalProps:{},
        counterpartyProps:{},
        productProps:{},
        counterpartyItems:{
            items:[],
            i:false,
            w:false,
            a:false,
            o:false
        },
        all:false
    },
    methods:{
        save:function () {
            let counterparty = {
                items:[],
                i:this.counterpartyItems.i,
                w:this.counterpartyItems.w,
                a:this.counterpartyItems.a,
                o:this.counterpartyItems.o,
            };
            for (let i = 0; i < this.counterpartyItems.items.length; i++){
                let item = this.counterpartyItems.items[i];
                counterparty.items.push({
                    itemId:item.id
                })
            }

            let interest = {
                all:this.all,
                counterparty:counterparty
            };
            console.log(interest);
            PostApi(this.api.save, interest, function (a) {
                if (a.status === 'success'){

                }
            })
        }
    }
});