/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { AwbStatusUpdateComponent } from 'app/entities/awb-status/awb-status-update.component';
import { AwbStatusService } from 'app/entities/awb-status/awb-status.service';
import { AwbStatus } from 'app/shared/model/awb-status.model';

describe('Component Tests', () => {
    describe('AwbStatus Management Update Component', () => {
        let comp: AwbStatusUpdateComponent;
        let fixture: ComponentFixture<AwbStatusUpdateComponent>;
        let service: AwbStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [AwbStatusUpdateComponent]
            })
                .overrideTemplate(AwbStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AwbStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AwbStatusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AwbStatus(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.awbStatus = entity;
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
                    const entity = new AwbStatus();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.awbStatus = entity;
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
