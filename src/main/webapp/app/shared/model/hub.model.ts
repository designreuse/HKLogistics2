import { IPincode } from 'app/shared/model//pincode.model';

export interface IHub {
    id?: number;
    name?: string;
    address?: string;
    country?: string;
    pinCodes?: IPincode[];
}

export class Hub implements IHub {
    constructor(public id?: number, public name?: string, public address?: string, public country?: string, public pinCodes?: IPincode[]) {}
}
