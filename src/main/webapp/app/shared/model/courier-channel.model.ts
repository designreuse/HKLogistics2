import { IVendorWHCourierMapping } from 'app/shared/model//vendor-wh-courier-mapping.model';

export interface ICourierChannel {
    id?: number;
    vendorWHCourierMappings?: IVendorWHCourierMapping[];
    channelName?: string;
    channelId?: number;
    courierName?: string;
    courierId?: number;
}

export class CourierChannel implements ICourierChannel {
    constructor(
        public id?: number,
        public vendorWHCourierMappings?: IVendorWHCourierMapping[],
        public channelName?: string,
        public channelId?: number,
        public courierName?: string,
        public courierId?: number
    ) {}
}
