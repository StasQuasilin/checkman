transportationSaver = {
    methods:{
        saveTransportation:function(item, onSuccess, onFail){

            let e = {};
            // e.organisation = !(this.transportation.deal.counterparty && this.transportation.deal.counterparty.id !== -1);
            // e.product = this.transportation.deal.product.id === -1;

            if (!e.type && !e.organisation && !e.product) {
                let products = [];
                for (let i = 0; i < item.products.length; i++){
                    let product = item.products[i];
                    products.push({
                        id:product.id,
                        addressId:product.addressId,
                        dealProduct:product.dealProduct,
                        amount:product.amount
                    });
                }
                let transportation = {
                    id:item.id,
                    date:item.date,
                    customer:item.customer,
                    products:products,
                    notes:[]
                };

                if (item.address > 0){
                    transportation.address = item.address;
                }
                if (item.vehicle){
                    transportation.vehicle = item.vehicle.id;
                }
                if (item.trailer){
                    transportation.trailer = item.trailer.id;
                }
                if (item.driver){
                    transportation.driver = item.driver.id;
                }
                if (item.transporter){
                    transportation.transporter = item.transporter.id;
                }
                // if (!item.deal.quantity){
                //     transportation.deal.quantity = 0;
                // } else {
                //     transportation.deal.quantity = item.deal.quantity;
                // }
                // if (!item.deal.price){
                //     transportation.deal.price = 0;
                // } else {
                //     transportation.deal.price = item.deal.price;
                // }
                // transportation.deal.products = [
                //     {
                //         product : transportation.deal.product,
                //         quantity: transportation.deal.quantity,
                //         unit: transportation.deal.unit,
                //         shipper:transportation.deal.shipper,
                //         price:transportation.deal.price
                //     }
                // ];

                for(let i in item.notes){
                    if (item.notes.hasOwnProperty(i)){
                        let note = item.notes[i];
                        transportation.notes.push({
                            id: note.id,
                            note: note.note
                        });
                    }
                }
                if (item.manager){
                    transportation.manager = item.manager.id;
                }
                transportation.count = this.count;
                PostApi(this.api.save, transportation, function (a) {
                    if (typeof onSuccess === "function"){
                        onSuccess(a);
                    }
                }, function(e){
                    console.log(e);
                    if (typeof onFail === "function"){
                        onFail(e);
                    }
                })
            } else {
                console.error('Some wrong')
            }
        }
    }
};