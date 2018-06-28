export interface IRegionType {
    id?: number;
    name?: string;
    priority?: number;
}

export class RegionType implements IRegionType {
    constructor(public id?: number, public name?: string, public priority?: number) {}
}
