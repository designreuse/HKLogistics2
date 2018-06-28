/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ZoneUpdateComponent } from 'app/entities/zone/zone-update.component';
import { ZoneService } from 'app/entities/zone/zone.service';
import { Zone } from 'app/shared/model/zone.model';

describe('Component Tests', () => {
    describe('Zone Management Update Component', () => {
        let comp: ZoneUpdateComponent;
        let fixture: ComponentFixture<ZoneUpdateComponent>;
        let service: ZoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ZoneUpdateComponent]
            })
                .overrideTemplate(ZoneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZoneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZoneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Zone(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.zone = entity;
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
                    const entity = new Zone();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.zone = entity;
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
