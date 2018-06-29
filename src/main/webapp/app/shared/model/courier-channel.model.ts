export interface ICourierChannel {
    id?: number;
    channelName?: string;
    channelId?: number;
    courierName?: string;
    courierId?: number;
}

export class CourierChannel implements ICourierChannel {
    constructor(
        public id?: number,
        public channelName?: string,
        public channelId?: number,
        public courierName?: string,
        public courierId?: number
    ) {}
}
