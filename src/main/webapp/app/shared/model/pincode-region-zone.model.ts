export interface IPincodeRegionZone {
    id?: number;
    regionTypeName?: string;
    regionTypeId?: number;
    courierGroupName?: string;
    courierGroupId?: number;
    sourceDestinationMappingId?: number;
}

export class PincodeRegionZone implements IPincodeRegionZone {
    constructor(
        public id?: number,
        public regionTypeName?: string,
        public regionTypeId?: number,
        public courierGroupName?: string,
        public courierGroupId?: number,
        public sourceDestinationMappingId?: number
    ) {}
}
