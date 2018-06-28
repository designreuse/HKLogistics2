/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierChannelComponent } from 'app/entities/courier-channel/courier-channel.component';
import { CourierChannelService } from 'app/entities/courier-channel/courier-channel.service';
import { CourierChannel } from 'app/shared/model/courier-channel.model';

describe('Component Tests', () => {
    describe('CourierChannel Management Component', () => {
        let comp: CourierChannelComponent;
        let fixture: ComponentFixture<CourierChannelComponent>;
        let service: CourierChannelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierChannelComponent],
                providers: []
            })
                .overrideTemplate(CourierChannelComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierChannelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierChannelService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CourierChannel(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.courierChannels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
