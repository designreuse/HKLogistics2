/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierGroupDetailComponent } from 'app/entities/courier-group/courier-group-detail.component';
import { CourierGroup } from 'app/shared/model/courier-group.model';

describe('Component Tests', () => {
    describe('CourierGroup Management Detail Component', () => {
        let comp: CourierGroupDetailComponent;
        let fixture: ComponentFixture<CourierGroupDetailComponent>;
        const route = ({ data: of({ courierGroup: new CourierGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourierGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourierGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courierGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
