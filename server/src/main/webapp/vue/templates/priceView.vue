var priceView = {
    props:{
        props:Object,
        item:Object
    },
    template:'<div style="display: inline-block" v-if="item.price > 0">' +
        '{{props.title}}: {{item.price.toLocaleString()}}' +
    '</div>'
};