export interface IPincodeCourierMapping {
    id?: number;
    routingCode?: string;
    applicableForCheapestCourier?: boolean;
    estimatedDeliveryDays?: number;
    pickupAvailable?: boolean;
    prepaidAir?: boolean;
    prepaidGround?: boolean;
    codAir?: boolean;
    codGround?: boolean;
    reverseAir?: boolean;
    reverseGround?: boolean;
    cardOnDeliveryAir?: boolean;
    cardOnDeliveryGround?: boolean;
    deliveryTypeOne?: boolean;
    deliveryTypeTwo?: boolean;
    pincodeId?: number;
    vendorWHCourierMappingId?: number;
    sourceDestinationMappingId?: number;
}

export class PincodeCourierMapping implements IPincodeCourierMapping {
    constructor(
        public id?: number,
        public routingCode?: string,
        public applicableForCheapestCourier?: boolean,
        public estimatedDeliveryDays?: number,
        public pickupAvailable?: boolean,
        public prepaidAir?: boolean,
        public prepaidGround?: boolean,
        public codAir?: boolean,
        public codGround?: boolean,
        public reverseAir?: boolean,
        public reverseGround?: boolean,
        public cardOnDeliveryAir?: boolean,
        public cardOnDeliveryGround?: boolean,
        public deliveryTypeOne?: boolean,
        public deliveryTypeTwo?: boolean,
        public pincodeId?: number,
        public vendorWHCourierMappingId?: number,
        public sourceDestinationMappingId?: number
    ) {
        this.applicableForCheapestCourier = false;
        this.pickupAvailable = false;
        this.prepaidAir = false;
        this.prepaidGround = false;
        this.codAir = false;
        this.codGround = false;
        this.reverseAir = false;
        this.reverseGround = false;
        this.cardOnDeliveryAir = false;
        this.cardOnDeliveryGround = false;
        this.deliveryTypeOne = false;
        this.deliveryTypeTwo = false;
    }
}
