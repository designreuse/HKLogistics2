/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierDetailComponent } from 'app/entities/courier/courier-detail.component';
import { Courier } from 'app/shared/model/courier.model';

describe('Component Tests', () => {
    describe('Courier Management Detail Component', () => {
        let comp: CourierDetailComponent;
        let fixture: ComponentFixture<CourierDetailComponent>;
        const route = ({ data: of({ courier: new Courier(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourierDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourierDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courier).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
