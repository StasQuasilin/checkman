var priceView = {
    props:{
        props:Object,
        item:Object
    },
    template:'<div style="display: inline-block" v-if="item.price > 0">' +
        '<span class="label">' +
            '{{props.title}}: ' +
        '</span>' +
        '{{item.price.toLocaleString()}}' +
    '</div>'
};