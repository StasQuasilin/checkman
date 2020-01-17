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
        addTransportation:function(){
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
                            contractProduct: product.id,
                            product: product.product,
                            plan: product.amount,
                            unit: product.unit
                        })
                    }
                }
            }
            this.transportations.push({
                id:-1,
                key:randomUUID(),
                date:date,
                customer:'szpt',
                driver:{},
                vehicle:{},
                trailer:{},
                products:products,
                save:-1
            })
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
                        plan += parseFloat(product.plan);
                    }
                })
            })
            contractProduct.plan = plan;
            return plan;
        },
        putDriver:function(driver, item){
            if (item){
                item.driver = driver;
                if (driver.vehicle){
                    this.putVehicle(driver.vehicle, item)
                }
            }
        },
        putVehicle:function(vehicle, item){
            if (item){
                item.vehicle = vehicle;
                if (vehicle.trailer){
                    this.putTrailer(vehicle.trailer, item);
                }
            }
        },
        putTrailer:function(trailer, item){
            if (item){
                item.trailer = trailer;
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
            datepicker.show(function(date){
                item.date = date;
            }, item.date);
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
            transport.save = setTimeout(function(){
                transport.save = -1;
                transport.id=100500;
            }, 1500)
        }
    }
})