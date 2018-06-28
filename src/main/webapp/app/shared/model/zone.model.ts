export interface IZone {
    id?: number;
    name?: string;
}

export class Zone implements IZone {
    constructor(public id?: number, public name?: string) {}
}
