export interface IProductVariant {
    id?: number;
    variantId?: string;
    serviceable?: boolean;
    pincode?: string;
}

export class ProductVariant implements IProductVariant {
    constructor(public id?: number, public variantId?: string, public serviceable?: boolean, public pincode?: string) {
        this.serviceable = false;
    }
}
