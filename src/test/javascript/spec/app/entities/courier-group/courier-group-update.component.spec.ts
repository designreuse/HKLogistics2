/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierGroupUpdateComponent } from 'app/entities/courier-group/courier-group-update.component';
import { CourierGroupService } from 'app/entities/courier-group/courier-group.service';
import { CourierGroup } from 'app/shared/model/courier-group.model';

describe('Component Tests', () => {
    describe('CourierGroup Management Update Component', () => {
        let comp: CourierGroupUpdateComponent;
        let fixture: ComponentFixture<CourierGroupUpdateComponent>;
        let service: CourierGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierGroupUpdateComponent]
            })
                .overrideTemplate(CourierGroupUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierGroupUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierGroupService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CourierGroup(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courierGroup = entity;
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
                    const entity = new CourierGroup();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courierGroup = entity;
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
