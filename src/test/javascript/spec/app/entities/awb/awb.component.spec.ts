/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { AwbComponent } from 'app/entities/awb/awb.component';
import { AwbService } from 'app/entities/awb/awb.service';
import { Awb } from 'app/shared/model/awb.model';

describe('Component Tests', () => {
    describe('Awb Management Component', () => {
        let comp: AwbComponent;
        let fixture: ComponentFixture<AwbComponent>;
        let service: AwbService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [AwbComponent],
                providers: []
            })
                .overrideTemplate(AwbComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AwbComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AwbService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Awb(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.awbs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
