var transportView = {
    props:{
        item:Object,
        fields:Object,
        customers:Array
    },
    components:{
        'object-input':objectInput
    },
    methods:{
        driverName:function(driver){
            return driver.person.surname + ' ' + driver.person.forename + ' ' + driver.person.patronymic;
        }
    },
    template:
    '<div style="display: inline-block; font-size: 10pt;">' +
        '<div>'+
            '<div style="display: inline-block;">' +
                '<div style="padding: 3pt 0">' +
                    '{{fields.driver}}: '+
                    '<object-input :props="fields.driverProps" :object="item.driver" :item="item"></object-input>' +

                '</div>' +
                '<div>'+
                    '{{fields.vehicle}}/{{fields.trailer}}: '+
                    '<object-input :props=fields.vehicleProps :object=item.vehicle :item="item"></object-input> '+
                    '<object-input :props=fields.trailerProps :object=item.trailer :item="item"></object-input>'+
                '</div>'+
            '</div>'+
            '<div style="display: inline-block; font-size: 10pt; padding-left: 4pt">'+
                '<div>'+
                    '{{fields.customer}}: '+
                    '<span style="font-size: 12pt; font-weight: bold; width: 100%; text-align: center">'+
                        '{{customers[item.customer]}}'+
                    '</span>'+
                '</div>'+
                '<div>'+
                    '{{fields.transporter}}: ' +
                    '<object-input :props=fields.transporterProps :object=item.transporter :item="item"></object-input>'+
                '</div>' +
            '</div>' +
        '</div>' +
        '<div v-if="item.driver">' +
            '<span v-if="item.driver.license">' +
                '{{fields.license}}: ' +
                '<b>{{item.driver.license}}</b>'+
            '</span>'+
            '<span v-for="phone in item.driver.person.phones" style="padding: 0 2pt">' +
                '<span>' +
                    '{{phone.number}}' +
                '</span>' +
            '</span>' +
        '</div>'+
    '</div>'
};