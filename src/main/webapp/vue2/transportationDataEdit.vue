transportaData = {
    props:{
        item:Object,
        titles:Object,
        props:Object,
        customers:Array
    },
    components:{
        'object-input':objectInput
    },
    template:'<div style="display: flex; flex-direction: row">' +
            '<div class="data-block" style="width: 400px">' +
                '<div>' +
                    '<object-input :object="item.driver" :item="item" :props="props.driverProps"></object-input>' +
                '</div>' +
                '<div>' +
                    '<object-input :object="item.vehicle" :item="item" :props="props.vehicleProps"></object-input>' +
                    '<object-input :object="item.trailer" :item="item" :props="props.trailerProps"></object-input>' +
                '</div>' +
            '<div>' +
        '</div>' +
        '<div v-if="item.driver && item.driver.person.phones.length > 0">' +
            '<img style="width: 10pt" :src="titles.phoneIcon" alt=""/>' +
            '<span style="padding-left: 2px" v-for="phone in item.driver.person.phones" class="secure">' +
                '{{phone.number}}' +
            '</span>' +
        '</div>' +
        '</div>' +
        '<div class="data-block">' +
            '<div>' +
                '<label :for="\'customer_\' + item.id">' +
                    '{{titles.customer}}: ' +
                '</label>' +
                '<select :id="\'customer_\' + item.id" v-model="item.customer" v-on:click.stop="" v-on:change="props.save(item)">' +
                    '<option v-for="customer in props.customers" :value="customer">' +
                        '{{titles[customer]}}' +
                    '</option>' +
                '</select>' +
            '</div>' +
            '<div>' +
                '{{titles.transporter}}: ' +
                '<object-input :object="item.transporter" :item="item" :props="props.transporterProps"></object-input>' +
            '</div>' +
        '</div>' +
        '</div>'
};