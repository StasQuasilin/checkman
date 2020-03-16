var transportView = {
    props:{
        item:Object,
        fields:Object,
        customers:Array
    },
    methods:{
        driverName:function(driver){
            if (driver && driver.person) {
                return driver.person.surname + ' ' + driver.person.forename + ' ' + driver.person.patronymic;
            }
        }
    },
    template:
    '<div style="display: inline-block;">'+
        '<div style="display: inline-block; font-size: 10pt;">' +
            '<div class="data-block">' +
                '<div>' +
                    '<span class="label">' +
                        '{{fields.driver}}: ' +
                    '</span>'+
                    '<span v-if="item.driver"> ' +
                        '<b :title="driverName(item.driver)" style="font-size: 10pt">'+
                            '{{item.driver.person.value}}'+
                        '</b>'+
                        '<span v-if="item.driver.license" :title="fields.license" style="font-size: 10pt">'+
                            ' /{{item.driver.license}}/'+
                        '</span>'+
                    '</span>'+
                    '<span v-else>'+
                        '{{fields.noData}}'+
                    '</span>'+
                '</div>' +
                '<div>' +
                    '<span class="label">' +
                        '{{fields.vehicle}}: '+
                    '</span>'+
                    '<span v-if="item.vehicle && item.vehicle.id">'+
                        '{{item.vehicle.model}}'+
                        '<span class="vehicle-number">'+
                            '{{item.vehicle.number}}'+
                        '</span>'+
                        '<span v-if="item.trailer" class="vehicle-number">'+
                            '{{item.trailer.number}}'+
                        '</span>'+
                    '</span>'+
                    '<span v-else>'+
                        '{{fields.noData}}'+
                    '</span>'+
                '</div>' +
            '</div>' +
            '<div v-if="item.driver && item.driver.person.phones.length > 0" style="position: absolute">' +
                '<img style="width: 10pt" src="images/phone.svg">' +
                '<span v-for="phone in item.driver.person.phones" style="padding: 0 2pt">' +
                    '<span>' +
                        '{{phone.number}}' +
                    '</span>' +
                '</span>' +
            '</div>' +

        '</div>'+
        '<div style="display: inline-block; font-size: 10pt; padding-left: 4pt">' +
            '<div class="data-block">'+
                '<div>' +
                    '<span class="label">'+
                        '{{fields.customer}}: ' +
                    '</span>'+
                    '<span style="font-weight: bold; width: 100%; text-align: center">'+
                        '{{customers[item.customer]}}'+
                    '</span>'+
                '</div>'+
                '<div>' +
                    '<div style="display: inline-block">' +
                        '<span class="label">'+
                            '{{fields.transporter}}: ' +
                        '</span>'+
                        '<span v-if="item.transporter" class="transporter-label">' +
                            '{{item.transporter.value}}' +
                        '</span>' +
                        '<span v-else>' +
                            '{{fields.noData}}' +
                        '</span>' +
                    '</div>'+
                '</div>' +
            '</div>'+
        '</div>'+
    '</div>'
};