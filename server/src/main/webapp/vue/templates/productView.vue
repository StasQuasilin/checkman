var productView = {
    props:{
        props:Object,
        item:Object
    },
    template:'<div style="display: inline-block">' +
        '{{props.title}}:{{item.product.name}}' +
    '</div>'
};