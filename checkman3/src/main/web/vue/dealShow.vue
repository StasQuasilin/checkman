function randomUUID() {
    const s4 = function(){
        return ((( 1 + Math.random()) * 0x10000 ) | 0).toString(16).substring(1);
    };
    return s4() + '-' + s4() + '-' + s4() + s4() + '-' + s4() + '-' + s4();
}

dealShow = new Vue({
    el:'#dealShow',
    mixins:[editor],
    data:function () {
        return{
            deals:[],
            deal:-1,
            dealProduct:-1,
            transportations:[],
            carriages:[],
            customers:[],
            customerNames:{},
            tab:'transportations',
            tabs:[
                'transportations',
                'carriages'
            ],
            transportationDate:'',
            savers:{}
        }
    },
    computed:{
        dealMap:function () {
            let map = {};
            for (let i = 0; i < this.deals.length; i++){
                let deal = this.deals[i];
                map[deal.id] = deal;
            }
            return map;
        },
        dpMap:function(){
            let map = {};
            let deal = this.dealMap[this.deal];
            for (let i = 0; i < deal.documents.length; i++){
                let document = deal.documents[i];
                for (let j = 0; j < document.products.length; j++){
                    let product = document.products[j];
                    map[product.id] = product;
                }
            }
            return map;
        },
        transportationDates:function () {
            let dates = [];
            for (let i = 0 ; i < this.transportations.length; i++){
                let transportation = this.transportations[i];
                let date = transportation.date;
                if (!dates.includes(date)){
                    dates.push(date);
                }
            }
            return dates;
        },
        transportationsByDate:function () {
            let tr = {};
            for (let i = 0 ; i < this.transportations.length; i++){
                let transportation = this.transportations[i];
                let date = transportation.date;
                if (!tr[date]){
                    tr[date] = {
                        count:0,
                        amount:0
                    };
                }
                let item = tr[date];
                item.count++;
                item.amount += parseFloat(transportation.amount);
            }
            return tr;
        }
    },
    methods:{
        save:function(item, api){
            if (!item.key){
                item.key = randomUUID();
            }
            let key = item.key;

            let saver = this.savers[key];
            if (saver){
                clearTimeout(saver);
            }

            saver = setTimeout(function () {
                console.log('Save: ' + JSON.stringify(item) + ' on ' + api);
                // PostApi(api, item);
            }, 1000);
            this.savers[key] = saver;
        },
        newTransportation:function () {
            let item = {
                id:-1,
                date:this.getTransportationDate(),
                amount:25,
                customer:this.customers[0],
                inCarriage:false,
                carriage:-1
            };
            this.transportations.push(item);
            this.saveTransportation(item);
        },
        saveTransportation:function(item){
            this.save(item, this.api.saveTransportation);
        },
        getTransportationDate:function(){
            if (this.transportationDate){
                return this.transportationDate;
            } else {
                let now = new Date();
                let from = new Date(this.dealMap[this.deal].from);
                let to = new Date(this.dealMap[this.deal].to);
                if (from > (now)){
                    return from.toISOString().substring(0, 10);
                } else if (to < now){
                    return to.toISOString().substring(0, 10);
                } else {
                    return now.toISOString().substring(0, 10);
                }
            }
        },
        newCarriage:function () {
            this.carriages.push({
                id:-1,
                train:{

                },
                number:''
            })
        },
        checkDetails:function () {
            this.dealProduct = this.dealMap[this.deal].documents[0].products[0].id;
            //todo load details

        },
        getTransportations:function () {
            if (this.transportationDate){
                const self = this;
                return this.transportations.filter(function (item) {
                    return item.date === self.transportationDate;
                })
            } else {
                return this.transportations;
            }
        }
    }
});