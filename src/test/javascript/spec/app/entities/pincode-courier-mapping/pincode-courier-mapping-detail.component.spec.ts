/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeCourierMappingDetailComponent } from 'app/entities/pincode-courier-mapping/pincode-courier-mapping-detail.component';
import { PincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';

describe('Component Tests', () => {
    describe('PincodeCourierMapping Management Detail Component', () => {
        let comp: PincodeCourierMappingDetailComponent;
        let fixture: ComponentFixture<PincodeCourierMappingDetailComponent>;
        const route = ({ data: of({ pincodeCourierMapping: new PincodeCourierMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeCourierMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PincodeCourierMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PincodeCourierMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pincodeCourierMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
