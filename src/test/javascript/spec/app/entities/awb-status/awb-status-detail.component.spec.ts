/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { AwbStatusDetailComponent } from 'app/entities/awb-status/awb-status-detail.component';
import { AwbStatus } from 'app/shared/model/awb-status.model';

describe('Component Tests', () => {
    describe('AwbStatus Management Detail Component', () => {
        let comp: AwbStatusDetailComponent;
        let fixture: ComponentFixture<AwbStatusDetailComponent>;
        const route = ({ data: of({ awbStatus: new AwbStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [AwbStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AwbStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AwbStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.awbStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
