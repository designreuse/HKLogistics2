import { Moment } from 'moment';

export interface IAwb {
    id?: number;
    awbNumber?: string;
    awbBarCode?: string;
    cod?: boolean;
    createDate?: Moment;
    returnAwbNumber?: string;
    returnAwbBarCode?: string;
    isBrightAwb?: boolean;
    trackingLink?: string;
    channelName?: string;
    channelId?: number;
    vendorWHCourierMappingId?: number;
    awbStatusStatus?: string;
    awbStatusId?: number;
}

export class Awb implements IAwb {
    constructor(
        public id?: number,
        public awbNumber?: string,
        public awbBarCode?: string,
        public cod?: boolean,
        public createDate?: Moment,
        public returnAwbNumber?: string,
        public returnAwbBarCode?: string,
        public isBrightAwb?: boolean,
        public trackingLink?: string,
        public channelName?: string,
        public channelId?: number,
        public vendorWHCourierMappingId?: number,
        public awbStatusStatus?: string,
        public awbStatusId?: number
    ) {
        this.cod = false;
        this.isBrightAwb = false;
    }
}
