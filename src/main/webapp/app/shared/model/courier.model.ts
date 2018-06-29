import { IVendorWHCourierMapping } from 'app/shared/model//vendor-wh-courier-mapping.model';
import { ICourierChannel } from 'app/shared/model//courier-channel.model';

export interface ICourier {
    id?: number;
    name?: string;
    shortCode?: string;
    active?: boolean;
    parentCourierId?: number;
    hkShipping?: boolean;
    vendorShipping?: boolean;
    reversePickup?: boolean;
    vendorWHCourierMappings?: IVendorWHCourierMapping[];
    courierChannels?: ICourierChannel[];
    courierGroupName?: string;
    courierGroupId?: number;
}

export class Courier implements ICourier {
    constructor(
        public id?: number,
        public name?: string,
        public shortCode?: string,
        public active?: boolean,
        public parentCourierId?: number,
        public hkShipping?: boolean,
        public vendorShipping?: boolean,
        public reversePickup?: boolean,
        public vendorWHCourierMappings?: IVendorWHCourierMapping[],
        public courierChannels?: ICourierChannel[],
        public courierGroupName?: string,
        public courierGroupId?: number
    ) {
        this.active = false;
        this.hkShipping = false;
        this.vendorShipping = false;
        this.reversePickup = false;
    }
}
