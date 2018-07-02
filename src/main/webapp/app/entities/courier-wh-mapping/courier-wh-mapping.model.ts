export interface ICourierWHMapping {
    warehouse?: any;
    courierChannel?: any;
}

export class CourierWHMapping implements ICourierWHMapping {
    constructor(
        public warehouse?: any,
        public courierChannel?: any
    ) { }
}
