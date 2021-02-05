transportaData = {
    props:{
        item:Object,
        titles:Object
    },
    components:{
        'object-input':objectInput
    },
    template:'<div style="display: flex; flex-direction: row">' +
        '<div class="data-block" style="width: 300px">' +
            '<div>' +
                '<span v-if="item.driver">' +
                    '<object-input :object="item.driver" :props="driverProps"></object-input>' +
                    '<span v-if="item.driver.license" style="display: inline-block; width: 90px; float: right; text-align: right">' +
                        '{{item.driver.license}}' +
                    '</span>' +
                '</span>' +
                '<b v-else>' +
                    '{{titles.noSpecified}}' +
                '</b>' +
            '</div>' +
        '</div>' +
    '</div>'
};