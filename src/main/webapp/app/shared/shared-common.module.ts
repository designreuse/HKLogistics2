import { NgModule } from '@angular/core';

import { HkLogisticsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [HkLogisticsSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [HkLogisticsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class HkLogisticsSharedCommonModule {}
