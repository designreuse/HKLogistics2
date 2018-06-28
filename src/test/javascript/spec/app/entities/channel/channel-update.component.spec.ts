/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ChannelUpdateComponent } from 'app/entities/channel/channel-update.component';
import { ChannelService } from 'app/entities/channel/channel.service';
import { Channel } from 'app/shared/model/channel.model';

describe('Component Tests', () => {
    describe('Channel Management Update Component', () => {
        let comp: ChannelUpdateComponent;
        let fixture: ComponentFixture<ChannelUpdateComponent>;
        let service: ChannelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ChannelUpdateComponent]
            })
                .overrideTemplate(ChannelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChannelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChannelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Channel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.channel = entity;
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
                    const entity = new Channel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.channel = entity;
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
