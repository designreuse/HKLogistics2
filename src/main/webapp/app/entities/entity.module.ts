import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HkLogisticsRegionTypeModule } from './region-type/region-type.module';
import { HkLogisticsCourierChannelModule } from './courier-channel/courier-channel.module';
import { HkLogisticsCourierGroupModule } from './courier-group/courier-group.module';
import { HkLogisticsCourierModule } from './courier/courier.module';
import { HkLogisticsPincodeModule } from './pincode/pincode.module';
import { HkLogisticsCourierPricingEngineModule } from './courier-pricing-engine/courier-pricing-engine.module';
import { HkLogisticsProductVariantModule } from './product-variant/product-variant.module';
import { HkLogisticsChannelModule } from './channel/channel.module';
import { HkLogisticsSourceDestinationMappingModule } from './source-destination-mapping/source-destination-mapping.module';
import { HkLogisticsVendorWHCourierMappingModule } from './vendor-wh-courier-mapping/vendor-wh-courier-mapping.module';
import { HkLogisticsAwbStatusModule } from './awb-status/awb-status.module';
import { HkLogisticsAwbModule } from './awb/awb.module';
import { HkLogisticsCityModule } from './city/city.module';
import { HkLogisticsCountryModule } from './country/country.module';
import { HkLogisticsStateModule } from './state/state.module';
import { HkLogisticsZoneModule } from './zone/zone.module';
import { HkLogisticsHubModule } from './hub/hub.module';
import { HkLogisticsPincodeCourierMappingModule } from './pincode-courier-mapping/pincode-courier-mapping.module';
import { HkLogisticsPincodeRegionZoneModule } from './pincode-region-zone/pincode-region-zone.module';
import { HkLogisticsShipmentServiceTypeModule } from './shipment-service-type/shipment-service-type.module';
import { HkLogisticsShipmentModule } from './shipment/shipment.module';
import {HkLogisticsCourierWHMappingModule} from './courier-wh-mapping/courier-wh-mapping.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HkLogisticsRegionTypeModule,
        HkLogisticsCourierChannelModule,
        HkLogisticsCourierGroupModule,
        HkLogisticsCourierModule,
        HkLogisticsPincodeModule,
        HkLogisticsCourierPricingEngineModule,
        HkLogisticsProductVariantModule,
        HkLogisticsChannelModule,
        HkLogisticsSourceDestinationMappingModule,
        HkLogisticsVendorWHCourierMappingModule,
        HkLogisticsAwbStatusModule,
        HkLogisticsAwbModule,
        HkLogisticsCityModule,
        HkLogisticsCountryModule,
        HkLogisticsStateModule,
        HkLogisticsZoneModule,
        HkLogisticsHubModule,
        HkLogisticsPincodeCourierMappingModule,
        HkLogisticsPincodeRegionZoneModule,
        HkLogisticsShipmentServiceTypeModule,
        HkLogisticsShipmentModule,
        HkLogisticsCourierWHMappingModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsEntityModule {}
