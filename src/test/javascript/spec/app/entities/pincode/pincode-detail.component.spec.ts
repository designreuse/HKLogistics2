/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeDetailComponent } from 'app/entities/pincode/pincode-detail.component';
import { Pincode } from 'app/shared/model/pincode.model';

describe('Component Tests', () => {
    describe('Pincode Management Detail Component', () => {
        let comp: PincodeDetailComponent;
        let fixture: ComponentFixture<PincodeDetailComponent>;
        const route = ({ data: of({ pincode: new Pincode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PincodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PincodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pincode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
