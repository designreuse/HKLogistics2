export interface IShipmentServiceType {
    id?: number;
    name?: string;
}

export class ShipmentServiceType implements IShipmentServiceType {
    constructor(public id?: number, public name?: string) {}
}
