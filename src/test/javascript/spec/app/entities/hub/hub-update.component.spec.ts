/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { HubUpdateComponent } from 'app/entities/hub/hub-update.component';
import { HubService } from 'app/entities/hub/hub.service';
import { Hub } from 'app/shared/model/hub.model';

describe('Component Tests', () => {
    describe('Hub Management Update Component', () => {
        let comp: HubUpdateComponent;
        let fixture: ComponentFixture<HubUpdateComponent>;
        let service: HubService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [HubUpdateComponent]
            })
                .overrideTemplate(HubUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HubUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HubService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Hub(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hub = entity;
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
                    const entity = new Hub();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hub = entity;
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
