var productView = {
    props:{
        fields:Object,
        products:Array,
        showPrice:Boolean
    },
    methods:{
        getHeader:function(){
            if (this.products.length == 1){
                return this.products[0].product.name;
            } else {
                let names = [];

                for (let i in this.products) {
                    if (this.products.hasOwnProperty(i)) {
                        let p = this.products[i].product;
                        if (p.group) {
                            if (!names.includes(p.group.name)) {
                                names.unshift(p.group.name);
                            }
                        } else {
                            if (!names.includes(p.name)) {
                                names.push(p.name);
                            }
                        }
                    }
                }

                let name = names[0];
                name += ' +' + (names.length - 1);
                return name;
            }
        }
    },
    template:'<div class="product-view">' +
        '<div class="product-header">' +
            '<span>{{getHeader()}}</span>' +
            '<span v-if="products.length == 1">' +
                ', {{(products[0].amount).toLocaleString()}}' +
                '<span v-if="products[0].unit">' +
                    ' {{products[0].unit.name}}' +
                '</span>' +
                '<span v-if="showPrice">' +
                    ' {{products[0].price}}' +
                '</span>' +
                '<span>' +
                    ' {{products[0].shipper.name}}' +
                '</span>' +
            '</span>' +
        '</div>' +
        '<div v-if="products.length > 1" class="product-list">' +
            '<div v-for="product in products">' +
                '{{product.product.name}}' +
                '<span>{{product.amount}}</span>' +
                '<span v-if="product.unit">{{product.unit.name}}</span>' +
            '</div>' +
        '</div>' +
    '</div>'
};