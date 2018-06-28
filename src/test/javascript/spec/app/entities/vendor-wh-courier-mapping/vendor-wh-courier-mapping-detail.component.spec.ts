/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { VendorWHCourierMappingDetailComponent } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping-detail.component';
import { VendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';

describe('Component Tests', () => {
    describe('VendorWHCourierMapping Management Detail Component', () => {
        let comp: VendorWHCourierMappingDetailComponent;
        let fixture: ComponentFixture<VendorWHCourierMappingDetailComponent>;
        const route = ({ data: of({ vendorWHCourierMapping: new VendorWHCourierMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [VendorWHCourierMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VendorWHCourierMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VendorWHCourierMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vendorWHCourierMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
