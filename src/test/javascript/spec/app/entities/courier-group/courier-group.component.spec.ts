/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierGroupComponent } from 'app/entities/courier-group/courier-group.component';
import { CourierGroupService } from 'app/entities/courier-group/courier-group.service';
import { CourierGroup } from 'app/shared/model/courier-group.model';

describe('Component Tests', () => {
    describe('CourierGroup Management Component', () => {
        let comp: CourierGroupComponent;
        let fixture: ComponentFixture<CourierGroupComponent>;
        let service: CourierGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierGroupComponent],
                providers: []
            })
                .overrideTemplate(CourierGroupComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierGroupService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CourierGroup(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.courierGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
