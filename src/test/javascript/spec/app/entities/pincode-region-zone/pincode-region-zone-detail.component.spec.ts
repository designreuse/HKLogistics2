/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeRegionZoneDetailComponent } from 'app/entities/pincode-region-zone/pincode-region-zone-detail.component';
import { PincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';

describe('Component Tests', () => {
    describe('PincodeRegionZone Management Detail Component', () => {
        let comp: PincodeRegionZoneDetailComponent;
        let fixture: ComponentFixture<PincodeRegionZoneDetailComponent>;
        const route = ({ data: of({ pincodeRegionZone: new PincodeRegionZone(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeRegionZoneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PincodeRegionZoneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PincodeRegionZoneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pincodeRegionZone).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
