/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { SourceDestinationMappingComponent } from 'app/entities/source-destination-mapping/source-destination-mapping.component';
import { SourceDestinationMappingService } from 'app/entities/source-destination-mapping/source-destination-mapping.service';
import { SourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';

describe('Component Tests', () => {
    describe('SourceDestinationMapping Management Component', () => {
        let comp: SourceDestinationMappingComponent;
        let fixture: ComponentFixture<SourceDestinationMappingComponent>;
        let service: SourceDestinationMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [SourceDestinationMappingComponent],
                providers: []
            })
                .overrideTemplate(SourceDestinationMappingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceDestinationMappingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDestinationMappingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SourceDestinationMapping(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sourceDestinationMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
