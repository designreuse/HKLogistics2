export interface IState {
    id?: number;
    name?: string;
    identifier?: string;
    unionTerritory?: boolean;
}

export class State implements IState {
    constructor(public id?: number, public name?: string, public identifier?: string, public unionTerritory?: boolean) {
        this.unionTerritory = false;
    }
}
