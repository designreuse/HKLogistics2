import { Moment } from 'moment';

export interface ICourierPricingEngine {
    id?: number;
    firstBaseWt?: number;
    firstBaseCost?: number;
    secondBaseWt?: number;
    secondBaseCost?: number;
    thirdBaseWt?: number;
    thirdBaseCost?: number;
    additionalWt?: number;
    additionalCost?: number;
    fuelSurcharge?: number;
    minCodCharges?: number;
    codCutoffAmount?: number;
    variableCodCharges?: number;
    validUpto?: Moment;
    costParameters?: string;
    courierName?: string;
    courierId?: number;
    regionTypeName?: string;
    regionTypeId?: number;
}

export class CourierPricingEngine implements ICourierPricingEngine {
    constructor(
        public id?: number,
        public firstBaseWt?: number,
        public firstBaseCost?: number,
        public secondBaseWt?: number,
        public secondBaseCost?: number,
        public thirdBaseWt?: number,
        public thirdBaseCost?: number,
        public additionalWt?: number,
        public additionalCost?: number,
        public fuelSurcharge?: number,
        public minCodCharges?: number,
        public codCutoffAmount?: number,
        public variableCodCharges?: number,
        public validUpto?: Moment,
        public costParameters?: string,
        public courierName?: string,
        public courierId?: number,
        public regionTypeName?: string,
        public regionTypeId?: number
    ) {}
}
