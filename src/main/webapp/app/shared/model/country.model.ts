export interface ICountry {
    id?: number;
    name?: string;
    countryCode?: string;
}

export class Country implements ICountry {
    constructor(public id?: number, public name?: string, public countryCode?: string) {}
}
