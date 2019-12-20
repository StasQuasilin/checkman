var transportView = {
    props:{
        item:Object,
        fields:Object,
        customers:Array
    },
    methods:{
        driverName:function(driver){
            return driver.person.surname + ' ' + driver.person.forename + ' ' + driver.person.patronymic;
        }
    },
    template:
    '<div style="display: inline-block;">'+
        '<div style="display: inline-block; font-size: 10pt;">' +
            '<div>' +
                '{{fields.driver}}: '+
                '<span style="font-size: 12pt" v-if="item.driver"> ' +
                    '<b :title="driverName(item.driver)">'+
                        '{{item.driver.person.value}}'+
                    '</b>'+
                    '<span v-if="item.driver.license">'+
                        ' {{item.driver.license}}'+
                    '</span>'+
                '</span>'+
                '<span v-else>'+
                    '{{fields.noData}}'+
                '</span>'+
            '</div>' +
            '<div>'+
                '{{fields.vehicle}}: '+
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
            '<div v-if="item.driver && item.driver.person.phones.length > 0">' +
                '&#128222;' +
                '<span v-for="phone in item.driver.person.phones" style="padding: 0 2pt">' +
                    '<span>' +
                        '{{phone.number}}' +
                    '</span>' +
                '</span>' +
            '</div>' +
        '</div>'+
        '<div style="display: inline-block; font-size: 10pt; padding-left: 4pt">'+
            '<div>'+
                '{{fields.customer}}: '+
                '<span style="font-size: 12pt; font-weight: bold; width: 100%; text-align: center">'+
                    '{{customers[item.customer]}}'+
                '</span>'+
            '</div>'+
            '<div>'+
                '{{fields.transporter}}: '+
                '<span v-if="item.transporter">'+
                    '{{item.transporter.value}}'+
                '</span>' +
                '<span v-else>' +
                    '{{fields.noData}}' +
                '</span>'+
            '</div>'+
        '</div>'+
    '</div>'
};