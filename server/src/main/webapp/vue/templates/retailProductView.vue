var productView = {
    props:{
        fields:Object,
        products:Array,
        showPrice:Boolean
    },
    computed:{
        getCounterparty:function(){
            var clients = [];
            this.products.forEach(function(item){
                let counterparty = item.counterparty.value;
                if (!clients.includes(counterparty)){
                    clients.push(counterparty);
                }
            });
            return clients;
        },
        getAddress:function(){
            var address = [];
            this.products.forEach(function(item){
                let a = item.address;
                if (a && !address.includes(a)){
                    address.push(a);
                }
            });
            return address;
        },
        getProductHeader:function(){
            var headers = [];
            let total = 0;
            this.products.forEach(function(item){
                item.products.forEach(function(p){
                    let product = p.product;
                    total++;
                    if (product.group){
                        if (!headers.includes(product.group.name)){
                            headers.unshift(product.group.name);
                        }
                    } else{
                        if (!headers.includes(product.name)){
                            headers.push(product.name);
                        }
                    }
                })
            });
            let header = headers[0];
            if (headers.length > 1){
                header += ' ( ' + total + ' )'
            }
            return header;
        }
    },
    methods:{

    },
    template:'<div style="display: inline-flex">' +
        '<div v-if="getCounterparty.length == 1" class="label">' +
            '{{getCounterparty[0]}}' +
        '</div>' +
        '<div v-if="getAddress && getAddress.length > 0 && getAddress.length == 1" class="label">' +
            '{{getAddress[0].city}}, {{getAddress[0].street}}, {{getAddress[0].build}}' +
        '</div>' +
        '<div class="product-header">{{getProductHeader}}' +
            '<div class="product-list">' +
                '<div v-for="doc in products">' +
                    '<div v-if="getCounterparty.length > 1">' +
                        '{{doc.counterparty.value}}' +
                    '</div>' +
                '<div v-if="getAddress.length > 1">' +
                    '{{doc.address.city}}, {{doc.address.street}}, {{doc.address.build}}' +
                '</div>' +
                '<div class="product-row" v-for="product in doc.products">' +
                    '{{product.product.name}}' +
                    ' {{product.amount}}' +
                    ' {{product.unit.name}} ' +
                    ' {{product.shipper.name}}' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</div>' +
    '</div>'
};