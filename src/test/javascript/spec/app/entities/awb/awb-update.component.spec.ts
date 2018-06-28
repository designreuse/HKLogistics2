/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { AwbUpdateComponent } from 'app/entities/awb/awb-update.component';
import { AwbService } from 'app/entities/awb/awb.service';
import { Awb } from 'app/shared/model/awb.model';

describe('Component Tests', () => {
    describe('Awb Management Update Component', () => {
        let comp: AwbUpdateComponent;
        let fixture: ComponentFixture<AwbUpdateComponent>;
        let service: AwbService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [AwbUpdateComponent]
            })
                .overrideTemplate(AwbUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AwbUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AwbService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Awb(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.awb = entity;
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
                    const entity = new Awb();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.awb = entity;
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
