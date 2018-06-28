/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ChannelDetailComponent } from 'app/entities/channel/channel-detail.component';
import { Channel } from 'app/shared/model/channel.model';

describe('Component Tests', () => {
    describe('Channel Management Detail Component', () => {
        let comp: ChannelDetailComponent;
        let fixture: ComponentFixture<ChannelDetailComponent>;
        const route = ({ data: of({ channel: new Channel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ChannelDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChannelDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChannelDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.channel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
