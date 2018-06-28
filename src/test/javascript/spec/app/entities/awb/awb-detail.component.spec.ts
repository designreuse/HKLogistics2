/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { AwbDetailComponent } from 'app/entities/awb/awb-detail.component';
import { Awb } from 'app/shared/model/awb.model';

describe('Component Tests', () => {
    describe('Awb Management Detail Component', () => {
        let comp: AwbDetailComponent;
        let fixture: ComponentFixture<AwbDetailComponent>;
        const route = ({ data: of({ awb: new Awb(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [AwbDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AwbDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AwbDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.awb).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
