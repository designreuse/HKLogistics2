/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeRegionZoneComponent } from 'app/entities/pincode-region-zone/pincode-region-zone.component';
import { PincodeRegionZoneService } from 'app/entities/pincode-region-zone/pincode-region-zone.service';
import { PincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';

describe('Component Tests', () => {
    describe('PincodeRegionZone Management Component', () => {
        let comp: PincodeRegionZoneComponent;
        let fixture: ComponentFixture<PincodeRegionZoneComponent>;
        let service: PincodeRegionZoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeRegionZoneComponent],
                providers: []
            })
                .overrideTemplate(PincodeRegionZoneComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PincodeRegionZoneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeRegionZoneService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PincodeRegionZone(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.pincodeRegionZones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
