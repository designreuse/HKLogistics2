export interface ICity {
    id?: number;
    name?: string;
}

export class City implements ICity {
    constructor(public id?: number, public name?: string) {}
}
