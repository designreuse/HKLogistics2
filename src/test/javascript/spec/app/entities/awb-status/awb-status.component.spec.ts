/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { AwbStatusComponent } from 'app/entities/awb-status/awb-status.component';
import { AwbStatusService } from 'app/entities/awb-status/awb-status.service';
import { AwbStatus } from 'app/shared/model/awb-status.model';

describe('Component Tests', () => {
    describe('AwbStatus Management Component', () => {
        let comp: AwbStatusComponent;
        let fixture: ComponentFixture<AwbStatusComponent>;
        let service: AwbStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [AwbStatusComponent],
                providers: []
            })
                .overrideTemplate(AwbStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AwbStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AwbStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AwbStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.awbStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
