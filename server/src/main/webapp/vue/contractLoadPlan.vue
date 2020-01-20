var loadPlan = new Vue({
    components:{
      'object-input':objectInput
    },
    el:'#loadPlan',
    data:{
        api:{},
        contract:-1,
        products:[],
        transportations:[],
        types:{},
        customers:[],
        filterDate:-1
    },
    methods:{
        addProduct:function(json){
            json.select = true;
            loadPlan.products.push(json);
        },
        newTransportation:function(){
            var date;
            if (this.filterDate == -1){
                date = new Date().toISOString().substring(0, 10);
            } else{
                date = this.filterDate;
            }
            var products = [];
            for (var i in this.products){
                if (this.products.hasOwnProperty(i)){
                    var product = this.products[i];
                    if (product.select) {
                        products.push({
                            id: -1,
                            key:randomUUID,
                            contractProduct: product.id,
                            product: product.product,
                            amount: product.amount - product.plan,
                            unit: product.unit
                        })
                    }
                }
            }
            var transport = {
                id:-1,
                date:date,
                customer:'szpt',
                driver:{id:-1},
                vehicle:{id:-1},
                trailer:{id:-1},
                transporter:{id:-1},
                products:products
            };
            this.addTransportation(transport);
            this.initSaveTimer(transport);
        },
        addTransportation:function(transport){
            transport.save = -1;
            this.transportations.push(transport);
            this.sortTransport();
        },
        selectProducts:function(val){
          for(var i in this.products){
              if(this.products.hasOwnProperty(i)){
                  this.products[i].select = val;
              }
          }
        },
        inverseSelection:function(){
            for(var i in this.products){
                if(this.products.hasOwnProperty(i)){
                    this.products[i].select = !this.products[i].select;
                }
            }
        },
        selectProduct:function(product){
          if (this.products.length == 1){
              product.select = true;
          } else {
              product.select = !product.select;
          }
        },
        getPlan:function(contractProduct){
            var plan = 0;
            this.transportations.forEach(function(transport){
                transport.products.forEach(function(product){
                    if (product.contractProduct == contractProduct.id){
                        plan += parseFloat(product.amount);
                    }
                })
            });
            contractProduct.plan = plan;
            return plan;
        },
        putTransporter:function(transporter, item){
            if (item){
                item.transporter = transporter;
                this.initSaveTimer(item);
            }
        },
        putDriver:function(driver, item){
            if (item){
                item.driver = driver;
                if (driver.vehicle) {
                    this.putVehicle(driver.vehicle, item)
                } else{
                    this.initSaveTimer(item);
                }
                if (driver.organisation) {
                    this.putTransporter(driver.organisation)
                }
            }
        },
        putVehicle:function(vehicle, item){
            if (item){
                item.vehicle = vehicle;
                if (vehicle.trailer){
                    this.putTrailer(vehicle.trailer, item);
                } else{
                    this.initSaveTimer(item);
                }
            }
        },
        putTrailer:function(trailer, item){
            if (item){
                item.trailer = trailer;
                this.initSaveTimer(item);
            }
        },
        removeTransportation:function(transportation){
            for (var i in this.transportations){
                if (this.transportations.hasOwnProperty(i)){
                    var transport = this.transportations[i];
                    if (transport.key === transportation.key){
                        this.transportations.splice(i, 1);
                        break;
                    }
                }
            }
        },
        removeTransportationProduct:function(transportaton, productIdx){
          var res = transportaton.products.splice(productIdx, 1);
            console.log(res);
        },
        getDates:function(){
            var result = {};
            for (var i in this.transportations){
                if (this.transportations.hasOwnProperty(i)){
                    var date = this.transportations[i].date;
                    if (!result[date]){
                        result[date] = 0;
                    }
                    result[date]++;
                }
            }
            return result;
        },
        pickDate:function(item){
            const self = this;
            datepicker.show(function(date){
                item.date = date;
                self.sortTransport();
                self.initSaveTimer(item);
            }, item.date);
        },
        sortTransport:function(){
            this.transportations.sort(function(a, b){
                return new Date(a.date) - new Date(b.date);
            })
        },
        setFilterDate:function(date){
            if (this.filterDate === date){
                this.filterDate = -1;
            } else {
                this.filterDate = date;
            }
        },
        filteredTransportations:function(){
            if (this.filterDate == -1){
                return this.transportations;
            } else{
                const date = this.filterDate;
                return this.transportations.filter(function(item){
                    return item.date === date;
                })
            }
        },
        initSaveTimer:function(transport){
            clearTimeout(transport.save);
            const self = this;
            transport.save = setTimeout(function(){
                var data = {};
                data.id = transport.id;
                data.date = transport.date;
                data.customer = transport.customer;
                data.driver = transport.driver.id;
                if (transport.driver.license) {
                    data.license = transport.driver.license;
                }
                data.vehicle = transport.vehicle.id;
                data.trailer = transport.trailer.id;
                if (transport.transporter) {
                    data.transporter = transport.transporter.id;
                }
                data.products = [];
                for (var i in transport.products){
                    if (transport.products.hasOwnProperty(i)){
                        var t = transport.products[i];
                        data.products.push({
                            id:t.id,
                            key:t.key,
                            contractProduct:t.contractProduct,
                            plan:t.amount
                        })
                    }

                }
                PostApi(self.api.save, data, function(a){
                    transport.save = -1;
                    if (a.status === 'success'){
                        if (a.id){
                            transport.id = a.id;
                        }
                    }
                });
            }, 1500)
        }
    }
});