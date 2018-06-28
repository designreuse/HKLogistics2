/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { StateDetailComponent } from 'app/entities/state/state-detail.component';
import { State } from 'app/shared/model/state.model';

describe('Component Tests', () => {
    describe('State Management Detail Component', () => {
        let comp: StateDetailComponent;
        let fixture: ComponentFixture<StateDetailComponent>;
        const route = ({ data: of({ state: new State(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [StateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.state).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
