/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { HubDetailComponent } from 'app/entities/hub/hub-detail.component';
import { Hub } from 'app/shared/model/hub.model';

describe('Component Tests', () => {
    describe('Hub Management Detail Component', () => {
        let comp: HubDetailComponent;
        let fixture: ComponentFixture<HubDetailComponent>;
        const route = ({ data: of({ hub: new Hub(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [HubDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HubDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HubDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hub).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
