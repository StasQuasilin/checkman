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
                '<div>' +
                    '<span class="label">' +
                        '{{fields.driver}}:' +
                    '</span>'+
                    '<object-input :props="fields.driverProps" :object="item.driver" :item="item"></object-input>' +
                    '<span v-if="item.driver && item.driver.license">' +
                        ' <{{item.driver.license}}>'+
                    '</span>' +
                '</div>' +
                '<div>' +
                    '<span class="label"/>'+
                        '{{fields.vehicle}}:' +
                    '</span>'+
                    '<object-input :props=fields.vehicleProps :object=item.vehicle :item="item"></object-input> ' +
                    '<template v-if="item.vehicle && item.vehicle.id > 0">' +
                        '<object-input  :props=fields.trailerProps :object=item.trailer :item="item"></object-input>'+
                    '</template>'+

                '</div>'+
            '</div>'+
            '<div style="display: inline-block; margin-left: 2pt">'+
                '<div>' +
                    '<span class="label">'+
                        '{{fields.customer}}:' +
                    '</span>'+
                    '<span style="font-weight: bold; font-size: 13pt; width: 100%; text-align: center">'+
                        '{{customers[item.customer]}}'+
                    '</span>'+
                '</div>'+
                '<div>' +
                    '<span class="label">'+
                        '{{fields.transporter}}:' +
                    '</span>' +
                    '<object-input :props=fields.transporterProps :object=item.transporter :item="item"></object-input>'+
                '</div>' +
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
    '</div>'
};