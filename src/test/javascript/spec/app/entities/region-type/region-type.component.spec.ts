/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { RegionTypeComponent } from 'app/entities/region-type/region-type.component';
import { RegionTypeService } from 'app/entities/region-type/region-type.service';
import { RegionType } from 'app/shared/model/region-type.model';

describe('Component Tests', () => {
    describe('RegionType Management Component', () => {
        let comp: RegionTypeComponent;
        let fixture: ComponentFixture<RegionTypeComponent>;
        let service: RegionTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [RegionTypeComponent],
                providers: []
            })
                .overrideTemplate(RegionTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegionTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegionTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RegionType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.regionTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
