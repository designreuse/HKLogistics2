export interface IVendorWHCourierMapping {
    id?: number;
    active?: boolean;
    vendor?: string;
    warehouse?: number;
    courierChannelId?: number;
}

export class VendorWHCourierMapping implements IVendorWHCourierMapping {
    constructor(
        public id?: number,
        public active?: boolean,
        public vendor?: string,
        public warehouse?: number,
        public courierChannelId?: number
    ) {
        this.active = false;
    }
}
