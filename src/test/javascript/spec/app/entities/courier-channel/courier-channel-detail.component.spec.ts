/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierChannelDetailComponent } from 'app/entities/courier-channel/courier-channel-detail.component';
import { CourierChannel } from 'app/shared/model/courier-channel.model';

describe('Component Tests', () => {
    describe('CourierChannel Management Detail Component', () => {
        let comp: CourierChannelDetailComponent;
        let fixture: ComponentFixture<CourierChannelDetailComponent>;
        const route = ({ data: of({ courierChannel: new CourierChannel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierChannelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourierChannelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourierChannelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courierChannel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
