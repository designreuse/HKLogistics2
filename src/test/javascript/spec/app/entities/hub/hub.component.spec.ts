/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { HubComponent } from 'app/entities/hub/hub.component';
import { HubService } from 'app/entities/hub/hub.service';
import { Hub } from 'app/shared/model/hub.model';

describe('Component Tests', () => {
    describe('Hub Management Component', () => {
        let comp: HubComponent;
        let fixture: ComponentFixture<HubComponent>;
        let service: HubService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [HubComponent],
                providers: []
            })
                .overrideTemplate(HubComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HubComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HubService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Hub(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.hubs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
