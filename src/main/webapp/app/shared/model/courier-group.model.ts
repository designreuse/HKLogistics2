import { ICourier } from 'app/shared/model//courier.model';

export interface ICourierGroup {
    id?: number;
    name?: string;
    couriers?: ICourier[];
}

export class CourierGroup implements ICourierGroup {
    constructor(public id?: number, public name?: string, public couriers?: ICourier[]) {}
}
