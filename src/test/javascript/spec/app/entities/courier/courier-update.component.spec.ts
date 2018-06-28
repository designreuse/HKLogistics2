/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierUpdateComponent } from 'app/entities/courier/courier-update.component';
import { CourierService } from 'app/entities/courier/courier.service';
import { Courier } from 'app/shared/model/courier.model';

describe('Component Tests', () => {
    describe('Courier Management Update Component', () => {
        let comp: CourierUpdateComponent;
        let fixture: ComponentFixture<CourierUpdateComponent>;
        let service: CourierService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierUpdateComponent]
            })
                .overrideTemplate(CourierUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Courier(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courier = entity;
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
                    const entity = new Courier();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courier = entity;
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
