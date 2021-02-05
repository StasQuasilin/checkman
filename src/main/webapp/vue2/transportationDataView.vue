transportaData = {
    props:{
        item:Object,
        titles:Object
    },
    template:'<div style="display: flex; flex-direction: row">' +
        '<div class="data-block" style="width: 300px">' +
            '<div>' +
                '<span v-if="item.driver">' +
                    '<b>' +
                        '{{item.driver.person.value}}' +
                    '</b>' +
                    '<span v-if="item.driver.license" style="display: inline-block; width: 90px; float: right; text-align: right">' +
                        '{{item.driver.license}}' +
                    '</span>' +
                '</span>' +
                '<b v-else>' +
                    '{{titles.noSpecified}}' +
                '</b>' +
            '</div>' +
        '<div>' +
        '<span v-if="item.vehicle">' +
        '{{item.vehicle.model}}' +
        '<span class="vehicle-number">' +
        '{{item.vehicle.number}}' +
        '</span>' +
        '<span v-if="item.vehicle.trailer" class="vehicle-number">' +
        '{{item.vehicle.trailer.number}}' +
        '</span>' +
        '</span>' +
        '<span v-else>' +
        '{{titles.noSpecified}}' +
        '</span>' +
        '</div>' +
        '<div v-if="item.driver && item.driver.person.phones.length > 0">' +
        '<img style="width: 10pt" :src="titles.phoneIcon" alt=""/>' +
        '<span style="padding-left: 2px" v-for="phone in item.driver.person.phones">' +
        '{{phone.number}}' +
        '</span>' +
        '</div>' +
        '</div>' +
        '<div class="data-block">' +
        '<div>' +
        '{{titles.customer}}: {{titles[item.customer]}}' +
        '</div>' +
        '<div>' +
        '{{titles.transporter}}: ' +
        '<b v-if="item.transporter">' +
        '{{item.transporter.value}}' +
        '</b>' +
        '<b v-else>' +
        '{{titles.noSpecified}}' +
        '</b>' +
        '</div>' +
        '</div>' +
        '</div>'
};