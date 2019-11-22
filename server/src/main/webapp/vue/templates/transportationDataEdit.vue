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
    '<div style="display: inline-block;">'+
        '<div style="display: inline-block; font-size: 10pt;">' +
            '<div>' +
                '{{fields.driver}}: '+
                '<object-input :props="fields.driverProps" :object="item.driver"></object-input>' +
                '<span style="font-size: 12pt" v-if="item.driver && item.driver.license">' +
                    '{{item.driver.license}}'+
                '</span>'+
            '</div>' +
            '<div>'+
                '{{fields.vehicle}}: '+
                '<object-input :props=fields.vehicleProps :object=item.vehicle></object-input>'+
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
                '<object-input :props=fields.transporterProps :object=item.transporter></object-input>'+
            '</div>'+
        '</div>'+
    '</div>'
};