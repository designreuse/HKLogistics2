/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierChannelUpdateComponent } from 'app/entities/courier-channel/courier-channel-update.component';
import { CourierChannelService } from 'app/entities/courier-channel/courier-channel.service';
import { CourierChannel } from 'app/shared/model/courier-channel.model';

describe('Component Tests', () => {
    describe('CourierChannel Management Update Component', () => {
        let comp: CourierChannelUpdateComponent;
        let fixture: ComponentFixture<CourierChannelUpdateComponent>;
        let service: CourierChannelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierChannelUpdateComponent]
            })
                .overrideTemplate(CourierChannelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierChannelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierChannelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CourierChannel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courierChannel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CourierChannel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courierChannel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
