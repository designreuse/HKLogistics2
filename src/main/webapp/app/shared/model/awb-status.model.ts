export interface IAwbStatus {
    id?: number;
    status?: string;
}

export class AwbStatus implements IAwbStatus {
    constructor(public id?: number, public status?: string) {}
}
