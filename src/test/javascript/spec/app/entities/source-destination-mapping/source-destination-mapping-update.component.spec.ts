/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { SourceDestinationMappingUpdateComponent } from 'app/entities/source-destination-mapping/source-destination-mapping-update.component';
import { SourceDestinationMappingService } from 'app/entities/source-destination-mapping/source-destination-mapping.service';
import { SourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';

describe('Component Tests', () => {
    describe('SourceDestinationMapping Management Update Component', () => {
        let comp: SourceDestinationMappingUpdateComponent;
        let fixture: ComponentFixture<SourceDestinationMappingUpdateComponent>;
        let service: SourceDestinationMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [SourceDestinationMappingUpdateComponent]
            })
                .overrideTemplate(SourceDestinationMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceDestinationMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDestinationMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceDestinationMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceDestinationMapping = entity;
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
                    const entity = new SourceDestinationMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceDestinationMapping = entity;
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
