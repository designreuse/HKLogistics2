export interface IPincode {
    id?: number;
    pincode?: string;
    region?: string;
    locality?: string;
    lastMileCost?: number;
    tier?: string;
    cityName?: string;
    cityId?: number;
    stateName?: string;
    stateId?: number;
    zoneName?: string;
    zoneId?: number;
    hubName?: string;
    hubId?: number;
}

export class Pincode implements IPincode {
    constructor(
        public id?: number,
        public pincode?: string,
        public region?: string,
        public locality?: string,
        public lastMileCost?: number,
        public tier?: string,
        public cityName?: string,
        public cityId?: number,
        public stateName?: string,
        public stateId?: number,
        public zoneName?: string,
        public zoneId?: number,
        public hubName?: string,
        public hubId?: number
    ) {}
}
