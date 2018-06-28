/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { SourceDestinationMappingDetailComponent } from 'app/entities/source-destination-mapping/source-destination-mapping-detail.component';
import { SourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';

describe('Component Tests', () => {
    describe('SourceDestinationMapping Management Detail Component', () => {
        let comp: SourceDestinationMappingDetailComponent;
        let fixture: ComponentFixture<SourceDestinationMappingDetailComponent>;
        const route = ({ data: of({ sourceDestinationMapping: new SourceDestinationMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [SourceDestinationMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SourceDestinationMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceDestinationMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sourceDestinationMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
