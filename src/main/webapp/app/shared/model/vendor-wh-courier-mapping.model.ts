export interface IVendorWHCourierMapping {
    id?: number;
    active?: boolean;
    vendor?: string;
    warehouse?: number;
    courierName?: string;
    courierId?: number;
}

export class VendorWHCourierMapping implements IVendorWHCourierMapping {
    constructor(
        public id?: number,
        public active?: boolean,
        public vendor?: string,
        public warehouse?: number,
        public courierName?: string,
        public courierId?: number
    ) {
        this.active = false;
    }
}
