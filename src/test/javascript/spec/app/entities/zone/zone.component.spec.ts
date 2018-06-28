/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { ZoneComponent } from 'app/entities/zone/zone.component';
import { ZoneService } from 'app/entities/zone/zone.service';
import { Zone } from 'app/shared/model/zone.model';

describe('Component Tests', () => {
    describe('Zone Management Component', () => {
        let comp: ZoneComponent;
        let fixture: ComponentFixture<ZoneComponent>;
        let service: ZoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ZoneComponent],
                providers: []
            })
                .overrideTemplate(ZoneComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZoneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZoneService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Zone(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.zones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
