var editor = new Vue({
    components:{
        'object-input':objectInput
    },
    el:'#editor',
    data:{
        api:{},
        contract:{
            id:-1,
            type:'sell',
            number:'',
            from:new Date().toISOString().substring(0, 10),
            to:new Date().toISOString().substring(0, 10),
            counterparty:{
                id:-1
            },
            address:-1,
            addressList:[],
            products:[]
        },
        shipper:-1,
        types:[],
        units:[],
        shippers:[],
        counterpartyProps:{},
        productProps:{}
    },
    methods:{
        selectFrom:function(){
            const self = this;
            datepicker.show(function(date){
                self.contract.from = date;
                var from = new Date(self.contract.from);
                var to = new Date(self.contract.to);
                if (from > to){
                    self.contract.to = date;
                }
            }, self.contract.from);
        },
        selectTo:function(){
            const self = this;
            datepicker.show(function(date){
                self.contract.to = date;
                var from = new Date(self.contract.from);
                var to = new Date(self.contract.to);
                if (to < from){
                    self.contract.from = date;
                }
            }, self.contract.to);
        },
        getAddress:function(counterparty, contract){
            if (counterparty.name) {
                console.log('Get Address for ' + counterparty.value + ', id=' + counterparty.id);
                PostApi(this.api.findAddress, {counterparty: counterparty.id}, function (a) {
                    contract.addressList = a;
                    if (a.length == 1){
                        contract.address = a[0];
                    }
                })
            } else {
                console.log('Clear address list');
                contract.addressList = [];
            }
        },
        checkAddress:function(contract){
            if (contract.address.id == -2){
                contract.address.id = -1;
                this.addAddress(contract);
            }
        },
        addAddress:function(contract){
            loadModal(this.api.editAddress, {counterparty:contract.counterparty.id}, function(a){
                contract.addressList.push(a.address);
                contract.address = a.address;
            });
        },
        getShipper:function(){
            for (var i in this.shippers){
                if (this.shippers.hasOwnProperty(i)){
                    if (this.shippers[i].id == this.shipper){
                        return this.shippers[i];
                    }
                }
            }
        },
        addProductLine:function(){
            this.contract.products.push({
                id:-1,
                key:randomUUID(),
                product:{
                    id:-1
                },
                amount:0,
                price:0,
                shipper:this.getShipper(),
                unit:{
                    id:-1
                }
            })
        },
        getPrice:function(contract, product){
            const self = this;
//        this.contracts.forEach(function(d){
//          d.products.forEach(function(p){
//            if (p == contract){
//              if (d.counterparty.id != -1) {
//                PostApi(self.api.price, {counterparty:d.counterparty.id, product:product.id}, function(a){
//                  console.log(a);
//                  if (contract.price == 0) {
//                    if (a.status === 'success') {
//                      contract.price = a.price;
//                    }
//                  }
//                })
//              }
//            }
//          })
//        });
        },
        anyUnselected:function(){
            return this.contract.products.filter(function(item){
                    return item.product.id == -1;
                }).length > 0;
        },
        removeProduct:function(idx){
            this.contract.products.splice(idx, 1);
        },
        save:function(onSave){
            if (this.checkErrors()){
                var data = {
                    id:this.contract.id,
                    type:this.contract.type,
                    number:this.contract.number,
                    from:this.contract.from,
                    to:this.contract.to,
                    counterparty:this.contract.counterparty.id,
                    address:this.contract.address.id,
                    products:[]
                };


                var products = data.products;
                for (var i in this.contract.products){
                    if (this.contract.products.hasOwnProperty(i)){
                        var product = this.contract.products[i];
                        products.push({
                            id:product.id,
                            key:product.key,
                            type:this.contract.type,
                            product:product.product.id,
                            amount:product.amount,
                            price:product.price,
                            shipper:product.shipper.id,
                            unit:product.unit.id
                        })
                    }
                }
                const self = this;
                PostApi(this.api.save, data, function(a){
                    console.log(a);
                    self.contract.id = a.contract;
                    for (var i in self.contract.products){
                        if (self.contract.products.hasOwnProperty(i)){
                            var p1 = self.contract.products[i];
                            for (var j in a.products){
                                if (a.products.hasOwnProperty(j)){
                                    var p2 = a.products[j];
                                    if (p1.key === p2.key){
                                        p1.id == p2.id;
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                    onSave(a);
                })
            }
        },
        checkErrors:function(){
            return true;
        },
        saveClose:function(){
            this.save(function(a){
                if (a.status === 'success'){
                    closeModal();
                }
            });
        },
        saveContinue:function(){
            this.save(function(a){
                if (a.status === 'success'){
                    //todo load preview
                    closeModal();
                }
            })
        }
    }
})