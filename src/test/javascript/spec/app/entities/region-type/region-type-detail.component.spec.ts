/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { RegionTypeDetailComponent } from 'app/entities/region-type/region-type-detail.component';
import { RegionType } from 'app/shared/model/region-type.model';

describe('Component Tests', () => {
    describe('RegionType Management Detail Component', () => {
        let comp: RegionTypeDetailComponent;
        let fixture: ComponentFixture<RegionTypeDetailComponent>;
        const route = ({ data: of({ regionType: new RegionType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [RegionTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegionTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegionTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.regionType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
