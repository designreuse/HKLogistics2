export interface ISourceDestinationMapping {
    id?: number;
    sourcePincode?: string;
    destinationPincode?: string;
}

export class SourceDestinationMapping implements ISourceDestinationMapping {
    constructor(public id?: number, public sourcePincode?: string, public destinationPincode?: string) {}
}
