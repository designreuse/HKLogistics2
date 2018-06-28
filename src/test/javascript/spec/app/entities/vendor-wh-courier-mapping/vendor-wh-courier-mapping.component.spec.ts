/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { VendorWHCourierMappingComponent } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping.component';
import { VendorWHCourierMappingService } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping.service';
import { VendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';

describe('Component Tests', () => {
    describe('VendorWHCourierMapping Management Component', () => {
        let comp: VendorWHCourierMappingComponent;
        let fixture: ComponentFixture<VendorWHCourierMappingComponent>;
        let service: VendorWHCourierMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [VendorWHCourierMappingComponent],
                providers: []
            })
                .overrideTemplate(VendorWHCourierMappingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VendorWHCourierMappingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorWHCourierMappingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VendorWHCourierMapping(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vendorWHCourierMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
