/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeCourierMappingComponent } from 'app/entities/pincode-courier-mapping/pincode-courier-mapping.component';
import { PincodeCourierMappingService } from 'app/entities/pincode-courier-mapping/pincode-courier-mapping.service';
import { PincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';

describe('Component Tests', () => {
    describe('PincodeCourierMapping Management Component', () => {
        let comp: PincodeCourierMappingComponent;
        let fixture: ComponentFixture<PincodeCourierMappingComponent>;
        let service: PincodeCourierMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeCourierMappingComponent],
                providers: []
            })
                .overrideTemplate(PincodeCourierMappingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PincodeCourierMappingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeCourierMappingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PincodeCourierMapping(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pincodeCourierMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
