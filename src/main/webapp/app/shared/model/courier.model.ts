import { ICourierChannel } from 'app/shared/model//courier-channel.model';

export interface ICourier {
    id?: number;
    name?: string;
    shortCode?: string;
    active?: boolean;
    trackingParameter?: string;
    trackingUrl?: string;
    parentCourierId?: number;
    hkShipping?: boolean;
    vendorShipping?: boolean;
    reversePickup?: boolean;
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
        public trackingParameter?: string,
        public trackingUrl?: string,
        public parentCourierId?: number,
        public hkShipping?: boolean,
        public vendorShipping?: boolean,
        public reversePickup?: boolean,
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
